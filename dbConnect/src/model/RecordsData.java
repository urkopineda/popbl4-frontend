package model;

import java.util.ArrayList;

public class RecordsData {
	public int maxDays = 0;
	public String maxDuration = null;
	public int maxPPM = 0;
	public int maxBPM = 0;
	
	public RecordsData(int trainingNumber, String dateTime, String duration, ArrayList<Integer> ppm, ArrayList<Integer> bpm) {
		this.trainingNumber = trainingNumber;
		this.dateTime = dateTime;
		this.duration = duration;
		this.ppm = ppm;
		this.bpm = bpm;
	}
}
