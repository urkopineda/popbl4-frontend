package language;

import java.util.ArrayList;

import main.Configuration;

public class Strings {
	public static ArrayList<LanguageList> lista = new ArrayList<>();
	/*
	// LogInUI
	public static String windowLogIn = lista.get(Configuration.lang).get("windowLogIn");
	public static String logInUser = "Usuario: ";
	public static String logInPass = "Contraseña: ";
	public static String logInCheck = "Comprobar";
	public static String logInChecking = "Comprobando...";
	public static String logInCancel = "Cancelar";
	public static String logInError = "ERROR: Usuario y/o contraseña incorrectos.";
	// MainUI
	public static String windowMain = "Runnstein";
	public static String mainTabTraining = "Iniciar Entrenamiento";
	public static String mainTabTrainingData = "Mis Entrenamientos";
	public static String mainTabStatistics = "Mis Estadísticas";
	public static String mainTabProfile = "Mi Perfil";
	// TrainingUI
	public static String trainingList = "Lista de Reproducción";
	public static String trainingMain = "Control del Entrenamiento";
	public static String trainingChrono = "Cronómetro";
	public static String trainingPlayer = "Reproductor de Música";
	public static String trainingPulse = "Pulsómetro";
	public static String trainingBtnStart = "Iniciar";
	public static String trainingBtnPause = "Pausar";
	public static String trainingBtnStop = "Stop";
	// TrainingDataUI
	public static String trainingDataMain = "Resumen de Entrenamientos";
	// StatisticsUI
	public static String statisticsMain = "Gráficos de Ejercicio";
	public static String statsModeTime = "Tiempo (minutos)";
	public static String statsModeMax = "Máximo";
	public static String statsModeMin = "Mínimo";
	public static String statsModeMean = "Media";
	public static String graphTime = "Duraciones de Entrenamientos";
	public static String graphTraining = "Entrenamientos";
	public static String graphPPM = "Pulsaciones por Minuto";
	public static String graphBPM = "Bits por Minuto";
	*/
	
	public final static String get(String key) {
		return lista.get(Configuration.lang).get(key);
	}
}
