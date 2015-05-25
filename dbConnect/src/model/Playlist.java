package model;

import java.util.ArrayList;
import java.util.ListIterator;

import playermodel.MiListModel;

public class Playlist {
	private ArrayList<Cancion> lista;
	private String nombre;
	private static MiListModel<Playlist> playListModel = new MiListModel<Playlist>();
	
	public Playlist(String nombre) {
		this.nombre = nombre;
	}
	
	public Playlist(ArrayList<Cancion> lista, String nombre) {
		this.lista = lista;
		this.nombre = nombre;
	}
	
	public static MiListModel<Playlist> getPlaylistModel() {
		return playListModel;
	}
	public ListIterator<Cancion> getListIterator() {
		return lista.listIterator();
	}
}
