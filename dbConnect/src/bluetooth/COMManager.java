package bluetooth;

import graphicinterface.TrainingUI;

import java.sql.SQLException;

import main.Configuration;
import utils.Heart;

public class COMManager extends Thread {
	Serial serial;
	volatile boolean endThread;
	
	public COMManager() {
		endThread = false;
		serial = new Serial();
		serial.openPort();
	}

	
	@Override
	public void run() {
		while(!endThread){
			Configuration.ppm = serial.receiveMessage();
			insertMuestra();
			Heart.recalculatePPM();
			if (TrainingUI.ppmNumbers != null) TrainingUI.ppmNumbers.setText(Configuration.ppm + " ppm");
		}
	}
	
	@Override
	public void interrupt() {
		endThread = true;
		serial.closePort();
		super.interrupt();
	}

	public Serial getSerialComm() {
		return serial;
	}
	
	private void insertMuestra() {
		try {
			Configuration.actualMuestra++;
			Configuration.conn.executeUpdate("INSERT INTO Muestra VALUES("+Configuration.actualMuestra+", "+Configuration.actualInterval+", "+Configuration.ppm+")");
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
}
