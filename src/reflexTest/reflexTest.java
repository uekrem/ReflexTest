package reflexTest;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.AbstractAction;
import javax.swing.KeyStroke;
import java.time.LocalTime;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Random;

public class reflexTest extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel mainLay;

	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					reflexTest frame = new reflexTest();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public long convertToMillis(int hours, int minutes, int seconds, int milliseconds) {
		return hours * 60 * 60 * 1000 + minutes * 60 * 1000 + seconds * 1000 + milliseconds;
	}
	
	public boolean flag;
	
	public void	startGame(JPanel mainLay, JLabel timeLabel, JLabel statusLabel, JButton btnNewButton) 
	{
		flag = true;
		Random random = new Random();
		Timer timer = new Timer();
		
		JButton pressBtn = new JButton("");
        pressBtn.setVisible(true);
		pressBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == pressBtn && flag) 
				{
					LocalTime currenttime_a = LocalTime.now();
			        int hour_a = currenttime_a.getHour();
			        int minute_a = currenttime_a.getMinute();
			        int second_a = currenttime_a.getSecond();
			        int millisecond_a = currenttime_a.getNano() / 1000000;
			        long sonmin = convertToMillis(hour_a, minute_a, second_a, millisecond_a) - Long.parseLong(timeLabel.getText());
					timeLabel.setText(sonmin + " ms");
					timeLabel.setBounds(124, 104, 182, 118);
					pressBtn.setVisible(false);
					btnNewButton.setVisible(true);
                }
				else
            	{
					statusLabel.setText("SLOW DOWN");
            		pressBtn.setVisible(false);
					btnNewButton.setVisible(true);
            	}
			}
		});
		
		pressBtn.setBounds(163, 215, 0, 0);
		mainLay.add(pressBtn);
		
		mainLay.setBackground(Color.WHITE);
		statusLabel.setText("BE READY");
		TimerTask task = new TimerTask() {
            public void run() {
            		LocalTime currenttime_b = LocalTime.now();
            		int hour_b = currenttime_b.getHour();
            		int minute_b = currenttime_b.getMinute();
            		int second_b = currenttime_b.getSecond();
            		int millisecond_b = currenttime_b.getNano() / 1000000;
            		timeLabel.setBounds(124, 104, 0, 0);
            		timeLabel.setText(String.valueOf(convertToMillis(hour_b, minute_b, second_b, millisecond_b)));
            		statusLabel.setText("PRESS");
            		mainLay.setBackground(Color.RED);            		
          
            }
        };
        
        timer.schedule(task, random.nextInt(6001) + 2000);
        
		
		KeyStroke keyStroke = KeyStroke.getKeyStroke("SPACE");
		pressBtn.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStroke, "SPACEPressed");
		pressBtn.getActionMap().put("SPACEPressed", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				if (statusLabel.getText() == "PRESS")
					pressBtn.doClick();
				else
				{
					task.cancel();
					flag = false;
					pressBtn.doClick();
				}
			}
		});
        
	}
	
	public reflexTest() {
		
		
		
		setTitle("Reflex Test");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		mainLay = new JPanel();
		mainLay.setBackground(new Color(255, 255, 255));
		mainLay.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(mainLay);
		mainLay.setLayout(null);
		
		JLabel timeLabel = new JLabel("00:00:00");
		timeLabel.setFont(new Font("Symbol", Font.PLAIN, 20));
		timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		timeLabel.setBounds(124, 104, 0, 0);
		mainLay.add(timeLabel);
		
		JLabel statusLabel = new JLabel("BE READY");
		statusLabel.setFont(new Font("Symbol", Font.PLAIN, 25));
		statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
		statusLabel.setBounds(124, 28, 182, 64);
		mainLay.add(statusLabel);
		
		JButton btnNewButton = new JButton("START");
		btnNewButton.setFont(new Font("Symbol", Font.PLAIN, 25));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == btnNewButton)
				{
					btnNewButton.setVisible(false);
					timeLabel.setText("");
					startGame(mainLay, timeLabel, statusLabel, btnNewButton);
				}
			}
		});
		
		KeyStroke keyStroke = KeyStroke.getKeyStroke("SPACE");
		btnNewButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStroke, "SPACEPressed");
		btnNewButton.getActionMap().put("SPACEPressed", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				btnNewButton.doClick();
			}
		});
		
		btnNewButton.setBounds(124, 202, 182, 64);
		mainLay.add(btnNewButton);
	}
}
