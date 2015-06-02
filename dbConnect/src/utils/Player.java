package utils;

import jaco.mp3.player.MP3Player;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.SwingWorker;
import javax.swing.event.ListSelectionListener;

import model.Album;
import model.Author;
import model.Duration;
import model.Playlist;
import model.Song;

import org.farng.mp3.TagException;
import org.farng.mp3.id3.AbstractID3;
import org.farng.mp3.id3.ID3v1;
import org.farng.mp3.id3.ID3v1_1;
import org.farng.mp3.id3.ID3v2_2;
import org.farng.mp3.id3.ID3v2_3;
import org.farng.mp3.id3.ID3v2_4;

import playerModel.MiRenderer;
import database.SQLiteUtils;
import exceptions.CancelacionException;
import exceptions.CancionRepetidaException;

public class Player {
	private MP3Player player;
	private ArrayList<Song> list;
	private int n;
	private Song playing;
	private Duration played;
	
	private PropertyChangeListener pgListener;
	private ActionListener actionListener;
	private ListSelectionListener listListener;
	
	private JPanel playerButtons;
	private JPanel playerList;
	
	private JList<Song>songList;
	private JList<Author> authorList;
	private JList<Album> albumList;
	private JList<Playlist> playList;
	private JButton playButton, pauseButton, nextButton, previousButton, stopButton;
	
	private final String DBNAME = "Runnstein";
	
	private boolean isLoaded;
	
	//private final String txtTitulo_INICIAL = "Selecciona una canción";
	private SQLiteUtils conn;
	
	private JLabel txtTitle, txtAuthor, txtAlbum, actualDuration, totalDuration;
	private JProgressBar progressBar;
	
	private Task task;
	private Thread thread;
	
	public Player(PropertyChangeListener pgListener, ActionListener actionListener, ListSelectionListener listListener) {
		player = new MP3Player();
		list = new ArrayList<>();
		n = 0;
		playing = null;
		isLoaded = false;
		loadPlayer();
		this.pgListener = pgListener;
		this.actionListener = actionListener;
		this.listListener = listListener;
	}
	
	public void loadPlayer() {
		songList = new JList<>();
		songList.setModel(Song.getSongListModel());
		songList.setCellRenderer(new MiRenderer());
		songList.addListSelectionListener(listListener);
		
		authorList = new JList<>();
		authorList.setModel(Author.getAuthorListModel());
		
		albumList = new JList<>();
		albumList.setModel(Album.getAlbumListModel());
		
		playList = new JList<>();
		playList.setModel(Playlist.getPlaylistModel());

		boolean nuevo = false;
		
		if (!(new File("./"+DBNAME+".db")).exists()) nuevo = true;
		conn = new SQLiteUtils(DBNAME);
		
		try {
			String s = new String(Files.readAllBytes(Paths.get("./SQLite.sql")), StandardCharsets.UTF_8);
			String [] dml = s.split("[;]");
			for (int i=0; i<dml.length; i++) conn.executeUpdate(dml[i]);
			if (nuevo) {
				conn.executeUpdate("INSERT INTO Autor VALUES (0,'Desconocido')");
				conn.executeUpdate("INSERT INTO Album VALUES (0,0,'Desconocido');");
			}
		} catch (IOException e) {
			System.out.println("Ha habido un error al leer el script SQL.");
		} catch (SQLException e) {
			System.out.println("Ha habido un error con la base de datos.");
		}
		readDB();
		if (System.getProperty("os.name").toLowerCase().contains("windows")) searchDirectory("c:\\users\\"+System.getProperty("user.name")+"\\music");
		else searchDirectory("~/Music");
		
		fillPlayerButtonView();
		fillPlayerListView();
		
		isLoaded = true;
	}
	
