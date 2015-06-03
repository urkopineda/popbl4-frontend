package model;

import java.io.File;
import java.io.IOException;
import java.util.ListIterator;
import java.util.Map;
import java.util.Properties;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.UnsupportedAudioFileException;

import playerModel.MiListModel;
import javazoom.spi.mpeg.sampled.file.MpegAudioFileReader;
import database.SQLiteUtils;

public class Song extends DataType {
	private static final float _1M = 1000000f;
	private String title;
	private Author author;
	private Album album;
	private String path;
	private Duration duration;
	private static MiListModel<Song> songListModel = new MiListModel<Song>();
	
	public static MiListModel<Song> getSongListModel() {
		return songListModel;
	}
	
	public Song(Properties pr) {
		super(pr);
		parseProperties(pr);
	}
	
	public Song(Properties pr, Album album) {
		super(pr);
		parseProperties(pr);
		this.setAlbum(album);
		this.setAuthor(album.getAuthor());
	}
	
	@Override
	public void searchID(SQLiteUtils conn) {
		Properties pr = new Properties();
		if (this.getAuthor().getID()!=null) pr.put("AutorID", this.getAuthor().getID());
		if (this.getAlbum().getID()!=null) pr.put("AlbumID", this.getAlbum().getID());
		pr.put("Ruta", this.getPath());
		setID(conn.getID("Cancion", pr));
	}
	
	public static int calculateLength(File file) {
		int ret = 0;
		AudioFileFormat format = null;
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
	
	public String getTitle() {
		return title;
	}
	
	public Album getAlbum() {
		return album;
	}
	
	public Author getAuthor() {
		return this.author;
	}
	
	public Duration getDuration() {
		return this.duration;
	}

	public String getPath() {
		return this.path;
	}
	
	public void setAuthor(Author a) {
		this.author = a;
		this.setProperty("Author", (String) a.getProperty("Author"));
	}
	
	public void setAlbum(Album a) {
		this.album = a;
		this.setProperty("Album", (String) a.getProperty("Album"));
	}
	
	public static Song checkDuplicateSong(Song c, MiListModel<Song> songListModel) {
		Song ret = null;
		ListIterator<Song> it = songListModel.listIterator();
		while (it.hasNext()) {
			Song aux = it.next();
			if (c.compareTo(aux)==0) ret = aux;
		}
		return ret;
	}
	
	public static Song checkDuplicateSong(String path, MiListModel<Song> songListModel) {
		Song ret = null;
		ListIterator<Song> it = songListModel.listIterator();
		while (it.hasNext()) {
			Song c = it.next();
			if (c.getPath().compareTo(path)==0) ret = c;
		}
		return ret;
	}
	
	@Override
	public String toString() {
		return this.getTitle()+" by "+this.getAuthor()+" (from "+this.getAlbum()+") "+this.getDuration()+"seg.";
	}

	@Override
	public int compareTo(DataType d) {
		Song c = (Song) d;
		if (c.getPath().toLowerCase().equals(this.getPath().toLowerCase()))
			if (this.getTitle().toLowerCase().equals(c.getTitle().toLowerCase())) {
				if (this.getAuthor().equals(c.getAuthor())) {
					return 0;
				} else return this.getAuthor().compareTo(c.getAuthor());
			} else return this.getTitle().toLowerCase().compareTo(c.getTitle().toLowerCase());
		else return c.getPath().toLowerCase().compareTo(this.getPath().toLowerCase());
	}

	@Override
	public String getTableColumns() {
		return "(AutorID, AlbumID, Duracion, Nombre, Ruta)";
	}

	@Override
	public String getColumnValues() {
		return "("+getAuthor().getID()+","+getAlbum().getID()+","+duration.inSeconds()+",'"+title.replaceAll("'", "''")+"', '"+path.replaceAll("'", "''")+"')";
	}

	@Override
	public void parseProperties(Properties pr) {
		this.title = pr.getProperty("Title");
		this.path = pr.getProperty("Path");
		this.duration = new Duration((pr.getProperty("Duration")==null)?0:Integer.parseInt(pr.getProperty("Duration")));
		try {this.id = Integer.parseInt(pr.getProperty("SongID"));}
		catch (NumberFormatException e) {System.out.println("No se ha definidio ID de canción.");}
	}
	
	
}
