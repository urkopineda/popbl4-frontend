package language;

import java.util.HashMap;

public class LanguageList extends HashMap<String, String> {
	private static final long serialVersionUID = 6719033038450111359L;
	public static String LANG = "idioma";
	
	public void setIdioma(String idioma) {
		if (containsKey(LANG)) replace(LANG, idioma);
		else put(LANG, idioma);
	}
	
	public String getIdioma() {
		return get(LANG);
	}
	
}
