package main.java.projectciv.client.gui;

import java.awt.Color;
import java.awt.Graphics2D;

import main.java.projectciv.Main;
import main.java.projectciv.city.CityHandler;
import main.java.projectciv.client.renderer.IRenderer;
import main.java.projectciv.util.math.MathH;

public class HudResources implements IRenderer {
	
	@Override
	public void setupTextures() {
		
	}
	
	@Override
	public void render(Graphics2D g) {
		g.setColor(new Color(50, 50, 50));
		g.fillRect(0, 0, Main.HUD_WIDTH, 14);
		g.setColor(Color.YELLOW);
		
		CityHandler ch = Main.getMain().getCityHandler();
		
		float cpt = MathH.roundTo(ch.getCoinsPT(), 3);
		g.drawString("Coins Per Tick: " + (cpt > 0 ? "+" : "") + cpt, 1, 11);
	}
	
	@Override
	public boolean isHud() {
		return true;
	}
}
