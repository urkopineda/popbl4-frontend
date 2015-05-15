package uart;

public class SensorConnection {
	boolean sensorState = false;
	int ppmValue = 0;
	
	public boolean getSensorState() {
		return sensorState;
	}
	
	public void switchSensorState() {
		if (sensorState) sensorState = false;
		else if(!sensorState) sensorState = true;
	}
	
	public int getPPMValue() {
		return ppmValue;
	}
}
