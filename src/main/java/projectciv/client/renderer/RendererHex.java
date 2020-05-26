package main.java.projectciv.client.renderer;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import main.java.projectciv.Main;
import main.java.projectciv.hex.Hex;
import main.java.projectciv.util.GetResource.TextureType;

public class RendererHex implements IRenderer {
	
	private static BufferedImage hexx;
	
	@Override
	public void setupTextures() {
		hexx = getTexture(TextureType.hex, "grass");
	}
	
	public void render(Graphics2D g) {
		for (Hex hex : Main.getMain().getHexHandler().getHexes()) {
			g.drawImage(hexx, hex.getPosX(), hex.getPosY(), Hex.SIZE.getX(), Hex.SIZE.getY(), null);
			
			int xw = Hex.SIZE.getX() - g.getFontMetrics().stringWidth("x:" + hex.getHexX()) / 2;
			int yw = Hex.SIZE.getX() - g.getFontMetrics().stringWidth("y:" + hex.getHexY()) / 2;
			int zw = Hex.SIZE.getX() - g.getFontMetrics().stringWidth("z:" + hex.getHexZ()) / 2;
			
			int tw = Hex.SIZE.getX() - g.getFontMetrics().stringWidth(hex.getResourceType().toString()) / 2;
			
			if (Main.isDebug) {
				g.setColor(hex.getHexX() == 0 ? Color.BLUE : Color.RED);
				g.drawString("x:" + hex.getHexX(), xw + hex.getPosX() - 48, hex.getPosY() + 26);
				g.setColor(hex.getHexY() == 0 ? Color.BLUE : Color.RED);
				g.drawString("y:" + hex.getHexY(), yw + hex.getPosX() - 29, hex.getPosY() + 58);
				g.setColor(hex.getHexZ() == 0 ? Color.BLUE : Color.RED);
				g.drawString("z:" + hex.getHexZ(), zw + hex.getPosX() - 12, hex.getPosY() + 26);
				
				g.setColor(hex.hasCity() ? hex.getCity().getColor() : Color.BLACK);
				g.drawString(hex.getResourceType().toString(), tw + hex.getPosX() - 29, hex.getPosY() + 41);
			}
		}
	}
}
