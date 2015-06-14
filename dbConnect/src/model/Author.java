package model;

import java.util.ListIterator;
import java.util.Properties;

import playerModel.MiListModel;
import database.SQLiteUtils;

/**
 * Clase que corresponde al autor (compositor, intérprete) de un álbum y, por consecuencia, de una canción.
 * @author unaipme
 *
 */
public class Author extends DataType {
	private String name;
	private static MiListModel<Author> authorListModel = new MiListModel<Author>();
	
	public Author(Properties pr) {
		super(pr);
		parseProperties(pr);
	}
	
	public static MiListModel<Author> getAuthorListModel() {
		return authorListModel;
	}
	
	public String getName() {
		return this.name;
	}
	
	/**
	 * Método estático que comprueba si una autor existe.
	 * @param a: Autor a buscar
	 * @param authorListModel: Modelo en el que buscar al autor.
	 * @return Devuelve el autor si está repetida, sino devuelve null.
	 */
	public static Author checkDuplicateAuthor(Author a, MiListModel<Author> authorListModel) {
		Author ret = null;
		ListIterator<Author> it = authorListModel.listIterator();
		while (it.hasNext()) {
			Author aux = it.next();
			if (a.compareTo(aux)==0) ret = aux;
		}
		return ret;
	}
	
	@Override
	public String getTableColumns() {
		return "(Nombre)";
	}

	@Override
	public String getColumnValues() {
		return "('"+getName().replaceAll("'","''")+"')";
	}

	@Override
	public void searchID(SQLiteUtils conn) {
		Properties pr = new Properties();
		pr.put("Nombre", getName());
		setID(conn.getID("Autor", pr));
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public void parseProperties(Properties pr) {
		this.name = pr.getProperty("Author");
		try {setID(Integer.parseInt(pr.getProperty("AuthorID")));}
		catch (NumberFormatException e) {System.out.println("No se ha conseguido ID de Author.");}
	}

	@Override
	public int compareTo(DataType a) {
		if (a instanceof Author) return ((Author)a).getName().toLowerCase().compareTo(this.getName().toLowerCase());
		else return -1;
	}

}
