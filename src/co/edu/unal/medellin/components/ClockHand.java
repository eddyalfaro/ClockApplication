package co.edu.unal.medellin.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import co.edu.unal.medellin.interfaces.Draw;

public class ClockHand implements Draw, Runnable{

	/**
	 * @author EddyHernando clase para crear las manos del reloj
	 * 
	 * @param name nombre de la mano
	 * @param lenght longitud de la mano, valor que va de 0 a 1.
	 * @param pace cantidad de movimientos que hace la mano del reloj dentro de una hora
	 * @param startAt valor en el que se ubica la mano del reloj a su inicio
	 */
	
	private int defaultStarting;
	private int pace;
	private double lenght;
	private String name;
	private int amount;
	
	private int[] xPoints = new int[6];
	private int[] yPoints = new int[6];
	
	private double deltaTheta;
	private double startingTheta = - Math.PI/2;
	private double pointingTheta;
	
	private boolean seconds;
	private boolean threadKiler;
	private double[] ratios = new double[6];
	private double[] directions = new double[6];
	
	private Thread usefulThread;
	private int delay;
	private int value;
	
	public static final int MINUTES_HAND = 60;
	public static final int SECONDS_HAND = 60;
	public static final int HOURS_HAND = 12;
	
	public ClockHand(String name, double lenght, int pace, int startAt, boolean seconds){
		this.name = name;
		this.lenght = lenght;
		this.pace  =pace;
		this.defaultStarting = startAt;
		this.seconds = seconds;
		
		if (seconds == true){
			this.amount = 1000;
			this.delay = 1000;
			
		}else{
			if (this.pace == 60){
			this.amount = 60*1000;
			}else{
			this.amount = 60*60*1000;
			}
		}
		
		this.deltaTheta = 2*Math.PI/pace;		
		this.pointingTheta = (startingTheta + deltaTheta*startAt);
		
		if(this.pace == 12){
			this.pace = 24;
		}
	}
		
	@Override
	public void draw(JPanel panel, Graphics g) {
		
		int h = panel.getSize().height;
		int w = panel.getSize().width;
		
		Graphics2D g2 = (Graphics2D) g;
		
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	                RenderingHints.VALUE_ANTIALIAS_ON);
		
		int panelCenterX = (int) (w/2);
		int panelCenterY = (int) (h/2);
		
		double lenghtHand;
	
		if (h > w){
			lenghtHand = ((0.9*w/2 - 15)*this.lenght);
		}else{
			lenghtHand = ((0.9*h/2 - 15)*this.lenght);
		}
		
				
		if (seconds == false){
			g2.setColor(Color.gray);
			
			double[] directions = {0 , -7*Math.PI/8 , -Math.PI/24 , 0 , Math.PI/24 , 7*Math.PI/8};
			double[] ratios = {0 , 0.1 , 0.70 , 1 , 0.70 , 0.1};
			
			this.ratios = ratios;
			this.directions = directions;
		}else{
			g2.setColor(Color.red);
			
			double[] directions = {0 , -Math.PI/2 , -Math.PI/92 , 0 , Math.PI/92 , 7*Math.PI/8};
			double[] ratios = {0 , 0.025 , 1 , 1 , 1 , 0.025};
			
			this.ratios = ratios;
			this.directions = directions;
		}
		
					
		for (int i = 0 ; i < 6 ; i++){
			xPoints[i] = (int) (lenghtHand*ratios[i]*Math.cos(this.pointingTheta + directions[i]) + panelCenterX);
			yPoints[i] = (int) (lenghtHand*ratios[i]*Math.sin(this.pointingTheta + directions[i]) + panelCenterY);
		}
		
		if (seconds == false){
			g2.fillPolygon(xPoints, yPoints,6);
			g2.setColor(Color.BLACK);
			g2.drawPolygon(xPoints, yPoints, 6);
		}else{
			g2.fillPolygon(xPoints, yPoints,6);
		}
		
		
	}
	
	public void setTimeHandValue(int value){
		this.defaultStarting = value;
		this.value = value;
		this.pointingTheta = (startingTheta + deltaTheta*value);
	}
	
	public double getDeltaTheta(){
		return this.deltaTheta;
	}
	
	public String getName(){
		return this.name;
	}
	
	public int getStarting(){
		return this.defaultStarting;
	}
	
	public int getPace(){
		return this.pace;
	}
	
	public int getValuesForText(){
		return this.value;
	}
	
	public void setValuesForText(int che){
		this.value = che;
	}


	@Override
	public void run() {
		int i = 0;
			while (this.threadKiler) {
				if (i!=0){
					this.pointingTheta = this.pointingTheta + this.deltaTheta;
					this.defaultStarting = this.defaultStarting +1;
				}
				if(this.defaultStarting > this.pace - 1){
					this.defaultStarting = 0;	
					this.pointingTheta = startingTheta;
				}
				this.setValuesForText(this.defaultStarting);
				i++;
				try {
					if (i == 1){
					Thread.sleep(this.delay);	
					}else{
					Thread.sleep(this.amount);
					}
				} catch (InterruptedException ex) {
					this.threadKiler = false;
					System.out.println(this.getName() + " parado");
				}
			}
		
	}
	
	public void BeginHands(){
		usefulThread = new Thread(this);
		usefulThread.setName(this.name);
		threadKiler = true;
		usefulThread.start();
	}
	
	public void finishHands(){
		usefulThread.interrupt();
	}
	
	public void setTimeFix(int delay){
		this.delay = delay;
	}
	
}
