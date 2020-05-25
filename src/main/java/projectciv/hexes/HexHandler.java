package main.java.projectciv.hexes;

import java.util.ArrayList;
import java.util.List;

import main.java.projectciv.util.math.Vec3i;

public class HexHandler {
	private List<Hex> hexes = new ArrayList<Hex>();
	
	public List<Hex> getHexes() {
		return hexes;
	}
	
	public void setup() {
		int radius = 1;
		
		for (int zz = -radius; zz <= radius; zz++) {
			for (int xx = -radius; xx <= radius; xx++) {
				if (xx + zz <= radius && -(xx + zz) <= radius) {
					hexes.add(new Hex(new Vec3i(xx, -(xx + zz), zz)));
				}
			}
		}
	}
}
