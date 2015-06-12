package main;

import bluetooth.COMManager;
import database.SQLiteUtils;

public class Configuration {
	// Variables esenciales.
	public final static String dbSQLite = "runnstein";
	public static final int timerPeriod = 1000;
	public static String dbUrl = "runnstein.mooo.com";
	public static int port = 3026;
	public static String user = "java";
	public static String password = "javarunnsteinuser";
	public static String dbName = "RunnsteinDB";
	public static final int samplesPerSong = 30;
	public static int userID = 0;
	public static String username = null;
	public static String name = null;
	public static String surname1 = null;
	public static String surname2 = null;
	public static String provincia = null;
	public static String pueblo = null;
	public static String calle = null;
	public static String numero = null;
	public static String piso = null;
	public static String letra = null;
	public static String tlf = null;
	public static boolean sensorState = false;
	public static boolean isRunning = false;
	public static int ppm = 0;
	public static int lang = 0;
	public static final int baudRate = 1200;
	public static final int requestMessage = 66;
	public static COMManager com = null;
	public static int actualTraining = 0;
	public static int actualInterval = 0;
	public static int actualMuestra = 0;
	public static SQLiteUtils conn = null; 
	// Direcciones de archivos.
	public static final String confFile = "conf.dat";
	public static final String dHeartOn = "img/heart/heart_on_up.png";
	public static final String dHeartOff = "img/heart/heart_on_down.png";
	public static final String dLogoMin = "img/logo/logo.png";
	public static final String dLogoMax = "img/logo/maxLogo.png";
}
