package co.edu.unal.medellin.interfaces;

import java.awt.Graphics;
import javax.swing.JPanel;

public interface Draw {

	/** @author EddyHernando
	 * 
	 * inteface que le permite a los objetos que la implementan saber como
	 * dibujarse, mediante el metodo draw
	 * 
	 * @param panel elemento en donde se va a dibujar
	 * @param g herramienta de dibujo
	 */
	
	public void draw(JPanel panel, Graphics g);
	
}
