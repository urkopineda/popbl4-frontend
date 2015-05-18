package utils;

public class Timer extends Thread {
	int interval;
	volatile boolean intervalHasFinished, isEnabled;
	volatile boolean endThread;
	
	public Timer(int interval){
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
	
	public boolean isEnabled() {
		return isEnabled;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public boolean intervalHasFinished() {
		return intervalHasFinished;
	}

	public void setIntervalHasFinished(boolean intervalHasFinished) {
		this.intervalHasFinished = intervalHasFinished;
	}
}
