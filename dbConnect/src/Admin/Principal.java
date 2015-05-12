package Admin;

/**
 * Clase que inicializa toda la aplicación.
 * 
 * @author Runnstein Team
 */
public class Principal {
	/**
	 * Main de pruebas.
	 * 
	 * @param args
	 */
	@SuppressWarnings("unused")
	public static void main(String args[]) {
		Controller initApp = new Controller("172.17.100.77", 3306, "java", "javarunnsteinuser", "SCOTT");
	}
}
