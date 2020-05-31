package main.java.projectciv;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.List;

import main.java.projectciv.city.CityHandler;
import main.java.projectciv.client.Camera;
import main.java.projectciv.client.KeyHandler;
import main.java.projectciv.client.MouseHandler;
import main.java.projectciv.client.Window;
import main.java.projectciv.client.gui.HudResources;
import main.java.projectciv.client.renderer.IRenderer;
import main.java.projectciv.client.renderer.RendererHex;
import main.java.projectciv.hex.HexHandler;
import main.java.projectciv.init.HexDatas;
import main.java.projectciv.util.Console;
import main.java.projectciv.util.Console.WarningType;
import main.java.projectciv.util.math.MathH;
import main.java.projectciv.util.math.Vec2i;

/**
 * @author -Unknown-
 */
public class Main {
	private static Main main;
	private static GameLoop gameLoop;
	
	public static final int HUD_WIDTH = 640, HUD_HEIGHT = 360;
	private int width = HUD_WIDTH, height = HUD_HEIGHT, w2, h2;
	private static int fps;
	private double scale;
	
	public static boolean isDebug;
	
	public static final String ASSETS_LOCATION = "/main/resources/projectciv/assets/";
	
	private static List<IRenderer> renderers = new ArrayList<IRenderer>();
	
	private static HexHandler hexHandler;
	private static CityHandler cityHandler;
	private static KeyHandler keyHandler;
	private static MouseHandler mouseHandler;
	private static Camera camera;
	
	public static void main(String args[]) {
		StringBuilder sb = new StringBuilder();
		for (String s : args) {
			sb.append(s + " ");
			if (s.equalsIgnoreCase("-debug")) {
				isDebug = true;
			}
		}
		
		main = new Main();
		main.start(sb.toString());
	}
	
	private synchronized void start(String titleSuffix) {
		new Window("Project Civ " + titleSuffix, gameLoop = new GameLoop());
		Console.getTimeExample();
		Console.print(WarningType.Info, "Window size: " + new Vec2i(width, height));
		Console.print(WarningType.Info, "Starting!");
		
		preInit();
		init();
		postInit();
		
		gameLoop.start();
	}
	
	private void preInit() {
		Console.print(WarningType.Info, "Pre-Initialization started...");
		
		gameLoop.setupComponents();
		
		keyHandler = new KeyHandler();
		mouseHandler = new MouseHandler();
		hexHandler = new HexHandler();
		
		addRenderer(new RendererHex());
		addRenderer(new HudResources());
		
		for (IRenderer r : renderers) {
			r.setupTextures();
		}
		
		HexDatas.registerAll();
		
		gameLoop.setupComponents();
		
		Console.print(WarningType.Info, "Pre-Initialization finished!");
	}
	
	private void init() {
		Console.print(WarningType.Info, "Initialization started...");
		
		hexHandler.setup();
		camera = new Camera();
		
		Console.print(WarningType.Info, "Initialization finished!");
	}
	
	private void postInit() {
		Console.print(WarningType.Info, "Post-Initialization started...");
		
		cityHandler = new CityHandler();
		
		Console.print(WarningType.Info, "Post-Initialization finished!");
	}
	
	private void tick() {
		keyHandler.tick();
		camera.tick();
	}
	
	private static final RenderingHints RH = new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	
	private void render(Graphics2D g) { //TODO use better
		scale = Math.min((double) width / HUD_WIDTH, (double) height / HUD_HEIGHT);
		int w = (int) Math.ceil((HUD_WIDTH * scale));
		w2 = (int) Math.ceil((width - w) / scale);
		int h = (int) Math.ceil((HUD_HEIGHT * scale));
		h2 = (int) Math.ceil((height - h) / scale);
		
		g.setRenderingHints(RH);
		g.scale(scale, scale);
		
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, width, height);
		g.translate(w2 / 2, h2 / 2);
		
		AffineTransform at = g.getTransform();
		g.scale(camera.getZoom(), camera.getZoom());
		
