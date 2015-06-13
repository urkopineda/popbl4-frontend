package model;

import java.sql.ResultSet;
import java.sql.SQLException;

import main.Configuration;
import database.MySQLUtils;

/**
 * Clase que genera un objeto para mostrar Records.
 * 
 * @author Urko
 *
 */
public class RecordsData {
	public int maxDuration = 0;
	public int maxPPM = 0;
	public int maxBPM = 0;
	public int totalTrainings = 0;
	public int totalDuration = 0;
	
	public RecordsData() {
		MySQLUtils db = new MySQLUtils();
		try {
			db.openDataBase();
			ResultSet rsEntrenamiento = db.exeQuery("SELECT * FROM ENTRENAMIENTO WHERE UsuarioID = "+Configuration.userID);
			while (rsEntrenamiento.next()) {
				totalTrainings++;
				String time [] = rsEntrenamiento.getString(4).split(":");
				int duration = (Integer.parseInt(time[0]) * 60) + Integer.parseInt(time[1]);
				totalDuration += duration;
				if (duration > maxDuration) maxDuration = duration;
				ResultSet rsIntervalo = db.exeQuery("SELECT * FROM INTERVALO WHERE EntrenamientoID = "+rsEntrenamiento.getInt(1));
				while (rsIntervalo.next()) {
					int bpm = rsIntervalo.getInt(3);
					if (bpm > maxBPM) maxBPM = bpm;
					ResultSet rsMuestra = db.exeQuery("SELECT * FROM MUESTRA WHERE IntervaloID = "+rsIntervalo.getInt(1));
					while (rsMuestra.next()) {
						int ppm = rsMuestra.getInt(3);
						if (ppm > maxPPM) maxPPM = ppm;
					}
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("ERROR: "+e.getSQLState()+" - "+e.getMessage()+".");
		} finally {
			try {
				db.closeDataBase();
			} catch (SQLException e) {
				System.out.println("ERROR: "+e.getSQLState()+" - "+e.getMessage()+".");
				e.printStackTrace();
			}
		}
	}
}
