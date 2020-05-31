package main.java.projectciv.client;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import main.java.projectciv.Main;
import main.java.projectciv.util.ITickable;

public class KeyHandler extends KeyAdapter implements ITickable {
	
	private boolean[] cameraKeys = new boolean[4];
	
	public KeyHandler() {
		for (int i = 0; i < 4; i++) {
			cameraKeys[0] = false;
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		if (key == KeyEvent.VK_W) {
			cameraKeys[0] = true;
		} else if (key == KeyEvent.VK_A) {
			cameraKeys[1] = true;
		} else if (key == KeyEvent.VK_S) {
			cameraKeys[2] = true;
		} else if (key == KeyEvent.VK_D) {
			cameraKeys[3] = true;
		} else if (key == KeyEvent.VK_F11) {
			Window.toggleFullscreen();
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
		if (key == KeyEvent.VK_W) {
			cameraKeys[0] = false;
		} else if (key == KeyEvent.VK_A) {
			cameraKeys[1] = false;
		} else if (key == KeyEvent.VK_S) {
			cameraKeys[2] = false;
		} else if (key == KeyEvent.VK_D) {
			cameraKeys[3] = false;
		}
	}
	
	@Override
	public void tick() {
		if ((cameraKeys[0] && cameraKeys[2]) || (!cameraKeys[0] && !cameraKeys[2])) {
			Main.getMain().getCamera().setMoveY(0);
		} else if (cameraKeys[0]) {
			Main.getMain().getCamera().setMoveY(-1);
		} else if (cameraKeys[2]) {
			Main.getMain().getCamera().setMoveY(1);
		}
		
		if ((cameraKeys[1] && cameraKeys[3]) || (!cameraKeys[1] && !cameraKeys[3])) {
			Main.getMain().getCamera().setMoveX(0);
		} else if (cameraKeys[1]) {
			Main.getMain().getCamera().setMoveX(-1);
		} else if (cameraKeys[3]) {
			Main.getMain().getCamera().setMoveX(1);
		}
	}
}
