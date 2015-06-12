package task;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import main.Configuration;
import model.Entrenamiento;
import model.Intervalo;
import model.Muestra;
import playerModel.MiLoadScreen;
import database.MySQLUtils;

public class Dump {
	public static Entrenamiento entrenamiento = null;
	static ArrayList<Intervalo> intervalos = null;
	static ArrayList<Muestra> muestras = null;
	
	public Dump() {}
	
	public static void dumpData(MiLoadScreen load, String duration) {
		entrenamiento.duracion = duration;
		extractFromSQLite();
		addToMySQL(load);
		deleteFromSQLite();
	}
	
	private static void deleteFromSQLite() {
		try {
			Configuration.conn.executeUpdate("DELETE FROM INTERVALO");
			Configuration.conn.executeUpdate("DELETE FROM MUESTRA");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private static void extractFromSQLite() {
		ResultSet rsI = Configuration.conn.executeQuery("SELECT * FROM INTERVALO");
		ResultSet rsM = Configuration.conn.executeQuery("SELECT * FROM MUESTRA");
		try {
			while (rsI.next()) {
				intervalos.add(new Intervalo(rsI.getInt(1), rsI.getDouble(3), rsI.getString(4)));
			}
			while (rsM.next()) {
				muestras.add(new Muestra(rsM.getInt(1), rsM.getInt(2), rsM.getInt(3)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private static void addToMySQL(MiLoadScreen load) {
		MySQLUtils db = new MySQLUtils();
		try {
			db.openDataBase();
			load.setWorkToMake(1 + intervalos.size() + muestras.size());
			db.exeStmt(entrenamiento.getInsert());
			load.progressHasBeenMade("Entrenamiento.", 1);
			for (int i = 0; i != intervalos.size(); i++) {
				db.exeStmt(intervalos.get(i).getInsert());
				load.progressHasBeenMade("Intervalo Nº"+i+".", 1);
			}
			for (int i = 0; i != muestras.size(); i++) {
				db.exeStmt(muestras.get(i).getInsert());
				load.progressHasBeenMade("Muestra Nº"+i+".", 1);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			load.closeScreen();
			try {
				db.closeDataBase();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
