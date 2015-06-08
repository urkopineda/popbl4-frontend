package utils;

import main.Configuration;

public class Heart extends Thread {
	static int miliseconds = 60000;
	static double interval = 0;
	volatile boolean intervalHasFinished = false;
	volatile boolean isEnabled = false;
	volatile boolean endThread = false;
	
	/**
	 * Contructor del hilo Timer que consigue el intervalo a calcular.
	 * 
	 * @param interval
	 */
	public Heart() {
		if (Configuration.ppm == 0) interval = miliseconds / 1;
		else interval = miliseconds / Configuration.ppm;
		System.out.println(interval);
		intervalHasFinished = false;
		endThread = false;
		isEnabled = true;
	}
	
	/**
	 * Recalcula el intervalo de las pulsaciones.
	 */
	public static void recalculatePPM() {
		if (Configuration.ppm == 0) interval = miliseconds / 1;
		else interval = miliseconds / Configuration.ppm;
	}
	
	@Override
	public void run() {
		while(!endThread){
			try {
				Thread.sleep((int) interval);
			} catch (InterruptedException e) {}
			if (isEnabled) intervalHasFinished = true;
		}
	}

	@Override
	public void interrupt() {
		endThread = true;
		super.interrupt();
	}
	
	/**
	 * Devuelve el estado del hilo.
	 * 
	 * @return boolean isEnabled
	 */
	public boolean isEnabled() {
		return isEnabled;
	}
	
	/**
	 * Establece el estado del hilo a habilitado (true) o deshabilitado (false).
	 * 
	 * @param boolean isEnabled
	 */
	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
	
	/**
	 * Devuelve si el intervalo a concluido o no.
	 * 
	 * @return boolean intervalHasFinished
	 */
	public boolean intervalHasFinished() {
		return intervalHasFinished;
	}
	
	/**
	 * Establece que el intervalo a finalizado (true) o no (false).
	 * 
	 * @param boolean intervalHasFinished
	 */
	public void setIntervalHasFinished(boolean intervalHasFinished) {
		this.intervalHasFinished = intervalHasFinished;
	}
}
