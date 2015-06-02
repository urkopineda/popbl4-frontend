package model;

import java.util.ListIterator;
import java.util.Properties;

import playerModel.MiListModel;
import database.SQLiteUtils;

public class Album extends DataType {
	private String title;
	private Author Author;
	private static MiListModel<Album> albumListModel = new MiListModel<Album>();

	public Album(Properties pr, Author author) {
		super(pr);
		parseProperties(pr);
		this.setAuthor(author);
	}
	
	public static MiListModel<Album> getAlbumListModel() {
		return albumListModel;
	}
	
	public void setAuthor(Author a) {
		this.Author = a;
		this.setProperty("Author", (String) a.getProperty("Author"));
	}

	public String getTitle() {
		return this.title;
	}
	
	public Author getAuthor() {
		return this.Author;
	}
	
	public static Album checkDuplicateAlbum(Album a, MiListModel<Album> albumListModel) {
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
		return title;
	}

	@Override
	public void searchID(SQLiteUtils conn) {
		Properties pr = new Properties();
		pr.put("Nombre", this.getTitle());
		if (this.getAuthor().getID()!=null) pr.put("AuthorID", this.getAuthor().getID());
		setID(conn.getID("Album", pr));
	}

	@Override
	public String getTableColumns() {
		return "(Nombre, AutorID)";
	}

	@Override
	public String getColumnValues() {
		return "('"+getTitle().replaceAll("'", "''")+"',"+getAuthor().getID()+")";
	}

	@Override
	public void parseProperties(Properties pr) {
		this.title = pr.getProperty("Album");
		try {this.setID(Integer.parseInt(pr.getProperty("AlbumID")));}
		catch (NumberFormatException e) {System.out.println("No se ha definido ID de álbum");}
	}

	@Override
	public int compareTo(DataType a) {
		if (a instanceof Album) return this.getTitle().toLowerCase().compareTo(((Album)a).getTitle().toLowerCase());
		else return -1;
	}

}
