package main.java.projectciv.client.renderer;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.java.projectciv.Main;
import main.java.projectciv.client.Camera;
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
	
	private Camera cam;
	
	private final boolean isDebug = true;
	
	@Override
	public void setupTextures() {
		for (HexType hex : HexType.values()) {
			hexes.put(hex, getTexture(TextureType.hex, hex.toString() + (isDebug ? "-" : "")));
		}
		for (HexResourceType hex : HexResourceType.values()) {
			if (hex != HexResourceType.none && hex != HexResourceType.animals && hex != HexResourceType.ore) {
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
		List<Hex> hasCity = new ArrayList<Hex>(), noCity = new ArrayList<Hex>();
		
		if (cam == null) {
			cam = Main.getMain().getCamera();
		}
		
		for (Hex hex : Main.getMain().getHexHandler().getVisibleHexes()) {
			g.drawImage(hexes.get(hex.getHexData().getHexType()), hex.getPosX(), hex.getPosY(), Hex.SIZE.getX(), Hex.SIZE.getY(), null);
			
			if (hex.hasCity()) {
				hasCity.add(hex);
			} else {
				noCity.add(hex);
			}
			
			if (cityColor == null && hex.hasCity()) {
				cityColor = hex.getCity().getColor();
			}
			
			if (hex.getHexData().getHexResourceType() != HexResourceType.none && hex.getHexData().getHexResourceType() != HexResourceType.animals && hex.getHexData().getHexResourceType() != HexResourceType.ore) {
				g.drawImage(resources.get(hex.getHexData().getHexResourceType()), hex.getPosX() + 4, hex.getPosY() + 64, MathH.floor(Hex.SIZE.getX() / 3f),
						MathH.floor(Hex.SIZE.getY() / 3f), null);
			}
			if (hex.getHexData().getHexRawType() != null) {
				g.drawImage(subResources.get(hex.getHexData().getHexRawType()), hex.getPosX() + 4,
						hex.getPosY() + 64, MathH.floor(Hex.SIZE.getX() / 3f), MathH.floor(Hex.SIZE.getY() / 3f), null);
			}
		}
		
		g.setColor(Color.BLACK);
		g.setStroke(smallStroke);
		for (Hex hex : noCity) {
			g.draw(hex.getHexPoly());
		}
		
		g.setColor(cityColor);
		g.setStroke(bigStroke);
		for (Hex hex : hasCity) {
			g.draw(hex.getHexPoly());
		}
	}
	
	private static final BasicStroke bigStroke = new BasicStroke(4), smallStroke =  new BasicStroke(1);
	
	@Override
	public boolean isHud() {
		return false;
	}
}
