package task;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import main.Configuration;
import utils.Heart;

public class HeartAnimator extends Thread{
	JLabel ppmImage = null;
	Heart heart = null;
	Thread changeImage = new Thread();
	
	/**
	 * Constructor #1 - Constructor que guarda el JLabel donde se contiene el valor del cronómetro para editarlo.
	 * 
	 * @param JLabel chronometerNumbers
	 */
	public HeartAnimator(JLabel ppmImage) {
		this.ppmImage = ppmImage;
		heart = new Heart();
	}
	
	@Override
	public synchronized void start() {
		heart.start();
		super.start();
	}

	@Override
	public void run() {
		while(true){
			try {
				Thread.sleep(Configuration.timerPeriod / 4);
			} catch (InterruptedException e) {
				e.getStackTrace();
			}
			if (heart.intervalHasFinished()) {
				executeAction();
				heart.setIntervalHasFinished(false);
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
	 * Ejecuta esta acción cada vez que el cronómetro salta.
	 */
	@SuppressWarnings("static-access")
	public void executeAction() {
		ppmImage.setIcon(new ImageIcon("img/heart_on_up.png"));
		try {
			if (Configuration.ppm < 120) changeImage.sleep(250);
			else if ((Configuration.ppm > 120) &&  (Configuration.ppm < 160))changeImage.sleep(150);
			else changeImage.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		ppmImage.setIcon(new ImageIcon("img/heart_on_down.png"));
	}
}
