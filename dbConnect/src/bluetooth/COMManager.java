package bluetooth;

import graphicinterface.TrainingUI;

import java.sql.SQLException;

import main.Configuration;
import model.Muestra;
import task.Dump;
import utils.Heart;

/**
 * COMManager es el controlador de serie que utilizan otras clases en todo el programa.
 * 
 * @author Urko
 *
 */
public class COMManager extends Thread {
	Serial serial;
	volatile boolean endThread;
	
	/**
	 * COMManager se ocupa de iniciar la conexión serie y abrir el puerto.
	 */
	public COMManager() {
		endThread = false;
		serial = new Serial();
		serial.openPort();
	}

	
	/**
	 * En este método iniciamos el Thread de conexión por serie, y guardamos el valor de los PPMs que recibimos por serie.
	 */
	@Override
	public void run() {
		while(!endThread){
			Configuration.ppm = serial.receiveMessage();
			// insertMuestra();
			Dump.muestras.add(new Muestra(++Configuration.actualMuestra, Configuration.actualInterval, Configuration.ppm));
			Heart.recalculatePPM();
			if (TrainingUI.ppmNumbers != null) TrainingUI.ppmNumbers.setText(Configuration.ppm + " ppm");
		}
	}
	
	/**
	 * Este método interrunpe y cierra la conexión por serie.
	 */
	@Override
	public void interrupt() {
		endThread = true;
		serial.closePort();
		super.interrupt();
	}

	/**
	 * Devuelve el objeto Serial utilizado por el Thread.
	 * 
	 * @return
	 */
	public Serial getSerialComm() {
		return serial;
	}
	
	/**
	 * Esta clase se ocupa de guardar el valor de los PPM en la base de datos SQLite.
	 */
	@SuppressWarnings("unused")
	private void insertMuestra() {
		try {
			Configuration.actualMuestra++;
			Configuration.conn.executeUpdate("INSERT INTO Muestra VALUES("+Configuration.actualMuestra+", "+Configuration.actualInterval+", "+Configuration.ppm+")");
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
}
