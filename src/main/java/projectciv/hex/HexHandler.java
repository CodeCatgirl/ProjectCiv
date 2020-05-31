package main.java.projectciv.hex;

import java.util.ArrayList;
import java.util.List;

import main.java.projectciv.Main;
import main.java.projectciv.util.math.Vec2i;
import main.java.projectciv.util.math.Vec3i;

public class HexHandler {
	private List<Hex> hexes = new ArrayList<Hex>();
	
	public List<Hex> getHexes() {
		return hexes;
	}
	
	public static final int RADIUS = 5;
	
	public void setup() {
		for (int zz = -RADIUS; zz <= RADIUS; zz++) {
			for (int xx = -RADIUS; xx <= RADIUS; xx++) {
				if (xx + zz <= RADIUS && -(xx + zz) <= RADIUS) {
					hexes.add(new Hex(new Vec3i(xx, -(xx + zz), zz)));
					//(Math.abs(xx) <= oRadius && Math.abs(zz) <= oRadius && Math.abs(xx + zz) <= oRadius)
				}
			}
		}
	}
	
	public void checkHexClicked(Vec2i mousePos) {
		for (Hex hex : hexes) {
			if (hex.intersects(mousePos.getX(), mousePos.getY(), 1, 1)) {
				Main.getMain().getCityHandler().getCities().get(0).buyHex(hex.getHexPos());
				break;
			}
		}
	}
	
	public Hex getHex(Vec3i vec) {
		for (Hex hex : hexes) {
			if (hex.getHexPos().equals(vec)) {
				return hex;
			}
		}
		
		return null;
	}
}
