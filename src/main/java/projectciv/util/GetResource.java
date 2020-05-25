package main.java.projectciv.util;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.java.projectciv.Main;

public class GetResource {
	public static final BufferedImage nil = getTexture(TextureType.none, "nil");
	
	public static BufferedImage getTexture(TextureType location, String textureName) {
		String newLoc = location == TextureType.none ? "" : location.toString().toLowerCase() + "/";
		String loc = Main.ASSETS_LOCATION + "textures/";
		
		if (GetResource.class.getResourceAsStream(loc + newLoc + textureName + ".png") == null) {
			Console.print(Console.WarningType.Error, "Cannot find texture : '" + loc + newLoc + textureName + ".png'");
			return nil;
		}
		
		try {
			return ImageIO.read(GetResource.class.getResourceAsStream(loc + newLoc + textureName + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
			return nil;
		}
	}
	
	public static BufferedImage getTexture(String textureName) {
		return getTexture(TextureType.none, textureName);
	}
	
	public static Font getFont(String fontName, float size) {
		if (GetResource.class.getResourceAsStream(Main.ASSETS_LOCATION + "fonts/" + fontName + ".ttf") == null) {
			Console.print(Console.WarningType.Error, "Cannot find font : '" + Main.ASSETS_LOCATION + "fonts/" + fontName + ".ttf'");
			return null;
		}
		
		Font font = null;
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, GetResource.class.getResourceAsStream(Main.ASSETS_LOCATION + "fonts/" + fontName + ".ttf")).deriveFont(size);
			GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(font);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return font;
	}
	
	public enum TextureType {
		none, hex;
	}
}
