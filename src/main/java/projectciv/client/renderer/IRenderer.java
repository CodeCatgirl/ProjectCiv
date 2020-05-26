package main.java.projectciv.client.renderer;

import java.awt.image.BufferedImage;

import main.java.projectciv.util.Console;
import main.java.projectciv.util.Console.WarningType;
import main.java.projectciv.util.GetResource;
import main.java.projectciv.util.GetResource.TextureType;

public interface IRenderer {
	
	public void setupTextures();
	
	public default BufferedImage getTexture(TextureType type, String name) {
		BufferedImage i = GetResource.getTexture(type, name);
		
		if (i == GetResource.nil) {
			Console.print(WarningType.TextureDebug, "Unable to register '" + name + "' for " + getClass().getSimpleName());
		} else {
			Console.print(WarningType.TextureDebug, "Registered '" + name + "' for" + getClass().getSimpleName());
		}
		
		return i;
	}
}
