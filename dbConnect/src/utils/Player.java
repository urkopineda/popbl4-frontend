package utils;

import jaco.mp3.player.MP3Player;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
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
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
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
import task.RunnsteinCalculator;
import database.SQLiteUtils;
import exceptions.CancelacionException;
import exceptions.CancionRepetidaException;

public class Player implements ActionListener {
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
	
	private JList<Song> songList;
	private JList<Author> authorList;
	private JList<Album> albumList;
	private JList<Playlist> playList;
	private JButton playButton, pauseButton, nextButton, previousButton, stopButton;
	
	private final String DBNAME = "Runnstein";
	
	private boolean isLoaded;
	
	//private final String txtTitulo_INICIAL = "Selecciona una canci�n";
	private SQLiteUtils conn;
	
	private JLabel txtTitle, txtAuthor, txtAlbum, actualDuration, totalDuration;
	
	//private Thread thread;
	private RunnsteinCalculator calculator;
	
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
	
	private void loadPlayer() {
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
		
		calculator = new RunnsteinCalculator(Song.getSongListModel().getArrayList());
		
		fillPlayerButtonView();
		fillPlayerListView();
		
		isLoaded = true;
	}
	
	private void readDB() {
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
					pr.put("BPM", String.valueOf(rs.getInt("BPM")));
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
	
	private void searchDirectory(String ruta) {
		JFileChooser fileChooser = null;
		try {
			if (ruta == null) {
				fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				fileChooser.showOpenDialog(this.playerButtons.getParent());
				if (fileChooser.getSelectedFile() == null) throw new CancelacionException("Elecci�n de directorio.");
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
			System.out.println("Ha habido un error de puntero vac�o.");
		}
	}
	
	private void createSongFromFile(File file) {
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
				pr.put("BPM", String.valueOf(Song.calculateBPM(file)));
				pr.put("Duration", String.valueOf(Song.calculateLength(file)));
				c.parseProperties(pr);
				conn.insertQuery("Cancion", c.getTableColumns(), c.getColumnValues());
				c.searchID(conn);
				Song.getSongListModel().addElement(c);
			}
			else throw new CancionRepetidaException(c.toString());
		} catch (FileNotFoundException e) {
			System.out.println("Ya no se encuentra la siguiente cancion: "+e.getMessage()+". Tal vez se haya borrado.");
		} catch (CancionRepetidaException e) {
			System.out.println("Ya existe la siguiente canci�n: "+e.getMessage()+".");
		} catch (NullPointerException e) {
			System.out.println("Ha habido un error cargando la canci�n: "+file.getPath());
		}
	}
	
	public boolean isLoaded() {
		return isLoaded;
	}
	
