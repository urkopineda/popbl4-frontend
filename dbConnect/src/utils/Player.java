package utils;

import jaco.mp3.player.MP3Player;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;

import language.Strings;
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

import playerModel.MiLoadScreen;
import playerModel.MiRenderer;
import task.RunnsteinCalculator;
import task.SongLengthTimer;
import database.SQLiteUtils;
import exceptions.CancelacionException;

public class Player implements ActionListener {
	private MP3Player player;
	private ArrayList<Song> list;
	private int n;
	private Song playing;
	@SuppressWarnings("unused")
	private Duration played;
	
	private MiLoadScreen load;
	
	private ListSelectionListener listListener;
	
	private JPanel playerButtons;
	private JPanel playerList;
	private JFrame parent;
	
	private JList<Song> songList;
	private JList<Author> authorList;
	private JList<Album> albumList;
	private JList<Playlist> playList;
	private JButton playButton, pauseButton, nextButton, previousButton, stopButton;
	
	private final String DBNAME = "Runnstein";
	
	private boolean isLoaded;
	private boolean justRestarted;
	
	private SQLiteUtils conn;
	
	@SuppressWarnings("unused")
	private JLabel txtTitle, txtAuthor, txtAlbum, actualDuration, totalDuration;
	
	private RunnsteinCalculator calculator;
	private SongLengthTimer songLength;
	
	public Player(JFrame parent) {
		player = new MP3Player();
		list = new ArrayList<>();
		n = 0;
		playing = null;
		isLoaded = false;
		justRestarted = false;
		this.parent = parent;
		loadPlayer();
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
			System.out.println(Strings.get("loadPlayerIOExceptionMessage"));
		} catch (SQLException e) {
			System.out.println(Strings.get("loadPlayerSQLExceptionMessage"));
		}
		readDB();
		if (System.getProperty("os.name").toLowerCase().contains("windows")) searchDirectory("c:\\users\\"+System.getProperty("user.name")+"\\music");
		else searchDirectory("~/Music");
		
		calculator = new RunnsteinCalculator(Song.getSongListModel().getArrayList());
		
		fillPlayerButtonView();
		fillPlayerListView();
		
		songLength = new SongLengthTimer(this);
		
