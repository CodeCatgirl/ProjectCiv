package main.java.projectciv.client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;
import javax.swing.JPanel;

import main.java.projectciv.Main;

public class Window {
	public static boolean canRender = false;
	
	private static JFrame frame;
	private static GamePanel gamePanel;
	private static GraphicsDevice gd;
	
	public Window(String title) {
		gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		
		frame = new JFrame(title);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gamePanel = new GamePanel();
		gamePanel.setPreferredSize(new Dimension(Main.HUD_WIDTH, Main.HUD_HEIGHT));
		gamePanel.setBackground(Color.GRAY);
		
		JPanel wrapperPanel = new JPanel(new AspectRatioKeeperLayout());
		wrapperPanel.add(getGamePanel());
		
		frame.getContentPane().add(wrapperPanel);
		frame.setPreferredSize(new Dimension(Main.HUD_WIDTH + 16, Main.HUD_HEIGHT + 39));
		frame.setMinimumSize(new Dimension(Main.HUD_WIDTH + 16, Main.HUD_HEIGHT + 39));
		
		frame.setVisible(true);
		frame.pack();
	}
	
	public static void setupComponents() {
		frame.addKeyListener(Main.getKeyHandler());
		frame.addMouseListener(Main.getMouseHandler());
		frame.addMouseMotionListener(Main.getMouseHandler());
		frame.addMouseWheelListener(Main.getMouseHandler());
	}
	
	public static void toggleFullscreen() {
		canRender = false;
		frame.dispose();
		
		if (frame.isUndecorated()) {
			gd.setFullScreenWindow(null);
			frame.setUndecorated(false);
		} else {
			frame.setUndecorated(true);
			gd.setFullScreenWindow(frame);
		}
		
		frame.setVisible(true);
		Main.getMain().requestFocus();
		
		canRender = true;
	}
	
	public static GamePanel getGamePanel() {
		return gamePanel;
	}
	
	public static JFrame getFrame() {
		return frame;
	}
}
