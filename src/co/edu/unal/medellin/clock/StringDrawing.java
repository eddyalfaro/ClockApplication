package co.edu.unal.medellin.clock;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

public class StringDrawing extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2240053031293846372L;
	private String words;
	
	public StringDrawing(String words){
		this.words = words;
	}

	public void paintComponent(Graphics g) {
		int h = this.getSize().height;
		int w = this.getSize().width;
		
		int panelCenterX = (int) (w/2);
		int panelCenterY = (int) (h/2);
				
		Graphics2D g2 = (Graphics2D) g;
		FontMetrics fontMetrics = g2.getFontMetrics();
		
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	                RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2.setColor(Color.WHITE);
		g2.fillRect(0, 0, w, h);
		
		g2.setColor(Color.black);
		
		g2.drawRect(0, 0, w, h);
		
		int letterSize = 12;
		 
		Font NewTimes = new Font("NewTimes", Font.BOLD, letterSize);
		g2.setFont(NewTimes);
		
		int sW = fontMetrics.stringWidth(this.words);
		int sH = fontMetrics.getAscent();
					
		fontMetrics = g2.getFontMetrics();
		
		g2.setColor(Color.black);
		
		g2.drawString(this.words, panelCenterX - sW/2 , panelCenterY + sH/2);
		
	}
	
	
}
