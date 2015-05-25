package model;

import java.io.File;
import java.io.IOException;
import java.util.ListIterator;
import java.util.Map;
import java.util.Properties;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.UnsupportedAudioFileException;

import playermodel.MiListModel;
import database.SQLiteUtils;

public class Cancion extends Dato {
	private static final float _1M = 1000000f;
	private String titulo;
	private Autor autor;
	private Album album;
	private String ruta;
	private int duracion;
	private static MiListModel<Cancion> songListModel = new MiListModel<Cancion>();
	
	public static MiListModel<Cancion> getSongListModel() {
		return songListModel;
	}
	
	public Cancion(Properties pr) {
		super(pr);
		parseProperties(pr);
	}
	
	public Cancion(Properties pr, Album album) {
		super(pr);
		parseProperties(pr);
		this.setAlbum(album);
		this.setAutor(album.getAutor());
	}
	
	public String formattedLength() {
		return (this.duracion/60)+":"+((this.duracion%60<10)?"0"+this.duracion%60:this.duracion%60);
	}
	
	@Override
	public void searchID(SQLiteUtils conn) {
		Properties pr = new Properties();
		if (this.getAutor().getID()!=null) pr.put("AutorID", this.getAutor().getID());
		if (this.getAlbum().getID()!=null) pr.put("AlbumID", this.getAlbum().getID());
		pr.put("Ruta", this.getRuta());
		setID(conn.getID("Cancion", pr));
	}
	
	public static int calculateLength(File file) {
		int ret = 0;
		AudioFileFormat format;
		try {
			format = (new MpegAudioFileReader()).getAudioFileFormat(file);
			Map<String, Object> properties = format.properties();
			long l = (long) properties.get("duration");
			float f = ((float)l/_1M);
			ret = (int)(f);
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	public String getTitulo() {
		return titulo;
	}
	
	public Album getAlbum() {
		return album;
	}
	
	public Autor getAutor() {
		return this.autor;
	}
	
	public int getDuracion() {
		return this.duracion;
	}

	public String getRuta() {
		return ruta;
	}
	
	public void setAutor(Autor a) {
		this.autor = a;
		this.setProperty("Autor", (String) a.getProperty("Autor"));
	}
	
	public void setAlbum(Album a) {
		this.album = a;
		this.setProperty("Album", (String) a.getProperty("Album"));
	}
	
	public static Cancion comprobarCancionRepetida(Cancion c, MiListModel<Cancion> songListModel) {
		Cancion ret = null;
		ListIterator<Cancion> it = songListModel.listIterator();
		while (it.hasNext()) {
			Cancion aux = it.next();
			if (c.compareTo(aux)==0) ret = aux;
		}
		return ret;
	}
	
	public static Cancion comprobarCancionRepetida(String path, MiListModel<Cancion> songListModel) {
		Cancion ret = null;
		ListIterator<Cancion> it = songListModel.listIterator();
		while (it.hasNext()) {
			Cancion c = it.next();
			if (c.getRuta().compareTo(path)==0) ret = c;
		}
		return ret;
	}
	
	@Override
	public String toString() {
		return this.getTitulo()+" by "+this.getAutor()+" (from "+this.getAlbum()+") "+this.formattedLength()+"seg.";
	}

	@Override
	public int compareTo(Dato d) {
		Cancion c = (Cancion) d;
		if (c.getRuta().toLowerCase().equals(this.getRuta().toLowerCase()))
			if (this.getTitulo().toLowerCase().equals(c.getTitulo().toLowerCase())) {
				if (this.getAutor().equals(c.getAutor())) {
					return 0;
				} else return this.getAutor().compareTo(c.getAutor());
			} else return this.getTitulo().toLowerCase().compareTo(c.getTitulo().toLowerCase());
		else return c.getRuta().toLowerCase().compareTo(this.getRuta().toLowerCase());
	}

	@Override
	public String getTableColumns() {
		return "(AutorID, AlbumID, Duracion, Nombre, Ruta)";
	}

	@Override
	public String getColumnValues() {
		return "("+getAutor().id+","+getAlbum().getID()+","+duracion+",'"+titulo.replaceAll("'", "''")+"', '"+ruta.replaceAll("'", "''")+"')";
	}

	@Override
	public void parseProperties(Properties pr) {
		this.titulo = pr.getProperty("Titulo");
		this.ruta = pr.getProperty("Ruta");
		this.duracion = (pr.getProperty("Duracion")==null)?0:Integer.parseInt(pr.getProperty("Duracion"));
		try {this.id = Integer.parseInt(pr.getProperty("CancionID"));}
		catch (NumberFormatException e) {System.out.println("No se ha definidio ID de canción.");}
	}
	
	
}
