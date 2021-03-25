package co.edu.unal.medellin.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import co.edu.unal.medellin.interfaces.Draw;

public class ClockPin implements Draw{

	@Override
	public void draw(JPanel panel, Graphics g) {
		int h = panel.getSize().height;
		int w = panel.getSize().width;
		
		Graphics2D g2 = (Graphics2D) g;
		
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	                RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2.setColor(Color.BLACK);
		
		int panelCenterX = (int) (w/2);
		int panelCenterY = (int) (h/2);
		
		int r;
		if (h > w){
			r = (int) ((0.9*w/2 - 15)*0.1);
		}else{
			r = (int) ((0.9*h/2 - 15)*0.1);
		}
		
		
		g2.fillOval(panelCenterX - r, panelCenterY -r, 2*r, 2*r);
		
		r = (int) ((0.9*h/2 - 15)*0.05);
		g2.setColor(Color.red);
		g2.fillOval(panelCenterX - r, panelCenterY -r, 2*r, 2*r);
	}
	
	

}
