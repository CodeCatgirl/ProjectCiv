package main.java.projectciv.hex;

import java.awt.Polygon;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import main.java.projectciv.Main;
import main.java.projectciv.city.City;
import main.java.projectciv.hex.HexData.HexType;
import main.java.projectciv.init.HexDatas;
import main.java.projectciv.util.EnumDirection;
import main.java.projectciv.util.math.MathH;
import main.java.projectciv.util.math.Vec2i;
import main.java.projectciv.util.math.Vec3i;

public class Hex {
	public static final Vec2i SIZE = new Vec2i(60, 70);
	
	private final Polygon hex = new Polygon();
	private final Vec2i pos;
	private final Vec3i hexPos;
	private final HexData hexData;
	
	private City city;
	
	Hex(Vec3i hexPos, HexData hexData) {
		this.pos = setupPos(hexPos);
		this.hexPos = hexPos;
		this.hexData = hexData;
		
		for (int i = 0; i < 6; i++) {
			hex.addPoint((int) ((SIZE.getX() / 2) + pos.getX() + (SIZE.getY() / 2) * Math.cos((1 + i * 2) * Math.PI / 6)),
					(int) ((SIZE.getY() / 2) + pos.getY() + (SIZE.getY() / 2) * Math.sin((1 + i * 2) * Math.PI / 6)));
		}
	}
	
	Hex(Vec3i hexPos) {
		this(hexPos, getSmartHexType(hexPos));
	}
	
	public static HexData getSmartHexType(Vec3i hexPos) {
		if (hexPos.equals(new Vec3i(0, HexHandler.RADIUS, -HexHandler.RADIUS)) || hexPos.equals(Vec3i.ZERO)) {
			return new HexData(HexType.none, HexDatas.getRandomResourceType(HexType.none));
		}
		
		Random r = new Random();
		List<HexType> types = new ArrayList<HexType>();
		
		for (EnumDirection dir : EnumDirection.values()) {
			Hex h = getHexAt(hexPos, dir);
			if (h != null) {
				types.add(h.getHexData().getHexType());
			}
		}
		
		List<HexType> toPick = new ArrayList<HexType>();
		
		for (HexType type : types) {
			int ri = r.nextInt(100);
			int hill = type.getChanceForHill(), none = type.getChanceForNone(), water = type.getChanceForWater();
			
			water += none;
			hill += water;
			
			if (MathH.within(ri, 0, none)) {
				toPick.add(HexType.none);
			} else if (MathH.within(ri, none + 1, water)) {
				toPick.add(HexType.water);
			} else if (MathH.within(ri, water + 1, hill)) {
				toPick.add(HexType.hill);
			} else if (MathH.within(ri, hill + 1, 100)) {
				toPick.add(HexType.mountain);
			}
		}
		
		HexType hh = toPick.get(r.nextInt(toPick.size()));
		return new HexData(hh, HexDatas.getRandomResourceType(hh));
	}
	
	public Hex getHexAt(EnumDirection dir) {
		return getHexAt(this.hexPos, dir, 1);
	}
	
	public Hex getHexAt(EnumDirection dir, int dist) {
		return getHexAt(this.hexPos, dir, dist);
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
