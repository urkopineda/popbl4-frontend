package model;

import java.sql.SQLException;
import java.util.ArrayList;

import playerModel.MiLoadScreen;
import database.MySQLUtils;

public class AllData {
	Entrenamiento entrenamiento = null;
	ArrayList<Intervalo> intervalos = null;
	ArrayList<Muestra> muestras = null;
	
	public AllData(Entrenamiento entrenamiento, ArrayList<Intervalo> intervalos, ArrayList<Muestra> muestras) {
		this.entrenamiento = entrenamiento;
		this.intervalos = intervalos;
		this.muestras = muestras;
	}
	
	public void dumpData(MiLoadScreen load) {
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
