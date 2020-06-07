package main.java.projectciv.client;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.util.List;

import javax.swing.JPanel;

import main.java.projectciv.Main;
import main.java.projectciv.client.renderer.IRenderer;

public class GamePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private static final RenderingHints RH = new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	private final Main main;
	private double scale;
	
	static {
		//RH.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); //lag?
	}
	
	public GamePanel() {
		main = Main.getMain();
	}
	
	@Override
	public void paintComponent(Graphics g2) {
		super.paintComponent(g2);
		if (!Window.canRender) {
			return;
		}
		
		Graphics2D g = (Graphics2D) g2;
		g.setRenderingHints(RH);
		
		Camera camera = main.getCamera();
		List<IRenderer> renderers = main.getRenderers();
		
		AffineTransform at = g.getTransform();
		g.scale(camera.getZoom(), camera.getZoom());
		g.translate(-camera.getPosX(), -camera.getPosY());
		g.setClip((int) camera.getPosX(), (int) camera.getPosY(), (int) (Main.getWindowWidth() / camera.getZoom()), (int) (Main.getWindowHeight() / camera.getZoom()));
		for (IRenderer r : renderers) {
			if (!r.isHud()) {
				r.render(g);
			}
		}
		g.setTransform(at);
		
		scale = Math.min((double) getWidth() / Main.HUD_WIDTH, (double) getHeight() / Main.HUD_HEIGHT);
		
		g.scale(scale, scale);
		for (IRenderer r : renderers) {
			if (r.isHud()) {
				r.render(g);
			}
		}
		if (Main.isDebug) {
			g.setColor(Color.GREEN);
			g.drawString("FPS: " + Main.getFPS(), 1, 25);
			g.drawString("ZOOM:" + camera.getZoom(), 1, 36);
		}
		g.dispose();
		
		long now = System.nanoTime();
		delta += (now - lastTime) / ns;
		lastTime = now;
		
		while (delta >= 1) {
			delta--;
		}
		
		frames++;
		
		if (System.currentTimeMillis() - timer > 1000) {
			timer += 1000;
			fps = frames;
			frames = 0;
		}
	}
	
	private long lastTime = System.nanoTime(), timer = System.currentTimeMillis();
	private double amountOfTicks = 60.0, ns = 1000000000 / amountOfTicks, delta = 0;
	private int frames = 0, fps;
	
	public int getFPS() {
		return fps;
	}
	
	public double getScale() {
		return scale;
	}
}
