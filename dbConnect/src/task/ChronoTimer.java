package task;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.Timer;

/**
 * Clase que se encarga del tiempo del cronómetro.
 * 
 * @author Runnstein Team
 */
public class ChronoTimer implements ActionListener{
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
		timer = new Timer(1000, this);
		timer.setActionCommand("start");
	}
	
	/**
	 * Contructor #1 de 'ChronoTimer', pide un JLabel para cambiar el valor a mostrar.
	 * 
	 * @param chronometerNumbers
	 */
	public ChronoTimer(JLabel chronometerNumbers) {
		this.chronometerNumbers = chronometerNumbers;
		timer = new Timer(1000, this);
		timer.setActionCommand("start");
	}
	
	public void startTimer() {
		timer.start();
	}
	
	public void stopTimer() {
		timer.stop();
		resetTimerValues();
		resetJLabelTextValue();
	}
	
	public void pauseTimer() {
		timer.stop();
	}
	
	public int getMinutes() {
		return minutes;
	}
	
	public int getSeconds() {
		return seconds;
	}
	
	public int getHours() {
		return hours;
	}
	
	public Timer getTimer() {
		return timer;
	}
	
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
	
	private void renameStrings() {
		if (seconds <= 9) sSeconds = "0"+seconds;
		else sSeconds = ((Integer) seconds).toString();
		if (minutes <= 9) sMinutes = "0"+minutes;
		else sMinutes = ((Integer) minutes).toString();
		if (hours <= 9) sHours = "0"+hours;
		else sHours = ((Integer) hours).toString();
	}
	
	private void resetTimerValues() {
		minutes = 0;
		seconds = 0;
		hours = 0;
	}
	
	private void resetJLabelTextValue() {
		chronometerNumbers.setText("00:00:00");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("start")){
			timerThreadMiliseconds();
			renameStrings();			
			if (chronometerNumbers != null) chronometerNumbers.setText(this.toString());
		}
	}
	
	public String toString() {
		return sHours+":"+sMinutes+":"+sSeconds;
	}
}
