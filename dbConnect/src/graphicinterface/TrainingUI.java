package graphicinterface;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import language.Strings;
import main.Configuration;
import task.ChronoTimer;
import task.HeartAnimator;
import utils.WindowMaker;

public class TrainingUI {
	ChronoTimer chronometerThread = null;
	HeartAnimator heartAnimator = null;
	JPanel mainPanel = null;
	JPanel rightPanel = null;
	JPanel leftPanel = null;
	JPanel chronoPlayerPanel = null;
	JPanel chronometerPanel = null;
	JPanel playerPanel = null;
	JPanel heartRatePanel = null;
	JPanel chronometerSouthPanel = null;
	JButton buttonStart = null;
	JButton buttonStop = null;
	JButton buttonPause = null;
	JLabel chronometerNumbers = null;
	JLabel ppmNumbers = null;
	JLabel ppmImage = null;
	ActionListener act = null;
	
	// ######################################################## //
	/*
	JFrame window;
	private Scanner teclado;
	private Reproductor player;
	private SQLiteUtils conn;
	
	private JList<Cancion> songList;
	private JList<Autor> authorList;
	private JList<Album> albumList;
	private JList<Playlist> playList;
	private MiBoton bAnterior, bPlay, bPause, bStop, bSiguiente;
	
	ListSelectionListener list = null;
	*/
	// ######################################################## //
	
	public TrainingUI(ActionListener act) {
		this.act = act;
	}
	
	public Container createMainPanel() {
		mainPanel = new JPanel(new GridLayout(1, 2, 0, 0));
		mainPanel.add(createLeftPanel());
		mainPanel.add(createRightPanel());
		buttonPause.setEnabled(false);
		buttonStop.setEnabled(false);
		return mainPanel;
	}
	
	private Container createRightPanel() {
		rightPanel = new JPanel();
		rightPanel.setBorder(BorderFactory.createTitledBorder(Strings.trainingList));
		
		return rightPanel;
	}
	
	private Container createLeftPanel() {
		leftPanel = new JPanel(new GridLayout(2, 1, 0, 0));
		leftPanel.setBorder(BorderFactory.createTitledBorder(Strings.trainingMain));
		leftPanel.add(createChronoPlayerPanel());
		leftPanel.add(createHeartRatePanel());
		return leftPanel;
	}
	
	private Container createChronoPlayerPanel() {
		chronoPlayerPanel = new JPanel(new GridLayout(2, 1, 0, 0));
		chronoPlayerPanel.add(createChronometerPanel());
		chronoPlayerPanel.add(createPlayerPanel());
		return chronoPlayerPanel;
	}
	
	private Container createChronometerPanel() {
		chronometerPanel = new JPanel(new BorderLayout());
		chronometerPanel.setBorder(BorderFactory.createTitledBorder(Strings.trainingChrono));
		chronometerNumbers = WindowMaker.createJLabel(chronometerNumbers, "00:00:00", 75, "center");
		chronometerPanel.add(chronometerNumbers, BorderLayout.CENTER);
		chronometerSouthPanel = new JPanel(new GridLayout(1, 3, 5, 5));
		buttonStart = WindowMaker.createJButton(buttonStart, Strings.trainingBtnStart, "start", act);
		buttonPause = WindowMaker.createJButton(buttonPause, Strings.trainingBtnPause, "pause", act);
		buttonStop = WindowMaker.createJButton(buttonStop, Strings.trainingBtnStop, "stop", act);
		chronometerSouthPanel.add(buttonStart);
		chronometerSouthPanel.add(buttonPause);
		chronometerSouthPanel.add(buttonStop);
		chronometerPanel.add(chronometerSouthPanel, BorderLayout.SOUTH);
		return chronometerPanel;
	}
	
	private Container createPlayerPanel() {
		playerPanel = new JPanel(new BorderLayout());
		playerPanel.setBorder(BorderFactory.createTitledBorder(Strings.trainingPlayer));
		// AQUÍ VA LO DE UNAI!
		// ######################################################## //
		// setLists();
		// ######################################################## //
		return playerPanel;
	}
	
