package playerModel;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import model.DataType;

public class MiRenderer extends JLabel implements ListCellRenderer<DataType>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6980337228947461037L;

	@Override
	public Component getListCellRendererComponent(JList<? extends DataType> list,
			DataType value, int index, boolean isSelected, boolean cellHasFocus) {
		// TODO Auto-generated method stub
		String s = "<html> <h2>"+value.getProperty("Title")+"</h2> by <font size='4'>"+value.getProperty("Author")+"</font>";
		s += " from "+value.getProperty("Album")+" <p align='left'>"+value.getProperty("BPM")+"</p> </html>";
		this.setText(s);
		this.setBorder(BorderFactory.createEmptyBorder());
		this.setOpaque(true);
		this.setForeground(new Color(0,0,0));
		if (!isSelected) {
			this.setBackground(new Color(255,255,255));
		} else {
			this.setBorder(BorderFactory.createLineBorder(new Color(38,160,218)));
			this.setBackground(new Color(203,232,246));
		}
		this.setVisible(true);
		return this;
	}


}
