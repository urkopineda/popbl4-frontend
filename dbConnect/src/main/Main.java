package main;

import administration.Controller;

/**
 * Clase que inicializa toda la aplicación.
 * 
 * @author Runnstein Team
 */
public class Main {
	/**
	 * Main de pruebas.
	 * 
	 * @param args
	 */
	@SuppressWarnings("unused")
	public static void main(String args[]) {
		Controller initApp = new Controller("runnstein.mooo.com", 3026, "java", "javarunnsteinuser", "SCOTT");
	}
}
