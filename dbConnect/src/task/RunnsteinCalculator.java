package task;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Random;

import main.Configuration;
import model.Song;
import bluetooth.COMManager;

/**
 * Aquí está la innovación de Runnstein. Clase que se ocupa de calcular a cada momento la canción más adecuada, como está explicado en la documentación. Se ejecuta en segundo plano constantemente.
 * @author unaipme
 *
 */
public class RunnsteinCalculator extends Thread {
	private Song chosenSong;
	private volatile Song playingSong;
	private ArrayList<Song> songList;
	private ArrayList<Song> playedSongsList;
	private int count;
	private boolean paused;
	private boolean songChanged;
	private double lastBPM;
	
	COMManager comManager = null;
	 /**
	  * 
	  * @param songList: Lista de todas las canciones.
	  */
	public RunnsteinCalculator(ArrayList<Song> songList) {
		comManager = Configuration.com;
		if (comManager != null) comManager.start();
		this.songList = songList;
		playedSongsList = new ArrayList<>();
		lastBPM = -1;
		paused = false;
		songChanged = false;
		start();
	}
	
	public ArrayList<Song> getPlayedSongsList() {
		return playedSongsList;
	}
	
	public void comCreated() {
		comManager = Configuration.com;
		comManager.start();
	}
	
	public Song getChosenSong() {
		return chosenSong;
	}
	
	/**
	 * Método que sirve para que la calculadora sepa que se ha cambiado la canción.
	 */
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

	/**
	 * Bucle en el que se ejecuta el algoritmo que calcula la canción.
	 */
	@Override
	public void run() {
		while (true) {
			// System.out.println(playingSong);
			try {
				if (playingSong != null && !songList.isEmpty()) {
					count = 0;
					if (Configuration.sensorState) comManager.getSerialComm().sendMessage();
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
				e.printStackTrace();
			}
			super.run();
		}
	}
	
	/**
	 * Método que prepara la lista de canciones compatibles con el estado del corredor.
	 * @return ArrayList de las canciones.
	 */
	private ArrayList<Song> makeSuitableSongList() {
		double songBPM = playingSong.getBPM();
		double heartRate = Configuration.ppm;
		@SuppressWarnings("unused")
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
	
	/**
	 * Método para filtrar las canciones que ya se han reproducido, teniendo en cuenta como mucho las diez últimas canciones.
	 * @param list: Lista de canciones compatibles, ya filtrada.
	 * @return ArrayList de canciones compatibles y no reproducidas.
	 */
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
