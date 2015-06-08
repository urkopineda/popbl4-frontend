package bluetooth;

import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;
import main.Configuration;

public class Serial {
	SerialPort port;
	
	/**
	 * Abrir puerto de conexión.
	 */
	public void openPort() {
		if (SerialPortList.getPortNames().length != 0) {
			port = new SerialPort(SerialPortList.getPortNames()[0]);	
			try {
				port.openPort();
				port.setParams(Configuration.baudRate, 8, 1, 0);
				Configuration.sensorState = true;
			} catch (SerialPortException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Envía un mensaje por serie Bluetooth.
	 * 
	 * @param int message
	 */
	public void sendMessage(){
		try {
			port.writeInt(Configuration.requestMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Recive un mensaje por serie Bluetooth
	 * 
	 * @return long data
	 */
	public int receiveMessage(){
		byte[] buffer = null; 
		int data = 0;
		try { 
			buffer = port.readBytes(1);
			data = Byte.toUnsignedInt(buffer[0]);
		} catch(Exception e) { 
			e.printStackTrace(); 
		} 
		return data;
	}
	
	/**
	 * Cierra el puerto de conexión anteriormente abierto.
	 */
	public void closePort(){
		try {
			if ((port != null) && (port.isOpened())){
				port.purgePort(1);
				port.purgePort(2);
				port.closePort();
				Configuration.sensorState = false;
			}
		} catch (SerialPortException e) {
			e.printStackTrace();
		}
	}
}
