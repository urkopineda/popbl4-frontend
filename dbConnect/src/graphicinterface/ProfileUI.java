package graphicinterface;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ProfileUI {
	JPanel mainPanel = null;
	JPanel northPanel = null;
	JPanel centerPanel = null;
	JLabel profileIMG = null;
	
	public ProfileUI() {
		
	}
	
	public Container createMainPanel() {
		mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(createNorthPanel(), BorderLayout.NORTH);
		mainPanel.add(createCenterPanel(), BorderLayout.CENTER);
		return mainPanel;
	}
	
	private Container createNorthPanel() {
		northPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		File img = new File("img/profile.jpg");
		File defaultIMG = new File("img/default.jpg");
		if (img.exists()) {
			profileIMG = new JLabel(new ImageIcon(img.getAbsolutePath()));
		} else if (defaultIMG.exists()){
			profileIMG = new JLabel(new ImageIcon(defaultIMG.getAbsolutePath()));
		} else {
			
		}
		northPanel.add(profileIMG);
		return northPanel;
	}
	
	private Container createCenterPanel() {
		centerPanel = new JPanel();
		centerPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        JPanel profilePanel = new JPanel(new GridLayout(4, 2, 5, 5));
        // FOTO
        // ...
        // USUARIO
        // NOMBRE
        // APELLIDO 1 | APELLIDO 2
        // CONTRASEÑA
        // VOLVER A METER CONTRASEÑA
        // ...
        // GUARDAR | CANCELAR
        centerPanel.add(profilePanel, gbc);
		return centerPanel;
	}
}
