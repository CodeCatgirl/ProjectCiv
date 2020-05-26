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
	
	public void setup() {
		int radius = 3;//, oRadius = 1;
		
		for (int zz = -radius; zz <= radius; zz++) {
			for (int xx = -radius; xx <= radius; xx++) {
				if (xx + zz <= radius && -(xx + zz) <= radius) {
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
