package task;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Random;

import main.Configuration;
import model.Song;

public class RunnsteinCalculator extends Thread {
	private Song chosenSong;
	private volatile Song playingSong;
	private ArrayList<Song> songList;
	private ArrayList<Song> playedSongsList;
	private int count;
	private boolean paused;
	private boolean songChanged;
	private double lastBPM;
	
	public RunnsteinCalculator(ArrayList<Song> songList) {
		this.songList = songList;
		playedSongsList = new ArrayList<>();
		lastBPM = -1;
		paused = false;
		songChanged = false;
		start();
	}
	
	public Song getChosenSong() {
		return chosenSong;
	}
	
	public void fireSongChanged() {
		playedSongsList.add(chosenSong);
		count = 0;
		this.songChanged = true;
	}
	
	public void setPlayingSong(Song s) {
		this.playingSong = s;
		System.out.println(s);
	}
	
	public void setPaused(boolean state) {
		this.paused = state;
	}
	
	public void clearPlayedSongsList() {
		playedSongsList.clear();
	}
	
	public boolean getPaused() {
		return paused;
	}

	@Override
	public void run() {
		while (true) {
			try {
				if (playingSong != null || !songList.isEmpty()) {
					count = 0;
					//INSERT LUEGO
					if (Math.abs(lastBPM-Configuration.ppm)>10 || songChanged) {
						songChanged = false;
						ArrayList<Song> suitableSongs = makeSuitableSongList();
						suitableSongs = cleanAlreadyPlayedSongs(suitableSongs);
						chosenSong = suitableSongs.get((new Random()).nextInt(suitableSongs.size()));
						System.out.println("Se elige "+chosenSong);
					}
					lastBPM = Configuration.ppm;
					while (count<playingSong.getDuration().inMilliseconds()/Configuration.samplesPerSong) {
						sleep(1);
						if (playingSong == null) break;
						if (!paused) count++;
					}
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			super.run();
		}
	}
	
	private ArrayList<Song> makeSuitableSongList() {
		double songBPM = playingSong.getBPM();
		double heartRate = Configuration.ppm;
		double diff = heartRate - songBPM;
		int permittedDiff = 15;
		double diffCompare = 0;
		boolean onlyTakeGreaterBPM = true;
		ArrayList<Song> suitableSongs = new ArrayList<>();
		do {
			ListIterator<Song> it = songList.listIterator();
			while (it.hasNext()) {
				Song s = it.next();
				if (onlyTakeGreaterBPM) diffCompare = heartRate;
				else diffCompare = heartRate - permittedDiff;
				if (s.getBPM()<heartRate+permittedDiff && s.getBPM() > diffCompare) {
					suitableSongs.add(s);
				} else if (s.getBPM()<heartRate-permittedDiff) {
					suitableSongs.add(s);
				}
			}
			if (!onlyTakeGreaterBPM) permittedDiff+=5;
			onlyTakeGreaterBPM = false;
		} while (suitableSongs.isEmpty());
		return suitableSongs;
	}
	
	private ArrayList<Song> cleanAlreadyPlayedSongs(ArrayList<Song> list) {
		Song aux = list.get((new Random()).nextInt(list.size()));
		for (int i=0; i<list.size(); i++) {
			boolean exit = false;
			ListIterator<Song> it = playedSongsList.listIterator();
			while (it.hasNext() && !exit) {
				if (it.next().equals(list.get(i))) {
					list.remove(i);
					exit = true;
					i--;
				}
			}
		}
		if (list.isEmpty()) list.add(aux);
		return list;
	}
	
	@Override
	public void start() {
		//chosenSong = songList.get(1);
		count = 0;
		System.out.println("Comienza");
		super.start();
	}
	
}
