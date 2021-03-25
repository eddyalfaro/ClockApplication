package co.edu.unal.medellin.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import co.edu.unal.medellin.interfaces.*;

public class ClockBackground implements Draw{
	
	private Color panelColor = new Color(168,232,253);
	private Color clockBorder = new Color(63,63,63);

	@Override
	public void draw(JPanel panel, Graphics g) {
		
		int h = panel.getSize().height;
		int w = panel.getSize().width;
		
		Graphics2D g2 = (Graphics2D) g;
		
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	                RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2.setColor(panelColor);
		g2.fillRect(0, 0, w, h);
		
		int re;
		int ri;
		
		if (h > w){
			ri = (int) (0.9*w/2);
			re = (int) (0.95*w/2);
		}else{
			ri = (int) (0.9*h/2);
			re = (int) (0.95*h/2);
		}
		
		int panelCenterX = (int) (w/2);
		int panelCenterY = (int) (h/2);
		
		g2.setColor(clockBorder);
		g2.fillOval(panelCenterX - re, panelCenterY - re, 2*re, 2*re);
		
		g2.setColor(Color.WHITE);
		g2.fillOval(panelCenterX - ri, panelCenterY - ri, 2*ri, 2*ri);
		
		g2.setColor(Color.black);
		g2.drawOval(panelCenterX - ri, panelCenterY - ri, 2*ri -1, 2*ri-1);
		g2.drawOval(panelCenterX - re, panelCenterY - re, 2*re -1, 2*re-1);
		
		int highR = ri - 4;
		int lowR = ri - 14;
		double deltaTheta = 2*Math.PI/12;
		
		for (int i = 0; i < 12; i++){
			int x1 = (int) (highR*Math.cos(deltaTheta*i) + panelCenterX);
			int x2 = (int) (lowR*Math.cos(deltaTheta*i) + panelCenterX);
			int y1 = (int) (highR*Math.sin(deltaTheta*i) + panelCenterY);
			int y2 = (int) (lowR*Math.sin(deltaTheta*i) + panelCenterY);
			
			g2.drawLine(x1, y1, x2, y2);
		}
		
		lowR = ri - 6;
		deltaTheta = 2*Math.PI/60;
		
		for (int i = 0; i < 60; i++){
			int x1 = (int) (highR*Math.cos(deltaTheta*i) + panelCenterX);
			int x2 = (int) (lowR*Math.cos(deltaTheta*i) + panelCenterX);
			int y1 = (int) (highR*Math.sin(deltaTheta*i) + panelCenterY);
			int y2 = (int) (lowR*Math.sin(deltaTheta*i) + panelCenterY);
			
			g2.drawLine(x1, y1, x2, y2);
		}
	}

}
