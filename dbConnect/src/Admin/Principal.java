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
	public static void main(String args[]) {
		DataBaseBasics newConnection = new DataBaseBasics("172.17.100.77", 3306, "java", "javarunnsteinuser", "SCOTT");
		@SuppressWarnings("unused")
		UI newUI = new UI(newConnection);
	}
}