		isLoaded = true;
	}
	
	private void readDB() {
		ResultSet rs;
		int id = -1;
		try {
			String sql = "SELECT * FROM vCanciones";
			rs = conn.executeQuery(sql);
			load = new MiLoadScreen(parent);
			load.setWorkToMake(conn.executeQuery("SELECT COUNT(*) AS N FROM vCanciones").getInt("N"));
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
					load.progressHasBeenMade(Strings.get("readDBSongRead")+c, null);
				} catch (FileNotFoundException e) {
					System.out.println(Strings.get("readDBFileNotFoundMessage")+e.getMessage());
					conn.deleteEntry("Cancion", id);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (load != null) load.dispose();
			load = null;
		}
	}
	
	public void searchDirectory(String ruta) {
		JFileChooser fileChooser = null;
		try {
			if (ruta == null) {
				fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				fileChooser.showOpenDialog(parent);
				if (fileChooser.getSelectedFile() == null) throw new CancelacionException(Strings.get("searchDirectoryThrowCancelation"));
				ruta = fileChooser.getSelectedFile().getAbsolutePath();
			}
			final File folder = new File(ruta);
			load = new MiLoadScreen(parent);
			load.setWorkToMake(folder.listFiles().length*3);
			for (final File fileEntry : folder.listFiles()) {
				String name = fileEntry.getName();
				if (name.toLowerCase().endsWith(".mp3")) {
					createSongFromFile(fileEntry);
				} else {
					load.progressHasBeenMade(Strings.get("searchDirectoryFileNotMP3")+name, 3);
				}
			}			
		} catch (CancelacionException e) {
			System.out.println(e.getMessage());
		} catch (NullPointerException e) {
			System.out.println(Strings.get("searchDirectoryNullPointer"));
		} finally {
			if (load != null) load.dispose();
			load = null;
		}
	}
	
	public void searchSong() {
		JFileChooser chooser = null;
		try {
			chooser = new JFileChooser();
			chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			chooser.setFileFilter(new FileFilter() {
				@Override
				public boolean accept(File file) {
					if (file.getName().endsWith(".mp3")||file.isDirectory()) return true;
					else return false;
				}
				@Override
				public String getDescription() {
					return "Archivo MP3";
				}			
			});
			chooser.showOpenDialog(parent);
			File file = chooser.getSelectedFile();
			if (file == null) throw new CancelacionException(Strings.get("searchSongThrowCancelation"));
			createSongFromFile(file);
		} catch (CancelacionException e) {
			System.out.println(e.getMessage());
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
				load.progressHasBeenMade(Strings.get("songFromFileBPMCalculate")+" \""+c.getTitle()+"\"", null);
				pr.put("BPM", String.valueOf(Song.calculateBPM(file)));
				load.progressHasBeenMade(Strings.get("songFromFileLengthCalculate")+" \""+c.getTitle()+"\"", null);
				pr.put("Duration", String.valueOf(Song.calculateLength(file)));
				c.parseProperties(pr);
				conn.insertQuery("Cancion", c.getTableColumns(), c.getColumnValues());
				c.searchID(conn);
				load.progressHasBeenMade(Strings.get("songFromFileNewSongAdded")+"\""+c.toString()+"\"", null);
				Song.getSongListModel().addElement(c);
			}
			else load.progressHasBeenMade(Strings.get("songFromFileExistingSong")+c.getTitle(), 3);
		} catch (FileNotFoundException e) {
			System.out.println(Strings.get("songFromFileNotFound")+e.getMessage()+". Tal vez se haya borrado.");
		} catch (NullPointerException e) {
			System.out.println(Strings.get("songFromFileNullPointer")+file.getPath());
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
		songLength.setIsPlaying(true);
		Song s = songList.getSelectedValue();
		if (s==null && songList.getModel().getSize()>0) s = songList.getModel().getElementAt(0);
		else if (s==null) JOptionPane.showMessageDialog(parent, Strings.get("startRepNoSong"), "ERROR", JOptionPane.ERROR_MESSAGE);
		if (s != null) addSong(s);
		calculator.setPaused(false);
		calculator.setPlayingSong(s);
		previousButton.setEnabled(false);
		stopButton.setEnabled(true);
		nextButton.setEnabled(true);
		pauseButton.setEnabled(true);
		playButton.setEnabled(false);
		play();
		songLength.setPlaying(playing);
	}
	
	public void pauseReproduction() {
		pause();
		songLength.setIsPlaying(false);
		pauseButton.setEnabled(false);
		stopButton.setEnabled(false);
		playButton.setEnabled(true);
	}
	
	public void stopReproduction() {
		player.stop();
		songLength.setIsPlaying(false);
		songLength.setPlaying(null);
		clear();
		calculator.clearPlayedSongsList();
		calculator.setPlayingSong(null);
		calculator.setPaused(true);
		stopButton.setEnabled(false);
		nextButton.setEnabled(false);
		previousButton.setEnabled(false);
		pauseButton.setEnabled(false);
		playButton.setEnabled(true);
	}
	
	public void resumeReproduction() {
		songLength.setIsPlaying(true);
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
		for (int i=player.getPlayList().size(); i>0; i--) player.skipBackward();
		player.getPlayList().clear();
		list.clear();
		justRestarted = true;
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
		if (justRestarted) {
			justRestarted = false;
			player.skipBackward();
		}
		if (player.getPlayList().size()>0) {
			player.play();
			String path = parseURL((URL)player.getPlayList().get(n));
			playing = Song.checkDuplicateSong(path, Song.getSongListModel());
			System.out.println(playing);
		}
	}
	
	private void pause() {
		calculator.setPaused(true);
		player.pause();
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
	
	public boolean skipForward() {
		if (calculator.getChosenSong() == null) {
			addSong(songList.getModel().getElementAt((new Random()).nextInt(songList.getModel().getSize())));
			played = new Duration(0);
			n++;
			player.skipForward();
			calculator.fireSongChanged();
		} else if (n==list.size()-1 && !calculator.getChosenSong().equals(playing)) {
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

	public RunnsteinCalculator getCalculator() {
		return calculator;
	}
	
	public int getListSize() {
		return songList.getModel().getSize();
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
