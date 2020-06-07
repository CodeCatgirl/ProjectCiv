package main.java.projectciv.hex;

import java.awt.Polygon;
import java.awt.Rectangle;

import main.java.projectciv.city.City;
import main.java.projectciv.init.HexDatas;
import main.java.projectciv.util.EnumDirection;
import main.java.projectciv.util.math.Vec2i;
import main.java.projectciv.util.math.Vec3i;

public class Hex {
	public static final Vec2i SIZE = new Vec2i((int) (60 * 2f), (int) (70 * 2f));
	
	private final Polygon hex = new Polygon();
	private final Vec2i pos;
	private final Vec3i hexPos;
	
	private HexData hexData;
	private City city;
	
	Hex(Vec3i hexPos, HexData hexData) {
		this.pos = HexUtils.hexPosToXYPos(hexPos);
		this.hexPos = hexPos;
		this.hexData = hexData;
		
		for (int i = 0; i < 6; i++) {
			hex.addPoint((int) ((SIZE.getX() / 2) + pos.getX() + (SIZE.getY() / 2) * Math.cos((1 + i * 2) * Math.PI / 6)),
					(int) ((SIZE.getY() / 2) + pos.getY() + (SIZE.getY() / 2) * Math.sin((1 + i * 2) * Math.PI / 6)));
		}
	}
	
	Hex(Vec3i hexPos) {
		this(hexPos, HexDatas.getSmartHexType(hexPos));
	}
	
	public Hex getHexAt(EnumDirection dir) {
		return HexUtils.getHexAt(hexPos, dir, 1);
	}
	
	public Hex getHexAt(EnumDirection dir, int dist) {
		return HexUtils.getHexAt(hexPos, dir, dist);
	}
	
	public boolean isHexNear(Hex hex) {
		return isHexNear(hex, 1);
	}
	
	public boolean isHexNear(Hex hex, int dist) {
		for (EnumDirection dir : EnumDirection.values()) {
			for (int i = 1; i < dist + 1; i++) {
				Hex h = getHexAt(dir, dist);
				
				if (h != null && h == hex) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	public void setCity(City city) {
		this.city = city;
	}
	
	public void setHexData(HexData hexData) {
		this.hexData = hexData;
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
	
	public HexData getHexData() {
		return hexData;
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
	
	public boolean intersects(Rectangle r) {
		return hex.intersects(r.x, r.y, r.width, r.height);
	}
	
	public boolean intersects(int x, int y, int w, int h) {
		return hex.intersects(x, y, w, h);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((hexPos == null) ? 0 : hexPos.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Hex) {
			if (((Hex) obj).hexPos.equals(hexPos)) {
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public String toString() {
		return "(" + hexData + ", " + hexPos.toString() + ")";
	}
}
