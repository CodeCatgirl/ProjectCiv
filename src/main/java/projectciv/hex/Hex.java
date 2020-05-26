package main.java.projectciv.hex;

import java.awt.Polygon;
import java.util.Random;

import main.java.projectciv.Main;
import main.java.projectciv.city.City;
import main.java.projectciv.util.math.Vec2i;
import main.java.projectciv.util.math.Vec3i;

public class Hex {
	public static final Vec2i SIZE = new Vec2i(60, 70);
	
	private final Polygon hex = new Polygon();
	private final Vec2i pos;
	private final Vec3i hexPos;
	private final HexResourceType hexResourceType;
	
	private City city;
	
	Hex(Vec3i hexPos, HexResourceType hexResourceType) {
		this.pos = setupPos(hexPos);
		this.hexPos = hexPos;
		this.hexResourceType = hexResourceType;
		
		for (int i = 0; i < 6; i++) {
			hex.addPoint((int) ((SIZE.getX() / 2) + pos.getX() + (SIZE.getY() / 2) * Math.cos((1 + i * 2) * Math.PI / 6)),
					(int) ((SIZE.getY() / 2) + pos.getY() + (SIZE.getY() / 2) * Math.sin((1 + i * 2) * Math.PI / 6)));
		}
	}
	
	Hex(Vec3i hexPos) {
		this(hexPos, HexResourceType.getRandom());
	}
	
	public void setCity(City city) {
		this.city = city;
	}
	
	private Vec2i setupPos(Vec3i hexPos) {
		Vec2i vec = new Vec2i(Main.HUD_WIDTH / 2 - SIZE.getX() / 2, Main.HUD_HEIGHT / 2 - SIZE.getY() / 2);
		
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
	
	public Vec3i getHexPos() {
		return hexPos;
	}
	
	public HexResourceType getResourceType() {
		return hexResourceType;
	}
	
	public Polygon getHexPoly() {
		return hex;
	}
	
	public boolean hasCity() {
		return city != null ? true : false;
	}
	
	public City getCity() {
		return city;
	}
	
	public boolean intersects(int x, int y, int w, int h) {
		return hex.intersects(x, y, w, h);
	}
	
	public enum HexResourceType {
		none, field, forest, hill, mountain;
		
		private static final Random r = new Random();
		
		public static HexResourceType getRandom() {
			return HexResourceType.values()[r.nextInt(values().length)];
		}
	}
}
