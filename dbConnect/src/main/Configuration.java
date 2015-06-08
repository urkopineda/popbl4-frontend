package main;

public class Configuration {
	// Variables esenciales.
	public final static String dbSQLite = "runnstein";
	public static final int timerPeriod = 1000;
	public static final String dbUrl = "runnstein.mooo.com";
	public static final int port = 3026;
	public static final String user = "java";
	public static final String password = "javarunnsteinuser";
	public static final String dbName = "RunnsteinDB";
	public static final int samplesPerSong = 30;
	public static int userID = 0;
	public static String username = null;
	public static String name = null;
	public static String surname1 = null;
	public static String surname2 = null;
	public static boolean sensorState = false;
	public static boolean syncState = false;
	public static int ppm = 1;
	public static int lang = 0;
	public static final int baudRate = 1200;
	public static final int requestMessage = 66;
	// Direcciones de archivos.
	public static final String confFile = "conf.dat";
	public static final String dHeartOn = "img/heart/heart_on_up.png";
	public static final String dHeartOff = "img/heart/heart_on_down.png";
	public static final String dLogoMin = "img/logo/logo.png";
	public static final String dLogoMax = "img/logo/maxLogo.png";
}
