package bluetooth;

import utils.Heart;
import graphicinterface.TrainingUI;
import main.Configuration;

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
			System.out.println(Configuration.ppm);
			Heart.recalculatePPM();
			TrainingUI.ppmNumbers.setText(Configuration.ppm + " ppm");
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

}