	public void readDB() {
		ResultSet rs;
		int id = -1;
		try {
			String sql = "SELECT * FROM vCanciones";
			rs = conn.executeQuery(sql);
			while (rs.next()) {
				try {
					Properties pr = new Properties();
					id = rs.getInt("CancionID");
					if (!(new File(rs.getString("Ruta"))).exists()) throw new FileNotFoundException(rs.getString("Ruta"));
					pr.put("SongID", String.valueOf(rs.getInt("CancionID")));
					pr.put("Title", rs.getString("Nombre"));
					pr.put("Path", rs.getString("Ruta"));
					pr.put("Duration", String.valueOf(rs.getInt("Duracion")));
					pr.put("Album", rs.getString("Album"));
					pr.put("AlbumID", String.valueOf(rs.getInt("AlbumID")));
					pr.put("Author", rs.getString("Autor"));
					pr.put("AuthorID", String.valueOf(rs.getInt("AutorID")));
					Song c = new Song(pr, new Album(pr, new Author(pr)));
					Song.getSongListModel().addElement(c);
					Author.getAuthorListModel().addElement(c.getAuthor());
					Album.getAlbumListModel().addElement(c.getAlbum());
				} catch (FileNotFoundException e) {
					System.out.println("Ya no se encuentra el archivo "+e.getMessage()+". Tal vez haya sido borrado.");
					conn.deleteEntry("Cancion", id);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void searchDirectory(String ruta) {
		JFileChooser fileChooser = null;
		try {
			if (ruta == null) {
				fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				fileChooser.showOpenDialog(this.playerButtons.getParent());
				if (fileChooser.getSelectedFile() == null) throw new CancelacionException("Elección de directorio.");
				ruta = fileChooser.getSelectedFile().getAbsolutePath();
			}
			final File folder = new File(ruta);
			for (final File fileEntry : folder.listFiles()) {
				String name = fileEntry.getName();
				if (name.toLowerCase().endsWith(".mp3")) createSongFromFile(fileEntry);
			}			
		} catch (CancelacionException e) {
			System.out.println(e.getMessage());
		} catch (NullPointerException e) {
			System.out.println("Ha habido un error de puntero vacío.");
		}
	}
	
	public void createSongFromFile(File file) {
		AbstractID3 tag = null;
		RandomAccessFile rafile = null;
		Song c = null;
		Properties pr;
		Album alb = null;
		Author aut = null;
		try {
			if (!file.exists()) throw new FileNotFoundException(file.getName());
			pr = new Properties();
			rafile = new RandomAccessFile(file, "r");
			tag = detectID3(rafile);
			pr.put("Title",tag.getSongTitle());
			pr.put("Author",tag.getLeadArtist());
			pr.put("Album", tag.getAlbumTitle());
			pr.put("Path", file.getAbsolutePath());
			pr.put("Duration", String.valueOf(Song.calculateLength(file)));
			c = new Song(pr);
			aut = new Author(pr);
			alb = new Album(pr, aut);
			Song caux = new Song(pr, alb);
			if (Song.checkDuplicateSong(caux, Song.getSongListModel())==null) {
				Author autorAux;
				if ((autorAux = Author.checkDuplicateAuthor(aut, Author.getAuthorListModel()))!=null) {
					c.setAuthor(autorAux);
					aut = autorAux;
				} else {
					conn.insertQuery("Autor", aut.getTableColumns(), aut.getColumnValues());
					aut.searchID(conn);
					Author.getAuthorListModel().addElement(aut);
				}
				Album albumAux;
				if ((albumAux = Album.checkDuplicateAlbum(alb, Album.getAlbumListModel()))!=null) {
					c.setAlbum(albumAux);
					alb = albumAux;
				} else {
					conn.insertQuery("Album", alb.getTableColumns(), alb.getColumnValues());
					alb.searchID(conn);
					Album.getAlbumListModel().addElement(alb);
				}
				c.setAlbum(alb);
				c.setAuthor(aut);
				conn.insertQuery("Cancion", c.getTableColumns(), c.getColumnValues());
				c.searchID(conn);
				Song.getSongListModel().addElement(c);
			}
			else throw new CancionRepetidaException(c.toString());
		} catch (FileNotFoundException e) {
			System.out.println("Ya no se encuentra la siguiente cancion: "+e.getMessage()+". Tal vez se haya borrado.");
		} catch (CancionRepetidaException e) {
			System.out.println("Ya existe la siguiente canción: "+e.getMessage()+".");
		}
	}
	
	public boolean isLoaded() {
		return isLoaded;
	}
	
	public AbstractID3 detectID3(RandomAccessFile rfile) {
		AbstractID3 ret = null;
		try {ret=new ID3v1(rfile);}
		catch (TagException e) {
			try {ret=new ID3v1_1(rfile);}
			catch (TagException e1){
				try {ret = new ID3v2_2(rfile);}
				catch (TagException e2) {
					try {ret = new ID3v2_3(rfile);}
					catch (TagException e3) {
						try {ret = new ID3v2_4(rfile);}
						catch (TagException e4) {
							ret = null;
						} catch (Exception e4) {}
					} catch (Exception e3) {}
				} catch (Exception e2) {}
			} catch (Exception e1) {}
		} catch (Exception e) {}
		return ret;
	}
	
	private void createThread() {
		thread = new Thread() {
			private boolean isStopped = false;
			public void run() {
				try {
					while (true) {
						Thread.sleep(1000);
						played.addSecond();
						actualDuration.setText(played.toString());
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		thread.start();
	}
	
	public JPanel getPlayerButtons() {
		return playerButtons;
	}
	
	public JPanel getPlayerList() {
		return playerList;
	}
	
	private void executeTask() {
		task = new Task();
		task.addPropertyChangeListener(pgListener);
		task.execute();
	}
	
	public void addSong(Song c) {
		player.addToPlayList(new File(c.getPath()));
		list.add(c);
	}
	
	public void clear() {
		player.getPlayList().clear();
		list.clear();
		playing = null;
		n=0;
		//actualizarLabels();
		task = null;
	}
	
	private String parseURL(URL url) {
		String ret = url.toString();
		ret = ret.replaceAll("%20", " ");
		ret = ret.replaceFirst("file:/", "");
		ret = ret.replaceAll("/", "\\\\");
		return ret;
	}
	
	public void play() {
		played = new Duration(0);
		if (thread == null) createThread();
		else thread.resume();
		player.play();
		String path = parseURL((URL)player.getPlayList().get(n));
		playing = Song.checkDuplicateSong(path, Song.getSongListModel());
		System.out.println(playing);
		executeTask();
		//actualizarLabels();
	}
	
	/*private void actualizarLabels() {
		if (playing == null) {
			txtTitle.setText(txtTitulo_INICIAL);
			txtAlbum.setText("");
			txtAuthor.setText("");
			totalDuration.setText("");
		} else {
			txtTitle.setText(playing.getTitle());
			txtAlbum.setText(playing.getAlbum().toString());
			txtAuthor.setText(playing.getAuthor().toString());
			totalDuration.setText(playing.getDuration().toString());
		}
		actualDuration.setText("0:00");
	}*/
	
	public void pause() {
		thread.stop();
		player.pause();
	}
	
	public void stop() {
		thread.stop();
		thread = null;
		task.cancel(true);
		task.restart();
		task = null;
		progressBar.setValue(0);
		player.stop();
	}
	
	public Song getWhatsPlaying() {
		return playing;
	}
	
	public void addPlaylist(Playlist list) {
		ListIterator<Song> it = list.getListIterator();
		while (it.hasNext()) {
			Song c = it.next();
			addSong(c);
		}
	}
	
	public boolean isPaused() {
		return player.isPaused();
	}
	
	public boolean isStopped() {
		return player.isStopped();
	}
	
	public boolean skipForward() {
		thread.stop();
		createThread();
		task.restart();
		progressBar.setValue(0);
		played = new Duration(0);
		player.skipForward();
		playing = list.get(++n);
		System.out.println(playing);
		//actualizarLabels();
		if (n==list.size()-1) return false;
		return true;
	}
	
	public boolean skipBackward() {
		thread.stop();
		createThread();
		task.restart();
		progressBar.setValue(0);
		played = new Duration(0);
		player.skipBackward();
		playing = list.get(--n);
		System.out.println(playing);
		//actualizarLabels();
		if (n==0) return false;
		return true;
	}
	
	private void fillPlayerButtonView() {
		playerButtons = new JPanel(new GridLayout(1, 5));
		previousButton = WindowMaker.createJButton(new ImageIcon("res/previous.png"), "previousSong", actionListener);
		setButtonStyle(previousButton);
		playerButtons.add(previousButton);
		pauseButton = WindowMaker.createJButton(new ImageIcon("res/pause.png"), "pauseSong", actionListener);
		setButtonStyle(pauseButton);
		playerButtons.add(pauseButton);
		playButton = WindowMaker.createJButton(new ImageIcon("res/play.png"), "playSong", actionListener);
		setButtonStyle(playButton);
		playerButtons.add(playButton);
		stopButton = WindowMaker.createJButton(new ImageIcon("res/stop.png"), "stopSong", actionListener);
		setButtonStyle(stopButton);
		playerButtons.add(stopButton);
		nextButton = WindowMaker.createJButton(new ImageIcon("res/next.png"), "nextSong", actionListener);
		setButtonStyle(nextButton);
		playerButtons.add(nextButton);
	}
	
	private void setButtonStyle(JButton btn) {
		btn.setOpaque(true);
		btn.setContentAreaFilled(false);
		btn.setBorderPainted(false);
	}
	
	private void fillPlayerListView() {
		playerList = new JPanel(new BorderLayout());
		JScrollPane scroll = new JScrollPane();
		scroll.setViewportView(songList);
		playerList.add(scroll);
	}
	
	/*private void rellenarVista() {
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		txtTitle = new JLabel(txtTitulo_INICIAL);
		txtTitle.setFont(new Font(txtTitle.getFont().getFontName(), Font.BOLD, 28));
		txtTitle.setHorizontalAlignment(SwingConstants.CENTER);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		add(txtTitle, c);
		
		txtAlbum = new JLabel();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 2;
		add(txtAlbum, c);
		
		txtAuthor = new JLabel();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 2;
		add(txtAuthor, c);
		
		actualDuration = new JLabel();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 3;
		c.anchor = GridBagConstraints.WEST;
		add(actualDuration, c);
		
		totalDuration = new JLabel();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 4;
		c.anchor = GridBagConstraints.EAST;
		add(totalDuration, c);
		
		progressBar = new JProgressBar();
		c.gridx = 0;
		c.gridy = 5;
		c.gridwidth = 2;
		c.anchor = GridBagConstraints.CENTER;
		add(progressBar, c);
	}*/
	
	private class Task extends SwingWorker<Void, Void> {
		private int progress;
		
		@Override
		protected Void doInBackground() {
			progress = 0;
			setProgress(0);
			while (progress<100) {
				int n = 0;
				try {
					do Thread.sleep(1); while (n++<playing.getDuration().enSegundos()*10);
				} catch (InterruptedException e) {
					System.out.println("Se ha interrumpido la espera.");
				}
				progress++;
				setProgress(progress);
			}
			return null;
		}
		
		public void restart() {
			progress = 0;
		}
		
	}
}
