package main.java.projectciv.city;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import main.java.projectciv.Main;
import main.java.projectciv.util.Console;
import main.java.projectciv.util.Console.WarningType;
import main.java.projectciv.util.math.Vec3i;

public class City {
	private final List<Vec3i> ownedHexes = new ArrayList<Vec3i>();
	private final Vec3i origin;
	private final Color color;
	
	public City(Vec3i origin) {
		Random r = new Random();
		this.color = new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256));
		this.origin = origin;

		if (Main.getMain().getHexHandler().getHex(origin).hasCity()) {
			Console.print(WarningType.Warning, "Cannot place a city there as there's already a city there!");
			return;
		}
		
		buyHex(origin);
	}
	
	public void buyHex(Vec3i vec) {
		if (!Main.getMain().getHexHandler().getHex(vec).hasCity()) {
			Main.getMain().getHexHandler().getHex(vec).setCity(this);
			ownedHexes.add(vec);
		}
	}
	
	public Vec3i getOrigin() {
		return origin;
	}
	
	public Color getColor() {
		return color;
	}
}
