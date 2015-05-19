package utils;

/**
 * Hilo del timer (Conseguimos que sea más preciso)
 * 
 * @author Runnstein Team
 */
public class Timer extends Thread {
	int interval;
	volatile boolean intervalHasFinished, isEnabled;
	volatile boolean endThread;
	
	/**
	 * Contructor del hilo Timer que consigue el intervalo a calcular.
	 * 
	 * @param interval
	 */
	public Timer(int interval) {
		this.interval = interval;
		intervalHasFinished = false;
		endThread = false;
		isEnabled = true;
	}
	
	@Override
	public void run() {
		while(!endThread){
			try {
				Thread.sleep(interval);
			} catch (InterruptedException e) {}
			if(isEnabled) intervalHasFinished = true;
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
