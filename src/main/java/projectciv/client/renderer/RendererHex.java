package main.java.projectciv.client.renderer;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import main.java.projectciv.Main;
import main.java.projectciv.hex.Hex;
import main.java.projectciv.hex.HexData.HexType;
import main.java.projectciv.util.GetResource.TextureType;

public class RendererHex implements IRenderer {
	
	private static BufferedImage hexx;
	
	@Override
	public void setupTextures() {
		hexx = getTexture(TextureType.hex, "grass");
	}
	
	@Override
	public void render(Graphics2D g) {
		for (Hex hex : Main.getMain().getHexHandler().getHexes()) {
			g.drawImage(hexx, hex.getPosX(), hex.getPosY(), Hex.SIZE.getX(), Hex.SIZE.getY(), null);
			
			int tw = Hex.SIZE.getX() - g.getFontMetrics().stringWidth(hex.getHexData().getHexType().toString()) / 2;
			int rtw = Hex.SIZE.getX() - g.getFontMetrics().stringWidth(hex.getHexData().getHexResourceType().toString()) / 2;
			
			if (hex.hasCity()) {
				g.setColor(hex.getCity().getColor());
				g.draw(hex.getHexPoly());
			}
			
			boolean isSpecial = hex.getHexData().getHexRawType() == null ? false : true;
			
			g.setColor(hex.getHexData().getHexType() == HexType.hill ? Color.GREEN :
					hex.getHexData().getHexType() == HexType.mountain ? Color.GRAY : hex.getHexData().getHexType() == HexType.water ? Color.BLUE : Color.BLACK);
			g.drawString(hex.getHexData().getHexType().toString(), tw + hex.getPosX() - 30, hex.getPosY() + (isSpecial ? 30 : 35));
			g.setColor(Color.BLACK);
			g.drawString(hex.getHexData().getHexResourceType().toString(), rtw + hex.getPosX() - 30, hex.getPosY() + (isSpecial ? 40 : 45));
			
			if (isSpecial) {
				int stw = Hex.SIZE.getX() - g.getFontMetrics().stringWidth(hex.getHexData().getHexRawType().toString()) / 2;
				g.drawString(hex.getHexData().getHexRawType().toString(), stw + hex.getPosX() - 30, hex.getPosY() + 50);
			}
		}
	}
	
	@Override
	public boolean isHud() {
		return false;
	}
}
