package language;

import java.util.ArrayList;

import main.Configuration;

/**
 * Clase con métodos y elementos estáticos que se usa para referenciar los elementos LanguageList que corresponden a todos lso strings de cada idioma.
 * @author unaipme
 *
 */
public class Strings {
	public static ArrayList<LanguageList> lista = new ArrayList<>();
	
	public final static String get(String key) {
		return lista.get(Configuration.lang).get(key);
	}
	
	public static boolean changeLanguage(String language) {
		if (language.equals("Euskera")) {
			if (Configuration.lang == 0) return false;
			else Configuration.lang = 0; return true;
		} else if (language.equals("Castellano")) {
			if (Configuration.lang == 1) return false;
			else Configuration.lang = 1; return true;
		} else if (language.equals("English")) {
			if (Configuration.lang == 2) return false;
			else Configuration.lang = 2; return true;
		}
		return true;
	}
}
