package language;

import java.util.ArrayList;

import main.Configuration;

public class Strings {
	public static ArrayList<LanguageList> lista = new ArrayList<>();
	
	public final static String get(String key) {
		return lista.get(Configuration.lang).get(key);
	}
}
