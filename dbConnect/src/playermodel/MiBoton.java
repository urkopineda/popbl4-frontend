package playermodel;

import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class MiBoton extends JButton {
	
	public MiBoton(String s) {
		super(s);
	}
	
	public MiBoton(String s, ActionListener al) {
		super(s);
		this.addActionListener(al);
	}
	
	public MiBoton(String s, ActionListener al, String cmd) {
		super(s);
		this.addActionListener(al);
		this.setActionCommand(cmd);
	}
	
	public MiBoton(ImageIcon icon) {
		super(icon);
	}
	
	public MiBoton(ImageIcon icon, ActionListener al) {
		super(icon);
		this.addActionListener(al);
	}
	
	public MiBoton(ImageIcon icon, ActionListener al, String cmd) {
		super(icon);
		this.addActionListener(al);
		this.setActionCommand(cmd);
	}
	
}
