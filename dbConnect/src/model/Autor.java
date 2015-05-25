package model;

import java.util.ListIterator;
import java.util.Properties;

import database.SQLiteUtils;
import playermodel.MiListModel;

public class Autor extends Dato {
	private String nombre;
	private static MiListModel<Autor> authorListModel = new MiListModel<Autor>();
	
	public Autor(Properties pr) {
		super(pr);
		parseProperties(pr);
	}
	
	public static MiListModel<Autor> getAuthorListModel() {
		return authorListModel;
	}
	
	public String getNombre() {
		return this.nombre;
	}
	
	public static Autor comprobarAutorRepetido(Autor a, MiListModel<Autor> authorListModel) {
		Autor ret = null;
		ListIterator<Autor> it = authorListModel.listIterator();
		while (it.hasNext()) {
			Autor aux = it.next();
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
		return "('"+getNombre().replaceAll("'","''")+"')";
	}

	@Override
	public void searchID(SQLiteUtils conn) {
		Properties pr = new Properties();
		pr.put("Nombre", getNombre());
		setID(conn.getID("Autor", pr));
	}

	@Override
	public String toString() {
		return nombre;
	}

	@Override
	public void parseProperties(Properties pr) {
		this.nombre = pr.getProperty("Autor");
		try {setID(Integer.parseInt(pr.getProperty("AutorID")));}
		catch (NumberFormatException e) {System.out.println("No se ha conseguido ID de autor.");}
	}

	@Override
	public int compareTo(Dato a) {
		if (a instanceof Autor) return ((Autor)a).getNombre().toLowerCase().compareTo(this.getNombre().toLowerCase());
		else return -1;
	}

}
