package co.edu.unal.medellin.clock;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ClockMain implements FocusListener, Runnable{
	
	/**
	 * @author EddyHernando 
	 * 
	 */
		
	private ClockAssemble assemble;
	private JTextField minutesField;
	private JTextField hoursField;
	private JTextField secondsField;
	
	private int minutesValue;
	private int secondsValue;
	private int hoursValue;
	
	private boolean friend = true;
	private Thread watchTime;	
	private JButton start;
	static ClockMain clock;
	
	public static void main(String[] args) {
		clock = new ClockMain();
		clock.go();
	}
	
	public void go(){
		JFrame frame = new JFrame("Reloj Digital");
				
		start = new JButton("Start");
		start.addActionListener(new StartListener());
		
		JButton stop = new JButton("Stop");
		stop.addActionListener(new StopListener());
		
		JPanel buttonsInset = new JPanel(new GridLayout(2,1));
		buttonsInset.add(start);
		buttonsInset.add(stop);
		
		GridLayout grid1X3 = new GridLayout(3,2);
		JPanel textInsets = new JPanel(grid1X3);
		
		JPanel allInsets = new JPanel(new GridLayout(1,2));
		allInsets.add(textInsets);
		allInsets.add(buttonsInset);
		
		minutesField = new JTextField("00-60");
		secondsField = new JTextField("00-60");
		hoursField = new JTextField("00-24");
		
		textInsets.setBackground(Color.white);
		textInsets.add(new StringDrawing("Hora: "));
		textInsets.add(hoursField);
		textInsets.add(new StringDrawing("Minutos: "));
		textInsets.add(minutesField);
		textInsets.add(new StringDrawing("Segundos: "));
		textInsets.add(secondsField);
		
		hoursField.addFocusListener(this);
		minutesField.addFocusListener(this);
		secondsField.addFocusListener(this);
		
		hoursField.setHorizontalAlignment(JTextField.CENTER);
		minutesField.setHorizontalAlignment(JTextField.CENTER);
		secondsField.setHorizontalAlignment(JTextField.CENTER);
		
		assemble = new ClockAssemble(secondsValue, minutesValue, hoursValue);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(assemble,BorderLayout.CENTER);
		frame.getContentPane().add(allInsets, BorderLayout.SOUTH);
		frame.setSize(300, 300);
		frame.setVisible(true);
		frame.setResizable(true);
	}

	public class StartListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {			
			watchTime = new Thread(clock);
			watchTime.setName("Viendo");
			friend=true;
			
			minutesField.setEditable(false);
			secondsField.setEditable(false);
			hoursField.setEditable(false);
			
			assemble.startClock();
			watchTime.start();
			
			start.setEnabled(false);
		}
	
	}

	public class StopListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			assemble.endClockWork();
			watchTime.interrupt();
			minutesField.setText("00-60");
			secondsField.setText("00-60");
			hoursField.setText("00-24");
			
			minutesField.setEditable(true);
			secondsField.setEditable(true);
			hoursField.setEditable(true);
			
			start.setEnabled(true);
			
			assemble.setTimeForHanders(0, 0, 0);
			
		}
	
	}


	@Override
	public void focusGained(FocusEvent arg0) {
		JTextField source = (JTextField) arg0.getSource();
		source.selectAll();
	}

	@Override
	public void focusLost(FocusEvent arg0) {	
		try{
			secondsValue =Integer.parseInt(secondsField.getText());
			assemble.setTimeForHanders(hoursValue, minutesValue, secondsValue);	
		}catch(NumberFormatException exc){
			assemble.setTimeForHanders(hoursValue, minutesValue, secondsValue);
		}
		
		try{
			minutesValue =Integer.parseInt(minutesField.getText());
			assemble.setTimeForHanders(hoursValue, minutesValue, secondsValue);	
		}catch(NumberFormatException exc){
			assemble.setTimeForHanders(hoursValue, minutesValue, secondsValue);
		}
		
		try{
			int i = Integer.parseInt(hoursField.getText());
			if (i>24){
				hoursValue = 0;
				hoursField.setText("Solo 24h");
			}else{
				hoursValue = i;
			}			
			assemble.setTimeForHanders(hoursValue, minutesValue, secondsValue);	
		}catch(NumberFormatException exc){
			assemble.setTimeForHanders(hoursValue, minutesValue, secondsValue);
		}
			
	}

	@Override
	public void run() {
		int i = 0;
		while (this.friend == true) {
			if (i!=0){
				if (assemble.getMinutes()<10){
					minutesField.setText("0" + assemble.getMinutes());
				}else{
					minutesField.setText("" + assemble.getMinutes());
				}
				if (assemble.getHours()<10){
					hoursField.setText("0" + assemble.getHours());
				}else{
					hoursField.setText("" + assemble.getHours());
				}
				if (assemble.getSeconds()<10){
					secondsField.setText("0" + assemble.getSeconds());
				}else{
					secondsField.setText("" + assemble.getSeconds());
				}				
			}
			i++;
			try {

				Thread.sleep(1000);

			} catch (InterruptedException ex) {
				System.out.println("Reloj parado");
				this.friend=false;
			}
		}
	}
	
}//close outer class
