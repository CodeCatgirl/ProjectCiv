package main.java.projectciv.hexes;

import main.java.projectciv.util.math.Vec2i;
import main.java.projectciv.util.math.Vec3i;

public class Hex {
	public static final Vec2i SIZE = new Vec2i(60, 70);
	
	protected final Vec2i pos;
	protected final Vec3i hexPos; //TODO
	
	Hex(Vec3i hexPos) {
		this.pos = hexPosToXYPos(hexPos);
		this.hexPos = hexPos;
	}
	
	private Vec2i hexPosToXYPos(Vec3i hexPos) {
		Vec2i vec = new Vec2i(240, 140);
		
		vec.addX(hexPos.getX() * SIZE.getX());
		vec.addY(hexPos.getZ() * -(Hex.SIZE.getY() / 4 - Hex.SIZE.getY()));
		vec.addX(hexPos.getZ() * SIZE.getX() / 2);
		
		return vec;
	}
	
	public int getPosX() {
		return pos.getX();
	}
	
	public int getPosY() {
		return pos.getY();
	}
	
	public int getHexX() {
		return hexPos.getX();
	}
	
	public int getHexY() {
		return hexPos.getY();
	}
	
	public int getHexZ() {
		return hexPos.getZ();
	}
}
