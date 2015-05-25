package playermodel;

import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

@SuppressWarnings("serial")
public class MiMenuItem extends JMenuItem {
	
	public MiMenuItem(String text) {
		super(text);
	}
	
	public MiMenuItem(String text, ActionListener al) {
		super(text);
		this.addActionListener(al);
	}
	
	public MiMenuItem(String text, ActionListener al, String actionCmd) {
		super(text);
		this.addActionListener(al);
		this.setActionCommand(actionCmd);
	}
	
}