		g.translate(-camera.getPosX(), -camera.getPosY());
		for (IRenderer r : renderers) {
			if (!r.isHud()) {
				r.render(g);
			}
		}
		g.translate(camera.getPosX(), camera.getPosY());
		g.setTransform(at);
		
		for (IRenderer r : renderers) {
			if (r.isHud()) {
				r.render(g);
			}
		}
		
		if (isDebug) {
			g.setColor(Color.GREEN);
			g.drawString("FPS: " + fps, 1, 25);
			g.drawString("ZOOM:" + camera.getZoom(), 1, 36);
		}
		
		g.setColor(Color.BLACK);
		g.fillRect((int) (w / scale), 0, w2, height);
		g.fillRect(-w2, 0, w2, height);
		g.fillRect(0, (int) (h / scale), width, h2);
		g.fillRect(0, -h2, width, h2);
		g.dispose();
	}
	
	private void addRenderer( IRenderer r) {
		renderers.add(r);
		Console.print(WarningType.RegisterDebug, "Registered '" + r.getClass().getSimpleName() + "' as a renderer!");
	}
	
	public int getWindowWidth() {
		return width;
	}
	
	public int getWindowHeight() {
		return height;
	}
	
	public int getExtraWidth() {
		return w2;
	}
	
	public int getExtraHeight() {
		return h2;
	}
	
	public double getScale() {
		return scale;
	}
	
	public int getFPS() {
		return fps;
	}
	
	public HexHandler getHexHandler() {
		return hexHandler;
	}
	
	public Camera getCamera() {
		return camera;
	}
	
	public CityHandler getCityHandler() {
		return cityHandler;
	}
	
	public static Main getMain() {
		return main;
	}
	
	public void requestFocus() {
		gameLoop.requestFocus();
	}
	
	public class GameLoop extends Canvas implements Runnable {
		private static final long serialVersionUID = 1L;
		
		private boolean running = false;
		private Thread thread;
		
		private void start() {
			thread = new Thread(this);
			thread.start();
			running = true;
			
			Console.print(WarningType.Info, "Started thread!");
			resize();
		}
		
		@Override
		public void run() {
			Console.print(WarningType.Info, "Started run loop!");
			requestFocus();
			long lastTime = System.nanoTime(), timer = System.currentTimeMillis();
			double amountOfTicks = 60.0, ns = 1000000000 / amountOfTicks, delta = 0;
			int frames = 0;
			
			while (running) {
				long now = System.nanoTime();
				delta += (now - lastTime) / ns;
				lastTime = now;
				
				while (delta >= 1) {
					tick();
					delta--;
				}
				
				if (running) {
					render();
					frames++;
					
					if (System.currentTimeMillis() - timer > 1000) {
						timer += 1000;
						fps = frames;
						frames = 0;
					}
				} else {
					stop();
				}
			}
		}
		
		private void render() {
			if (!Window.canRender) {
				return;
			}
			
			BufferStrategy bs = getBufferStrategy();
			if (bs == null) {
				createBufferStrategy(3);
				Console.print(WarningType.Info, "Started render loop!");
				return;
			}
			
			Main.this.render((Graphics2D) bs.getDrawGraphics());
			
			bs.show();
		}
		
		private synchronized void stop() {
			try {
				thread.join();
				running = false;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		private void setupComponents() {
			addKeyListener(keyHandler);
			addMouseListener(mouseHandler);
			addMouseMotionListener(mouseHandler);
			addMouseWheelListener(mouseHandler);
			addComponentListener(new ComponentListener() {
				@Override public void componentShown(ComponentEvent e) {}
				@Override public void componentMoved(ComponentEvent e) {}
				@Override public void componentHidden(ComponentEvent e) {}
				
				@Override
				public void componentResized(ComponentEvent e) {
					resize();
				}
			});
		}
		
		private void resize() {
			width = MathH.clamp(getWidth(), 0, Integer.MAX_VALUE);
			height = MathH.clamp(getHeight(), 0, Integer.MAX_VALUE);
		}
	}
}
