package model;

import java.util.ArrayList;
import java.util.ListIterator;

import playerModel.MiListModel;

public class Playlist {
	private ArrayList<Song> list;
	@SuppressWarnings("unused")
	private String name;
	private static MiListModel<Playlist> playListModel = new MiListModel<Playlist>();
	
	public Playlist(String name) {
		this.name = name;
	}
	
	public Playlist(ArrayList<Song> list, String name) {
		this.list = list;
		this.name = name;
	}
	
	public static MiListModel<Playlist> getPlaylistModel() {
		return playListModel;
	}
	
	public ListIterator<Song> getListIterator() {
		return list.listIterator();
	}
}
