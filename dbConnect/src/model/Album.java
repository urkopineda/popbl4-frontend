package model;

import java.util.ListIterator;
import java.util.Properties;

import playermodel.MiListModel;
import database.SQLiteUtils;

public class Album extends Dato {
	private String nombre;
	private Autor autor;
	private static MiListModel<Album> albumListModel = new MiListModel<Album>();

	public Album(Properties pr, Autor autor) {
		super(pr);
		parseProperties(pr);
		this.setAutor(autor);
	}
	
	public static MiListModel<Album> getAlbumListModel() {
		return albumListModel;
	}
	
	public void setAutor(Autor a) {
		this.autor = a;
		this.setProperty("Autor", (String) a.getProperty("Autor"));
	}

	public String getNombre() {
		return this.nombre;
	}
	
	public Autor getAutor() {
		return this.autor;
	}
	
	public static Album comprobarAlbumRepetido(Album a, MiListModel<Album> albumListModel) {
		Album ret = null;
		ListIterator<Album> it = albumListModel.listIterator();
		while (it.hasNext()) {
			Album aux = it.next();
			if (a.compareTo(aux)==0) ret = aux;
		}
		return ret;
	}

	@Override
	public String toString() {
		return nombre;
	}

	@Override
	public void searchID(SQLiteUtils conn) {
		Properties pr = new Properties();
		pr.put("Nombre", this.getNombre());
		if (this.getAutor().getID()!=null) pr.put("AutorID", this.getAutor().getID());
		setID(conn.getID("Album", pr));
	}

	@Override
	public String getTableColumns() {
		return "(Nombre, AutorID)";
	}

	@Override
	public String getColumnValues() {
		return "('"+getNombre().replaceAll("'", "''")+"',"+getAutor().getID()+")";
	}

	@Override
	public void parseProperties(Properties pr) {
		this.nombre = pr.getProperty("Album");
		try {this.setID(Integer.parseInt(pr.getProperty("AlbumID")));}
		catch (NumberFormatException e) {System.out.println("No se ha definido ID de álbum");}
	}

	@Override
	public int compareTo(Dato a) {
		if (a instanceof Album) return this.getNombre().toLowerCase().compareTo(((Album)a).getNombre().toLowerCase());
		else return -1;
	}

}
