package id3;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ListIterator;

import javax.swing.JPanel;

import model.Cancion;
import model.Playlist;

@SuppressWarnings("serial")
public class Reproductor extends JPanel {
	private MP3Player player;
	private ArrayList<Cancion> lista;
	private int n;
	private Cancion enReproduccion;
	
	public Reproductor() {
		player = new MP3Player();
		lista = new ArrayList<>();
		n = 0;
		enReproduccion = null;
	}
	
	public void addSong(Cancion c) {
		player.addToPlayList(new File(c.getRuta()));
		lista.add(c);
	}
	
	public void vaciarLista() {
		player.getPlayList().clear();
		lista.clear();
		enReproduccion = null;
		n=0;
	}
	
	private String parseURL(URL url) {
		String ret = url.toString();
		ret = ret.replaceAll("%20", " ");
		ret = ret.replaceFirst("file:/", "");
		ret = ret.replaceAll("/", "\\\\");
		return ret;
	}
	
	public void play() {
		player.play();
		//String path = ((URL)player.getPlayList().get(n)).toString().replaceAll("file:/", "").replaceAll("/","\\\\");
		String path = parseURL((URL)player.getPlayList().get(n));
		enReproduccion = Cancion.comprobarCancionRepetida(path, Cancion.getSongListModel());
		System.out.println(enReproduccion);
	}
	
	public void pause() {
		player.pause();
	}
	
	public void stop() {
		player.stop();
	}
	
	public Cancion getEnReproduccion() {
		return enReproduccion;
	}
	
	public void addPlaylist(Playlist list) {
		ListIterator<Cancion> it = list.getListIterator();
		while (it.hasNext()) {
			Cancion c = it.next();
			addSong(c);
		}
	}
	
	public boolean isPaused() {
		return player.isPaused();
	}
	
	public boolean isStopped() {
		return player.isStopped();
	}
	
	public boolean skipForward() {
		player.skipForward();
		enReproduccion = lista.get(++n);
		System.out.println(enReproduccion);
		if (n==lista.size()-1) return false;
		return true;
	}
	
	public boolean skipBackward() {
		player.skipBackward();
		enReproduccion = lista.get(--n);
		System.out.println(enReproduccion);
		if (n==0) return false;
		return true;
	}
}
