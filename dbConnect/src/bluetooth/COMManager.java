package bluetooth;

import graphicinterface.TrainingUI;

import java.sql.SQLException;

import main.Configuration;
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
	 * COMManager se ocupa de iniciar la conexi�n serie y abrir el puerto.
	 */
	public COMManager() {
		endThread = false;
		serial = new Serial();
		serial.openPort();
	}

	
	/**
	 * En este m�todo iniciamos el Thread de conexi�n por serie, y guardamos el valor de los PPMs que recibimos por serie.
	 */
	@Override
	public void run() {
		while(!endThread){
			Configuration.ppm = serial.receiveMessage();
			insertMuestra();
			Heart.recalculatePPM();
			if (TrainingUI.ppmNumbers != null) TrainingUI.ppmNumbers.setText(Configuration.ppm + " ppm");
		}
	}
	
	/**
	 * Este m�todo interrunpe y cierra la conexi�n por serie.
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
	private void insertMuestra() {
		try {
			Configuration.actualMuestra++;
			Configuration.conn.executeUpdate("INSERT INTO Muestra VALUES("+Configuration.actualMuestra+", "+Configuration.actualInterval+", "+Configuration.ppm+")");
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
}
