package model;

import java.util.ArrayList;

public class ChartData {
	public int trainingNumber = 0;
	public String dateTime = null;
	public String duration = null;
	public ArrayList<Integer> ppm = null;
	public ArrayList<Integer> bpm = null;
	
	public ChartData(int trainingNumber, String dateTime, String duration, ArrayList<Integer> ppm, ArrayList<Integer> bpm) {
		this.trainingNumber = trainingNumber;
		this.dateTime = dateTime;
		this.duration = duration;
		this.ppm = ppm;
		this.bpm = bpm;
	}
}
