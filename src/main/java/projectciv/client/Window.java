package main.java.projectciv.client;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;

import main.java.projectciv.Main;
import main.java.projectciv.Main.GameLoop;
import main.java.projectciv.util.math.MathH;

public class Window {
	public static boolean canRender = true;
	
	private static JFrame frame;
	private static GraphicsDevice gd;
	
	public Window(String title, GameLoop main) { //TODO redo!
		gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		frame = new JFrame(title);
		
		
		frame.setPreferredSize(new Dimension(MathH.floor(Main.HUD_WIDTH * 1.5f) + 16, MathH.floor(Main.HUD_HEIGHT * 1.5f) + 39));
		frame.setMinimumSize(new Dimension(Main.HUD_WIDTH + 16, Main.HUD_HEIGHT + 39));
		
		Container pane = frame.getContentPane();
		pane.setBackground(Color.BLACK);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setFocusable(true);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.add(main);
		
		frame.pack();
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
}
