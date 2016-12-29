package com.gynt.openrail.graphics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

import com.gynt.openrail.core.CurveTrack;
import com.gynt.openrail.core.ExitableTrack;
import com.gynt.openrail.core.ExitableTrack.Exit;
import com.gynt.openrail.core.Location;
import com.gynt.openrail.core.TrackManager;
import com.gynt.openrail.core.Train;

public class Debug extends JFrame {

	private JPanel contentPane;
	private Train train;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Debug frame = new Debug();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void redraw() {
		contentPane.repaint();
	}
	
	public void loop() {
		Timer t = new Timer(10, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				train.do_move();
				redraw();
			}
			
		});
		t.setInitialDelay(5000);
		t.start();
	}

	/**
	 * Create the frame.
	 */
	public Debug() {
		CurveTrack s = CurveTrack.getInstanceAngle(new Location(600,0,300), 100, 0, 360/16.0);
		ExitableTrack e = s;
		for(int i : new int[10]) {
			CurveTrack n =CurveTrack.getInstanceAngle(new Location(0,0,0), 100, 0, 360/16.0);
			TrackManager.assemble(e.getExits()[1], n.getExits()[0]);
			e=n;
		}
		
		train = Train.getInstance(s.getExits()[0].location, s.getExits()[1].location);
		train.setTracks(s, s);
		train.setSpeed(0.5);
		train.setDirection(true);
		train.setDirection2(true);
		train.setOffset(train.getLength());
		train.setOffset2(0);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1024, 768);
		contentPane = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				drawTrack(g, s);
				drawTrain(g, train);
			}
		};
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		loop();
		contentPane.repaint();
	}

	private void drawTrack(Graphics g, ExitableTrack e) {
		g.setColor(Color.black);
		//System.out.println(e.getExits()[0].location.x);
		g.drawLine((int) e.getExits()[0].location.x, (int)e.getExits()[0].location.z,(int) e.getExits()[1].location.x,(int) e.getExits()[1].location.z);
		for(int i = 1 ; i < e.getExits().length;i++) {
			if(e.getExits()[i].connection != null) {
				drawTrack(g, e.getExits()[i].connection.owner);
			}
		}
	}
	
	private void drawTrain(Graphics g, Train t) {
		g.setColor(Color.RED);
		//System.out.println(t.front_axis);
		//System.out.println(t.back_axis);
		//System.out.println();
		g.drawLine((int) t.front_axis.x,(int)  t.front_axis.z, (int) t.back_axis.x, (int) t.back_axis.z);
	}
	
}
