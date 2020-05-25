package main.java.projectciv.client;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import main.java.projectciv.Main;
import main.java.projectciv.hexes.Hex;
import main.java.projectciv.util.GetResource;
import main.java.projectciv.util.GetResource.TextureType;

public class HexRenderer {
	
	private static BufferedImage hexx;
	
	public void setupTextures() {
		hexx = GetResource.getTexture(TextureType.hex, "grass");
	}
	
	public void render(Graphics2D g) { //TODO setup better
		for (Hex hex : Main.getMain().getHexHandler().getHexes()) {
			g.drawImage(hexx, hex.getPosX(), hex.getPosY(), Hex.SIZE.getX(), Hex.SIZE.getY(), null);
			
			int xw = Hex.SIZE.getX() - g.getFontMetrics().stringWidth("x:" + hex.getHexX()) / 2;
			int yw = Hex.SIZE.getX() - g.getFontMetrics().stringWidth("y:" + hex.getHexY()) / 2;
			int zw = Hex.SIZE.getX() - g.getFontMetrics().stringWidth("z:" + hex.getHexZ()) / 2;
			
			if (hex.getHexX() == 0 && hex.getHexY() == 0 && hex.getHexZ() == 0) {
				g.setColor(Color.BLUE);
			} else {
				g.setColor(Color.RED);
			}
			
			g.drawString("x:" + hex.getHexX(), xw + hex.getPosX() - 48, hex.getPosY() + 26);
			g.drawString("y:" + hex.getHexY(), yw + hex.getPosX() - 29, hex.getPosY() + 58);
			g.drawString("z:" + hex.getHexZ(), zw + hex.getPosX() - 12, hex.getPosY() + 26);
		}
	}
}
