package graphicinterface;

import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class CronometerUI {
	JPanel mainCronometerPanel = null;
	JLabel cronometerNumbers = null;
	private long begin, end;
	
	public CronometerUI(double hours, double minutes, double seconds) {
		createCronometerPanel(hours, minutes, seconds);
	}

	public void setJLabelTime(double hours, double minutes, double seconds) {
		createCronometerPanel(hours, minutes, seconds);
	}
	
	private Container createCronometerPanel(double hours, double minutes, double seconds) {
		mainCronometerPanel = new JPanel(new GridLayout(1, 1, 5, 5));
		mainCronometerPanel.add(WindowMaker.createJLabel(cronometerNumbers, hours+":"+minutes+":"+seconds, 75));
		return mainCronometerPanel;
	}
	
	public JPanel getPane() {
		return mainCronometerPanel;
	}
	 
    public void start(){
        begin = System.currentTimeMillis();
    }
 
    public void stop(){
        end = System.currentTimeMillis();
    }
 
    public long getTime() {
        return end - begin;
    }
 
    public long getMilliseconds() {
        return end - begin;
    }
 
    public double getSeconds() {
        return (end - begin) / 1000.0;
    }
 
    public double getMinutes() {
        return (end - begin) / 60000.0;
    }
 
    public double getHours() {
        return (end - begin) / 3600000.0;
    }
}
