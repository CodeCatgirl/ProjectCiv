package main.java.projectciv;

import java.awt.Canvas;
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
import main.java.projectciv.util.CollectionUtils;
import main.java.projectciv.util.Console;
import main.java.projectciv.util.Console.WarningType;
import main.java.projectciv.util.math.Vec2i;

/**
 * @author -Unknown-
 */
public class Main {
	private static Main main;
	private static GameLoop gameLoop;
	
	public static final int HUD_WIDTH = 640, HUD_HEIGHT = 360;
	
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
		new Window("Project Civ " + titleSuffix);
		gameLoop = new GameLoop();
		Console.getTimeExample();
		Console.print(WarningType.Info, "Window size: " + new Vec2i(getWindowWidth(), getWindowHeight()));
		Console.print(WarningType.Info, "Starting!");
		
		preInit();
		init();
		postInit();
		Window.canRender = true;
		
		gameLoop.start();
	}
	
	private void preInit() {
		Console.print(WarningType.Info, "Pre-Initialization started...");
		
		keyHandler = new KeyHandler();
		mouseHandler = new MouseHandler();
		hexHandler = new HexHandler();
		
		addRenderer(new RendererHex());
		addRenderer(new HudResources());
		
		for (IRenderer r : renderers) {
			r.setupTextures();
		}
		
		HexDatas.registerAll();
		
		Window.setupComponents();
		
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
	
	private void addRenderer(IRenderer r) {
		renderers.add(r);
		Console.print(WarningType.RegisterDebug, "Registered '" + r.getClass().getSimpleName() + "' as a renderer!");
	}
	
	public List<IRenderer> getRenderers() {
		return CollectionUtils.copyList(renderers);
	}
	
	public static int getWindowWidth() {
		return Window.getGamePanel().getWidth();
	}
	
	public static int getWindowHeight() {
		return Window.getGamePanel().getHeight();
	}
	
	public static double getScale() {
		return Window.getGamePanel().getScale();
	}
	
	public static int getExtraWidth() {
		return Window.getFrame().getWidth() - Window.getGamePanel().getWidth();
	}
	
	public static int getExtraHeight() {
		return Window.getFrame().getHeight() - Window.getGamePanel().getHeight();
	}
	
	public static int getFPS() {
		return Window.getGamePanel().getFPS();
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
	
	public static KeyHandler getKeyHandler() {
		return keyHandler;
	}
	
	public static MouseHandler getMouseHandler() {
		return mouseHandler;
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
		}
		
		@Override
		public void run() {
			Console.print(WarningType.Info, "Started run loop!");
			requestFocus();
			long lastTime = System.nanoTime();
			double amountOfTicks = 60.0, ns = 1000000000 / amountOfTicks, delta = 0;
			
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
				} else {
					stop();
				}
			}
		}
		
		private void render() {
			if (!Window.canRender) {
				return;
			}
			
			Window.getGamePanel().repaint();
		}
		
		private synchronized void stop() {
			try {
				thread.join();
				running = false;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