	private AbstractID3 detectID3(RandomAccessFile rfile) {
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
	
	public JPanel getPlayerButtons() {
		return playerButtons;
	}
	
	public JPanel getPlayerList() {
		return playerList;
	}
	
	public void startReproduction() {
		Song s = songList.getSelectedValue();
		if (s==null) s = songList.getModel().getElementAt(0);
		addSong(s);
		calculator.setPlayingSong(s);
		calculator.start();
		previousButton.setEnabled(false);
		stopButton.setEnabled(true);
		nextButton.setEnabled(true);
		pauseButton.setEnabled(true);
		playButton.setEnabled(false);
		play();
	}
	
	public void pauseReproduction() {
		pause();
		pauseButton.setEnabled(false);
		stopButton.setEnabled(false);
		playButton.setEnabled(true);
	}
	
	public void stopReproduction() {
		stop();
		clear();
		calculator.clearPlayedSongsList();
		stopButton.setEnabled(false);
		nextButton.setEnabled(false);
		previousButton.setEnabled(false);
		pauseButton.setEnabled(false);
		playButton.setEnabled(true);
	}
	
	public void resumeReproduction() {
		play();
		pauseButton.setEnabled(true);
		stopButton.setEnabled(true);
		playButton.setEnabled(false);
	}
	
	private void addSong(Song c) {
		player.addToPlayList(new File(c.getPath()));
		list.add(c);
	}
	
	private void clear() {
		player.getPlayList().clear();
		list.clear();
		playing = null;
		n=0;
	}
	
	private String parseURL(URL url) {
		String ret = url.toString();
		ret = ret.replaceAll("%20", " ");
		ret = ret.replaceFirst("file:/", "");
		ret = ret.replaceAll("/", "\\\\");
		return ret;
	}
	
	private void play() {
		played = new Duration(0);
		calculator.setPaused(false);
		player.play();
		String path = parseURL((URL)player.getPlayList().get(n));
		playing = Song.checkDuplicateSong(path, Song.getSongListModel());
		System.out.println(playing);
	}
	
	private void pause() {
		calculator.setPaused(true);
		player.pause();
	}
	
	private void stop() {
		calculator.stop();
		player.stop();
	}
	
	public Song getWhatsPlaying() {
		return playing;
	}
	
	public boolean isPaused() {
		return player.isPaused();
	}
	
	public boolean isStopped() {
		return player.isStopped();
	}
	
	private boolean skipForward() {		
		if (n==list.size()-1 && !calculator.getChosenSong().equals(playing)) {
			addSong(calculator.getChosenSong());
			played = new Duration(0);
			n++;
			player.skipForward();
			calculator.fireSongChanged();
		} else if (n<list.size()-1) {
			played = new Duration(0);
			n++;
			player.skipForward();
			calculator.fireSongChanged();
		}
		playing = list.get(n);
		System.out.println(playing);
		//if (n==list.size()-1) return false;
		return true;
	}
	
	private boolean skipBackward() {
		played = new Duration(0);
		player.skipBackward();
		playing = list.get(--n);
		System.out.println(playing);
		calculator.fireSongChanged();
		if (n==0) return false;
		return true;
	}
	
	private void fillPlayerButtonView() {
		playerButtons = new JPanel(new GridLayout(1, 5));
		previousButton = WindowMaker.createJButton(new ImageIcon("img/player/previous.png"), "previousSong", this);
		setButtonStyle(previousButton);
		previousButton.setEnabled(false);
		playerButtons.add(previousButton);
		pauseButton = WindowMaker.createJButton(new ImageIcon("img/player/pause.png"), "pauseSong", this);
		setButtonStyle(pauseButton);
		pauseButton.setEnabled(false);
		playerButtons.add(pauseButton);
		playButton = WindowMaker.createJButton(new ImageIcon("img/player/play.png"), "playSong", this);
		setButtonStyle(playButton);
		playButton.setEnabled(true);
		playerButtons.add(playButton);
		stopButton = WindowMaker.createJButton(new ImageIcon("img/player/stop.png"), "stopSong", this);
		setButtonStyle(stopButton);
		stopButton.setEnabled(false);
		playerButtons.add(stopButton);
		nextButton = WindowMaker.createJButton(new ImageIcon("img/player/next.png"), "nextSong", this);
		setButtonStyle(nextButton);
		nextButton.setEnabled(false);
		playerButtons.add(nextButton);
	}
	
	private void setButtonStyle(JButton btn) {
		btn.setOpaque(true);
		btn.setContentAreaFilled(false);
		btn.setBorderPainted(true);
	}
	
	private void fillPlayerListView() {
		playerList = new JPanel(new BorderLayout());
		JScrollPane scroll = new JScrollPane();
		scroll.setViewportView(songList);
		playerList.add(scroll);
	}

	private RunnsteinCalculator getCalculator() {
		return calculator;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		switch (cmd) {
		case "playSong":
			if (isPaused()) resumeReproduction();
			else startReproduction();
			break;
		case "stopSong":
			stopReproduction();
			break;
		case "pauseSong":
			pauseReproduction();
			break;
		case "nextSong":
			nextButton.setEnabled(skipForward());
			previousButton.setEnabled(true);
			break;
		case "previousSong":
			previousButton.setEnabled(skipBackward());
			nextButton.setEnabled(true);
			break;
		}
	}
}