	private Container createHeartRatePanel() {
		heartRatePanel = new JPanel(new BorderLayout());
		heartRatePanel.setBorder(BorderFactory.createTitledBorder(Strings.trainingPulse));
		ppmNumbers = WindowMaker.createJLabel(ppmNumbers, Configuration.ppm+" ppm", 50, "center");
		heartRatePanel.add(ppmNumbers, BorderLayout.NORTH);
		ppmImage = new JLabel(new ImageIcon(Configuration.dHeartOff));
		heartRatePanel.add(ppmImage, BorderLayout.CENTER);
		return heartRatePanel;
	}
	
	// ######################################################## //
	/*
	private void setLists() {
		songList = new JList<>();
		songList.setModel(Cancion.getSongListModel());
		songList.setCellRenderer(new MiRenderer());
		songList.addListSelectionListener(list);
		authorList = new JList<>();
		authorList.setModel(Autor.getAuthorListModel());
		albumList = new JList<>();
		albumList.setModel(Album.getAlbumListModel());
		playList = new JList<>();
		playList.setModel(Playlist.getPlaylistModel());
	}
	
	private void createSQLite() {
		player = new Reproductor();
		boolean nuevo = false;
		teclado = new Scanner(System.in);
		if (!(new File("./doc/"+Configuration.dbSQLite+".db")).exists()) nuevo = true;
		conn = new SQLiteUtils(Configuration.dbSQLite);
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
					pr.put("CancionID", String.valueOf(rs.getInt("CancionID")));
					pr.put("Titulo", rs.getString("Nombre"));
					pr.put("Ruta", rs.getString("Ruta"));
					pr.put("Duracion", String.valueOf(rs.getInt("Duracion")));
					pr.put("Album", rs.getString("Album"));
					pr.put("AlbumID", String.valueOf(rs.getInt("AlbumID")));
					pr.put("Autor", rs.getString("Autor"));
					pr.put("AutorID", String.valueOf(rs.getInt("AutorID")));
					Cancion c = new Cancion(pr, new Album(pr, new Autor(pr)));
					Cancion.getSongListModel().addElement(c);
					Autor.getAuthorListModel().addElement(c.getAutor());
					Album.getAlbumListModel().addElement(c.getAlbum());
				} catch (FileNotFoundException e) {
					System.out.println("Ya no se encuentra el archivo "+e.getMessage()+". Tal vez haya sido borrado.");
					conn.deleteEntry("Cancion", id);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void searchDirectory(String ruta) {
		JFileChooser fileChooser = null;
		try {
			if (ruta == null) {
				fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				fileChooser.showOpenDialog(window);
				ruta = fileChooser.getSelectedFile().getAbsolutePath();
				if (ruta == null) throw new CancelException("Elección de directorio.");
			}
			final File folder = new File(ruta);
			for (final File fileEntry : folder.listFiles()) {
				String name = fileEntry.getName();
				if (name.toLowerCase().endsWith(".mp3")) createSong(fileEntry);
			}
		} catch (CancelException e) {
			System.out.println(e.getMessage());
		} catch (NullPointerException e) {
			System.out.println("Ha habido un error de puntero vacío.");
		}
	}
	
	public void searchSong() {
		System.out.print("Introduce la ruta de la cancion: ");
		File file = new File(teclado.nextLine());
		if (file.getName().toLowerCase().endsWith(".mp3")) createSong(file);
	}
	
	public void createSong(File file) {
		AbstractID3 tag = null;
		RandomAccessFile rafile = null;
		Cancion c = null;
		Properties pr;
		Album alb = null;
		Autor aut = null;
		try {
			if (!file.exists()) throw new FileNotFoundException(file.getName());
			pr = new Properties();
			rafile = new RandomAccessFile(file, "r");
			tag = detectID3(rafile);
			pr.put("Titulo",tag.getSongTitle());
			pr.put("Autor",tag.getLeadArtist());
			pr.put("Album", tag.getAlbumTitle());
			pr.put("Ruta", file.getAbsolutePath());
			pr.put("Duracion", String.valueOf(Cancion.calculateLength(file)));
			c = new Cancion(pr);
			aut = new Autor(pr);
			alb = new Album(pr, aut);
			Cancion caux = new Cancion(pr, alb);
			if (Cancion.comprobarCancionRepetida(caux, Cancion.getSongListModel())==null) {
				Autor autorAux;
				if ((autorAux = Autor.comprobarAutorRepetido(aut, Autor.getAuthorListModel()))!=null) {
					c.setAutor(autorAux);
					aut = autorAux;
				} else {
					conn.insertQuery("Autor", aut.getTableColumns(), aut.getColumnValues());
					aut.searchID(conn);
					Autor.getAuthorListModel().addElement(aut);
				}
				Album albumAux;
				if ((albumAux = Album.comprobarAlbumRepetido(alb, Album.getAlbumListModel()))!=null) {
					c.setAlbum(albumAux);
					alb = albumAux;
				} else {
					conn.insertQuery("Album", alb.getTableColumns(), alb.getColumnValues());
					alb.searchID(conn);
					Album.getAlbumListModel().addElement(alb);
				}
				c.setAlbum(alb);
				c.setAutor(aut);
				conn.insertQuery("Cancion", c.getTableColumns(), c.getColumnValues());
				c.searchID(conn);
				Cancion.getSongListModel().addElement(c);
			}
			else throw new RepeatedSongException(c.toString());
		} catch (FileNotFoundException e) {
			System.out.println("Ya no se encuentra la siguiente cancion: "+e.getMessage()+". Tal vez se haya borrado.");
		} catch (RepeatedSongException e) {
			System.out.println("Ya existe la siguiente canción: "+e.getMessage()+".");
		}
	}
	
	public void showSongs() {
		System.out.println("\nHe aquí la lista de todas las canciones:");
		ListIterator<Cancion> it = Cancion.getSongListModel().listIterator();
		while (it.hasNext()) {
			Cancion c = it.next();
			System.out.println(c);
		}
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
	
	public void startPlayer() {
		for (int i=songList.getSelectedIndex(); i<songList.getModel().getSize(); i++) player.addSong(songList.getModel().getElementAt(i));
		for (int i=0; i<songList.getSelectedIndex(); i++) player.addSong(songList.getModel().getElementAt(i));
		bAnterior.setEnabled(false);
		bStop.setEnabled(true);
		bSiguiente.setEnabled(true);
		bPause.setEnabled(true);
		player.play();
	}
	
	public void stopPlayer() {
		player.vaciarLista();
		bStop.setEnabled(false);
		bSiguiente.setEnabled(false);
		bAnterior.setEnabled(false);
		bPause.setEnabled(false);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		switch (cmd) {
		case "abrirArchivo": break;
		case "abrirDirectorio": buscarDirectorio(null); break;
		case "salir": this.dispose();break;
		case "play": if (!songList.isSelectionEmpty()) empezarReproduccion(); break;
		case "stop": if (!player.isStopped()) detenerReproduccion(); break;
		case "pause": if (!player.isPaused()) {
				player.pause();
				bPause.setEnabled(false);
			}
			break;
		case "previous": bAnterior.setEnabled(player.skipBackward());
			bSiguiente.setEnabled(true);
			break;
		case "next": bSiguiente.setEnabled(player.skipForward());
			bAnterior.setEnabled(true);
			break;
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		@SuppressWarnings("unchecked")
		JList<Dato> lista = (JList<Dato>) e.getSource();
		if (lista.equals(songList)) {
			if (!e.getValueIsAdjusting()) {
				if (!lista.isSelectionEmpty()) bPlay.setEnabled(true);
				else bPlay.setEnabled(false);
			}
		}
	}
	*/
	// ######################################################## //
	
	public void startTimer() {
		if (chronometerThread != null) {
			if (!chronometerThread.isAlive()) {
				chronometerThread.start();
				heartAnimator.start();
			}
		} else {
			chronometerThread = new ChronoTimer(chronometerNumbers);
			heartAnimator = new HeartAnimator(ppmImage);
			chronometerThread.start();
			heartAnimator.start();
		}
		chronometerThread.startTimer();
		buttonStop.setEnabled(true);
		buttonPause.setEnabled(true);
		buttonStart.setEnabled(false);
	}
	
	public void pauseTimer() {
		chronometerThread.pauseTimer();
		buttonPause.setEnabled(false);
		buttonStart.setEnabled(true);
	}
	
	public void stopTimer() {
		chronometerThread.stopTimer();
		chronometerThread = null;
		buttonPause.setEnabled(false);
		buttonStop.setEnabled(false);
		buttonStart.setEnabled(true);
	}
}
