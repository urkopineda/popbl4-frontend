package task;

import javax.swing.JLabel;

import main.Configuration;
import utils.Timer;

/**
 * Clase que se encarga del tiempo del cronómetro.
 * 
 * @author Runnstein Team
 */
public class ChronoTimer extends Thread{
	JLabel chronometerNumbers = null;
	Timer timer = null;
	int minutes = 0;
	int seconds = 0;
	int hours = 0;
	String sMinutes = null;
	String sSeconds = null;
	String sHours = null;
	
	/**
	 * Constructor #0 - Simplemente crea el objeto 'Timer'
	 */
	public ChronoTimer() {
		timer = new Timer(Configuration.timerPeriod);
	}
	
	/**
	 * Constructor #1 - Constructor que guarda el JLabel donde se contiene el valor del cronómetro para editarlo.
	 * 
	 * @param JLabel chronometerNumbers
	 */
	public ChronoTimer(JLabel chronometerNumbers) {
		this.chronometerNumbers = chronometerNumbers;
		timer = new Timer(Configuration.timerPeriod);
	}
	
	@Override
	public synchronized void start() {
		timer.start();
		super.start();
	}

	@Override
	public void run() {
		while(true){
			try {
				Thread.sleep(Configuration.timerPeriod/4);
			} catch (InterruptedException e) {
				e.getStackTrace();
			}
			if (timer.intervalHasFinished()) {
				executeAction();
				timer.setIntervalHasFinished(false);
			}
		}
	}
	
	@Override
	public void interrupt() {
		try {
			super.interrupt();
		} catch (Exception e) {
			e.getStackTrace();
		}
	}
	
	/**
	 * Incia el timer solo si ya no ha sido inicializado.
	 */
	public void startTimer() {
		if (!timer.isAlive()) timer.start();
		timer.setEnabled(true);
	}
	
	/**
	 * Para el timer (Resetea valores y para el timer)
	 */
	public void stopTimer() {
		timer.setEnabled(false);
		resetTimerValues();
		resetJLabelTextValue();
	}
	
	/**
	 * Pausa el timer (No resetea valores y para el timer)
	 */
	public void pauseTimer() {
		timer.setEnabled(false);
	}
	
	/**
	 * Devuelve los minutos.
	 * 
	 * @return int minutes
	 */
	public int getMinutes() {
		return minutes;
	}
	
	/**
	 * Devuelve los segundos.
	 * 
	 * @return int seconds
	 */
	public int getSeconds() {
		return seconds;
	}
	
	/**
	 * Devuelve las horas.
	 * 
	 * @return int hours
	 */
	public int getHours() {
		return hours;
	}
	
	/**
	 * Devuelve el objeto timer.
	 * 
	 * @return Timer timer
	 */
	public Timer getTimer() {
		return timer;
	}
	
	/**
	 * Se encarga de actualizar los valores de segundos, minutos y horas.
	 */
	private void timerThreadMiliseconds() {
		seconds++;
		if (seconds == 60) {
			seconds = 0;
			minutes++;
		}
		if (minutes == 60) {
			hours++;
		}
	}
	
	/**
	 * Se encarga de renombrar los Strings que se utilizan para crear el JLabel segundo los valores int del timer.
	 */
	private void renameStrings() {
		if (seconds <= 9) sSeconds = "0"+seconds;
		else sSeconds = ((Integer) seconds).toString();
		if (minutes <= 9) sMinutes = "0"+minutes;
		else sMinutes = ((Integer) minutes).toString();
		if (hours <= 9) sHours = "0"+hours;
		else sHours = ((Integer) hours).toString();
	}
	
	/**
	 * Resetea los valores int del timer.
	 */
	private void resetTimerValues() {
		minutes = 0;
		seconds = 0;
		hours = 0;
	}
	
	/**
	 * Establece el JLabel del timer a 0.
	 */
	private void resetJLabelTextValue() {
		chronometerNumbers.setText("00:00:00");
	}
	
	/**
	 * Ejecuta esta acción cada vez que el cronómetro salta.
	 */
	public void executeAction() {
		timerThreadMiliseconds();
		renameStrings();			
		if (chronometerNumbers != null) chronometerNumbers.setText(this.toString());
	}
	
	/**
	 * Devuelve el Strign que corresponde a este objeto.
	 */
	public String toString() {
		return sHours+":"+sMinutes+":"+sSeconds;
	}
}
