package task;

import model.Song;
import utils.Player;

public class SongLengthTimer extends Thread {
	private volatile boolean isPlaying = false;
	private int count = 0;
	private volatile Song playing = null;
	private Player player = null;
	
	public SongLengthTimer(Player player) {
		this.player = player;
		System.out.println("Creado");
		start();
	}
			
	@Override
	public void run() {
		System.out.println("Comenzemos calculando");
		while (true) {
			try {
				if (isPlaying && playing != null) {
					System.out.println(count);
					sleep(1000);
					count++;
					if (count == playing.getDuration().inSeconds()) {
						player.skipForward();
						count = 0;
					}
				}
			} catch (Exception e) {
				System.out.println("Ha habido un error con el Thread");
			}
		}
	}
	
	public void setIsPlaying(boolean value) {
		isPlaying = value;
	}
	
	public int getCount() {
		return count;
	}
	
	public void restartCount() {
		this.count = 0;
	}
	
	public void setPlaying(Song song) {
		this.playing = song;
	}
	
}