package id3;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ListIterator;
import java.util.Properties;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.Album;
import model.Autor;
import model.Cancion;
import model.Dato;
import model.Playlist;
import playermodel.MiBoton;
import playermodel.MiMenuItem;
import playermodel.MiRenderer;
import database.SQLiteUtils;
import exception.CancelException;
import exception.RepeatedSongException;

@SuppressWarnings("serial")
public class Main extends JFrame implements ActionListener, ListSelectionListener {
	private Scanner teclado;
	private Reproductor player;
	private SQLiteUtils conn;
	private final static String DBNAME = "runnstein";
	
	private JMenuBar menu;
	private JMenu mArchivo;
	private MiMenuItem miAbrirArchivo, miAbrirDirectorio, miSalir;
	private JList<Cancion> songList;
	private JList<Autor> authorList;
	private JList<Album> albumList;
	private JList<Playlist> playList;
	private MiBoton bAnterior, bPlay, bPause, bStop, bSiguiente;
	
	public Main() {		
		songList = new JList<>();
		songList.setModel(Cancion.getSongListModel());
		songList.setCellRenderer(new MiRenderer());
		songList.addListSelectionListener(this);
		authorList = new JList<>();
		authorList.setModel(Autor.getAuthorListModel());
		albumList = new JList<>();
		albumList.setModel(Album.getAlbumListModel());
		playList = new JList<>();
		playList.setModel(Playlist.getPlaylistModel());
		player = new Reproductor();
		boolean nuevo = false;
		teclado = new Scanner(System.in);
		if (!(new File("./doc/"+DBNAME+".db")).exists()) nuevo = true;
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
		leerDB();
		if (System.getProperty("os.name").toLowerCase().contains("windows")) buscarDirectorio("c:\\users\\"+System.getProperty("user.name")+"\\music");
		else buscarDirectorio("~/Music");
		this.setSize(640, 480);
		this.setExtendedState(MAXIMIZED_BOTH);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		crearMenu();
		this.setContentPane(rellenarVentana());
		this.setVisible(true);
	}
	
	private void crearMenu() {
		menu = new JMenuBar();
		mArchivo = new JMenu("Archivo");
		miAbrirArchivo = new MiMenuItem("Abrir una canción...", this, "abrirArchivo");
		miAbrirDirectorio = new MiMenuItem("Abrir un directorio...", this, "abrirDirectorio");
		miSalir = new MiMenuItem("Salir", this, "salir");
		mArchivo.add(miAbrirArchivo);
		mArchivo.add(miAbrirDirectorio);
		mArchivo.addSeparator();
		mArchivo.add(miSalir);
		menu.add(mArchivo);
		this.setJMenuBar(menu);
	}
	
	private JPanel rellenarVentana() {
		JPanel panel = new JPanel(new BorderLayout());
		JScrollPane scroll = new JScrollPane();
		scroll.setViewportView(songList);
		panel.add(scroll, BorderLayout.CENTER);
		panel.add(rellenarNorte(), BorderLayout.NORTH);
		return panel;
	}
	
	private JPanel rellenarNorte() {
		JPanel panel = new JPanel(new GridLayout(1, 5));
		bAnterior = new MiBoton(new ImageIcon("previous.png"), this, "previous");
		bAnterior.setEnabled(false);
		bPlay = new MiBoton(new ImageIcon("play.png"), this, "play");
		bPlay.setEnabled(false);
		bPause = new MiBoton(new ImageIcon("pause.png"), this, "pause");
		bPause.setEnabled(false);
		bStop = new MiBoton(new ImageIcon("stop.png"), this, "stop");
		bStop.setEnabled(false);
		bSiguiente = new MiBoton(new ImageIcon("next.png"), this, "next");
		bSiguiente.setEnabled(false);
		panel.add(bAnterior);
		panel.add(bPlay);
		panel.add(bPause);
		panel.add(bStop);
		panel.add(bSiguiente);
		return panel;
	}
	
	public void buscarDirectorio(String ruta) {
		JFileChooser fileChooser = null;
		try {
			if (ruta == null) {
				fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				fileChooser.showOpenDialog(this);
				ruta = fileChooser.getSelectedFile().getAbsolutePath();
				if (ruta == null) throw new CancelException("Elección de directorio.");
			}
			final File folder = new File(ruta);
			for (final File fileEntry : folder.listFiles()) {
				String name = fileEntry.getName();
				if (name.toLowerCase().endsWith(".mp3")) crearCancionDesdeArchivo(fileEntry);
			}
			
		} catch (CancelException e) {
			System.out.println(e.getMessage());
		} catch (NullPointerException e) {
			System.out.println("Ha habido un error de puntero vacío.");
		}
	}
	
	public void leerDB() {
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
	
	public void buscarCancion() {
		System.out.print("Introduce la ruta de la cancion: ");
		File file = new File(teclado.nextLine());
		if (file.getName().toLowerCase().endsWith(".mp3")) crearCancionDesdeArchivo(file);
	}
	
	public void crearCancionDesdeArchivo(File file) {
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
			tag = detectarID3(rafile);
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
	
	public void verCanciones() {
		System.out.println("\nHe aquí la lista de todas las canciones:");
		ListIterator<Cancion> it = Cancion.getSongListModel().listIterator();
		while (it.hasNext()) {
			Cancion c = it.next();
			System.out.println(c);
		}
	}
	
	/*
	 * 
	 * Este método queda muy feo así. Mi objetivo es cambiarlo.
	 * 
	 */
	public AbstractID3 detectarID3(RandomAccessFile rfile) {
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
	
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		Main main = new Main();
	}
	
	public void empezarReproduccion() {
		for (int i=songList.getSelectedIndex(); i<songList.getModel().getSize(); i++) player.addSong(songList.getModel().getElementAt(i));
		for (int i=0; i<songList.getSelectedIndex(); i++) player.addSong(songList.getModel().getElementAt(i));
		bAnterior.setEnabled(false);
		bStop.setEnabled(true);
		bSiguiente.setEnabled(true);
		bPause.setEnabled(true);
		player.play();
	}
	
	public void detenerReproduccion() {
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

}
