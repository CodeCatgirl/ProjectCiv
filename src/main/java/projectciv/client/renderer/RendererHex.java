package main.java.projectciv.client.renderer;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import main.java.projectciv.Main;
import main.java.projectciv.hex.Hex;
import main.java.projectciv.hex.HexData.HexAnimalType;
import main.java.projectciv.hex.HexData.HexOreType;
import main.java.projectciv.hex.HexData.HexResourceType;
import main.java.projectciv.hex.HexData.HexType;
import main.java.projectciv.hex.HexData.IRawHexType;
import main.java.projectciv.util.GetResource.TextureType;
import main.java.projectciv.util.math.MathH;

public class RendererHex implements IRenderer {
	
	private static Map<HexType, BufferedImage> hexes = new HashMap<HexType, BufferedImage>();
	private static Map<HexResourceType, BufferedImage> resources = new HashMap<HexResourceType, BufferedImage>();
	private static Map<IRawHexType, BufferedImage> subResources = new HashMap<IRawHexType, BufferedImage>();
	
	@Override
	public void setupTextures() {
		for (HexType hex : HexType.values()) {
			hexes.put(hex, getTexture(TextureType.hex, hex.toString()));
		}
		for (HexResourceType hex : HexResourceType.values()) {
			if (hex != HexResourceType.none) {
				resources.put(hex, getTexture(TextureType.hex_resources, hex.toString()));
			}
		}
		for (IRawHexType hex : HexOreType.values()) {
			subResources.put(hex, getTexture(TextureType.hex_resources, hex.toString()));
		}
		for (IRawHexType hex : HexAnimalType.values()) {
			subResources.put(hex, getTexture(TextureType.hex_resources, hex.toString()));
		}
	}
	
	@Override
	public void render(Graphics2D g) {
		Color cityColor = null;
		Map<Hex, Boolean> map = new HashMap<Hex, Boolean>();
		
		for (Hex hex : Main.getMain().getHexHandler().getHexes()) {
			g.drawImage(hexes.get(hex.getHexData().getHexType()), hex.getPosX(), hex.getPosY(), Hex.SIZE.getX(), Hex.SIZE.getY(), null);
			
			map.put(hex, hex.hasCity());
			
			if (cityColor == null && hex.hasCity()) {
				cityColor = hex.getCity().getColor();
			}
			
			if (hex.getHexData().getHexResourceType() != HexResourceType.none) {
				g.drawImage(resources.get(hex.getHexData().getHexResourceType()), hex.getPosX() + 4, hex.getPosY() + 64, MathH.floor(Hex.SIZE.getX() / 3f),
						MathH.floor(Hex.SIZE.getY() / 3f), null);
			}
			if (hex.getHexData().getHexRawType() != null) {
				g.drawImage(subResources.get(hex.getHexData().getHexRawType()), hex.getPosX() + Hex.SIZE.getX() - MathH.floor(Hex.SIZE.getX() / 3f) - 4,
						hex.getPosY() + 64, MathH.floor(Hex.SIZE.getX() / 3f), MathH.floor(Hex.SIZE.getY() / 3f), null);
			}
		}
		
		Iterator<Entry<Hex, Boolean>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Entry<Hex, Boolean> pair = it.next();
			g.setColor(pair.getValue() ? cityColor : Color.BLACK);
			g.setStroke(pair.getValue() ? bigStroke : smallStroke);
			g.draw(pair.getKey().getHexPoly());
		}
	}
	
	private static final BasicStroke bigStroke = new BasicStroke(4), smallStroke =  new BasicStroke(1);
	
	@Override
	public boolean isHud() {
		return false;
	}
}
