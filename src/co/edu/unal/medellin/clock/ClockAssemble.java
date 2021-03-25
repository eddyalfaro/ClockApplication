package co.edu.unal.medellin.clock;

import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

import co.edu.unal.medellin.components.ClockBackground;
import co.edu.unal.medellin.components.ClockHand;
import co.edu.unal.medellin.components.ClockPin;
import co.edu.unal.medellin.interfaces.Draw;

public class ClockAssemble extends JPanel implements Runnable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Draw> clockComponents = new ArrayList<Draw>();
	
	private ClockHand minutes;
	private ClockHand hours;
	private ClockHand seconds;
	
	private boolean threadKiller;
	private Thread all;
	
	private int seg;
	private int min;
	private int hr;
	private int amount = 1000;
	
	public ClockAssemble(int secondsValue, int minutesValue, int hoursValue){
		
		minutes= new ClockHand("Minutero", 0.9, ClockHand.MINUTES_HAND, minutesValue,false);
		hours = new ClockHand("Horario", 0.65, ClockHand.HOURS_HAND, hoursValue,false);
		seconds = new ClockHand("Segundero", 0.95, ClockHand.SECONDS_HAND, secondsValue,true);
	}
	
	public void paintComponent(Graphics g){

		clockComponents.add(new ClockBackground());
						
		clockComponents.add(minutes);
		clockComponents.add(hours);
		clockComponents.add(seconds);
		
		clockComponents.add(new ClockPin());

		for (int i = 0; i < clockComponents.size() ; i++){
			clockComponents.get(i).draw(this, g);
		}
		
	}

	@Override
	public void run() {
		
		minutes.setTimeFix(1000*(seconds.getPace()-seconds.getStarting()));
		hours.setTimeFix(1000*(minutes.getPace()-minutes.getStarting())*(seconds.getPace()-seconds.getStarting()));
		
		seconds.BeginHands();
		minutes.BeginHands();
		hours.BeginHands();
		while (this.threadKiller == true) {
			this.repaint();
			this.setTextTime(hours.getValuesForText(), minutes.getValuesForText(), seconds.getValuesForText());
			try {

				Thread.sleep(this.amount);

			} catch (InterruptedException ex) {
				this.threadKiller=false;
			}
		}
		
	}
	
	public void startClock(){
		all = new Thread(this);
		all.setName("Reloj");
		this.threadKiller = true;
		all.start();
	}
	
	public void endClockWork(){
		seconds.finishHands();
		minutes.finishHands();
		hours.finishHands();
		all.interrupt();
	}
	
	public void setTimeForHanders(int hour, int minutes, int seconds){
		this.seconds.setTimeHandValue(seconds);
		this.minutes.setTimeHandValue(minutes);
		this.hours.setTimeHandValue(hour);
		this.repaint();
	}
	
	public void setTextTime(int h, int min, int sec){
		this.hr =h;
		this.min = min;
		this.seg = sec;
	}
	
	public int getHours(){
		return this.hr;
	}
	
	public int getMinutes(){
		return this.min;
	}
	
	public int getSeconds(){
		return this.seg;
	}
}