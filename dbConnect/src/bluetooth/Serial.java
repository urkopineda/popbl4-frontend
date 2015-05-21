package bluetooth;

import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;
import main.Configuration;

/**
 * Se encarga de la conxeión Bluetooth por serie al módulo HC-06.
 * 
 * @author Runnstein Team
 */
public class Serial {
	SerialPort port;
	
	/**
	 * Abrir puerto de conexión.
	 */
	public void openPot() {
		if (SerialPortList.getPortNames().length != 0) {
			port = new SerialPort(SerialPortList.getPortNames()[0]);	
			try {
				port.openPort();
				port.setParams(9600, 8, 1, 0);
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
	public void sendMessage(int message){
		try {
			port.writeInt(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Recive un mensaje por serie Bluetooth
	 * 
	 * @return long data
	 */
	public long receiveMessage(){
		byte[] buffer = null; 
		long data = 0;
		try { 
			buffer = port.readBytes(1);
			data = Byte.toUnsignedLong(buffer[0]);
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
