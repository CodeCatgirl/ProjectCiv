package main.java.projectciv.hex;

import main.java.projectciv.Main;
import main.java.projectciv.util.EnumDirection;
import main.java.projectciv.util.math.Vec2i;
import main.java.projectciv.util.math.Vec3i;

public class HexUtils {
	public static Vec2i hexPosToXYPos(Vec3i hexPos) {
		Vec2i vec = new Vec2i(Main.HUD_WIDTH / 2 - Hex.SIZE.getX() / 2, Main.HUD_HEIGHT / 2 - Hex.SIZE.getY() / 2);
		
		vec.addX(hexPos.getX() * Hex.SIZE.getX());
		vec.addY(hexPos.getZ() * -(Hex.SIZE.getY() / 4 - Hex.SIZE.getY()));
		vec.addX(hexPos.getZ() * Hex.SIZE.getX() / 2);
		
		return vec;
	}
	
	public static Hex getHexAt(Vec3i hexPos, EnumDirection dir) {
		return getHexAt(hexPos, dir, 1);
	}
	
	public static Hex getHexAt(Vec3i hexPos, EnumDirection dir, int dist) {
		HexHandler h = Main.getMain().getHexHandler();
		
		switch (dir) {
			case east:
				return h.getHex(hexPos.copy().add(dist, -dist, 0));
			case north_east:
				return h.getHex(hexPos.copy().add(dist, 0, -dist));
			case north_west:
				return h.getHex(hexPos.copy().add(0, dist, -dist));
			case south_east:
				return h.getHex(hexPos.copy().add(0, -dist, dist));
			case south_west:
				return h.getHex(hexPos.copy().add(-dist, 0, dist));
			case west:
				return h.getHex(hexPos.copy().add(-dist, dist, 0));
		}
		
		return null;
	}
}
