package task;

import graphicinterface.TrainingUI;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Random;

import main.Configuration;
import model.Song;
import bluetooth.COMManager;

public class RunnsteinCalculator extends Thread {
	private Song chosenSong;
	private Song playingSong;
	private ArrayList<Song> songList;
	private ArrayList<Song> playedSongsList;
	private int count;
	private boolean paused;
	private boolean songChanged;
	private double lastBPM;
	
	COMManager comManager = null;
	
	public RunnsteinCalculator(ArrayList<Song> songList) {
		comManager = new COMManager();
		comManager.start();
		this.songList = songList;
		playedSongsList = new ArrayList<>();
		lastBPM = -1;
		paused = false;
		songChanged = false;
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
				count = 0;
				System.out.println("Calculando...");
				//INSERT LUEGO
				comManager.getSerialComm().sendMessage();
				
				//
				if (Math.abs(lastBPM-Configuration.ppm)>10 || songChanged) {
					songChanged = false;
					ArrayList<Song> suitableSongs = makeSuitableSongList();
					suitableSongs = cleanAlreadyPlayedSongs(suitableSongs);
					chosenSong = suitableSongs.get((new Random()).nextInt(suitableSongs.size()));
				}
				System.out.println("Se elige "+chosenSong);
				lastBPM = Configuration.ppm;
				while (count<playingSong.getDuration().inMilliseconds()/Configuration.samplesPerSong) {
					sleep(1);
					if (!paused) count++;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			super.run();
		}
	}
	
	private ArrayList<Song> makeSuitableSongList() {
		double songBPM = playingSong.getBPM();
		double heartRate = Configuration.ppm;
		double diff = heartRate - songBPM;
		//Si diff<0 es que la cancion es "demasiado rapida"
		int permittedDiff = 15;
		double diffCompare = 0;
		boolean onlyTakeGreaterBPM = true;
		ArrayList<Song> suitableSongs = new ArrayList<>();
		do {
			ListIterator<Song> it = songList.listIterator();
			while (it.hasNext()) {
				Song s = it.next();
				/*if (onlyTakeGreaterBPM) diffCompare = (diff>0)?diff:-diff;
				else diffCompare = Math.abs(diff);
				if (Math.abs(diff)<permittedDiff) {
					if (s.getBPM()>heartRate+diffCompare) {
						suitableSongs.add(s);
					}
				} else if (Math.abs(diff)>=permittedDiff && diff<0) {
					if (s.getBPM()+permittedDiff<heartRate) {
						suitableSongs.add(s);
					}
				}*/
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
		chosenSong = songList.get(1);
		count = 0;
		System.out.println("Comienza");
		super.start();
	}
	
}
