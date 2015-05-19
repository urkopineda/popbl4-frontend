package utils;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * Clase para establecer imágenes de fondo en los JPanel-s.
 * 
 * @author Runnstein Team
 */
@SuppressWarnings("serial")
public class ImagePanel extends JPanel {
	private Image img;
	
	/**
	 * Contructor #1 - Pasamos una dirección de la imágen y creamos el JPanel.
	 * 
	 * @param String pathToImg
	 */
	public ImagePanel(String pathToImg) {
		this(new ImageIcon(pathToImg).getImage());
	}
	
	/**
	 * Contructor #2 - Pasamos ya la imágen creada y creamos el JPanel.
	 * 
	 * @param Image img
	 */
	public ImagePanel(Image img) {
		this.img = img;
		Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
		setSize(size);
		setLayout(null);
	}
	
	/**
	 * Con esta función printamos la imágen en Graphics g.
	 */
	public void paintComponent(Graphics g) {
		g.drawImage(img, (this.getWidth() / 2) - (img.getWidth(null) / 2), (this.getHeight() / 2) - (img.getHeight(null) / 2), null);
	}
}
