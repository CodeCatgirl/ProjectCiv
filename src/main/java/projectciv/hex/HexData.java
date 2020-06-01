package main.java.projectciv.hex;

import main.java.projectciv.init.HexDatas;

public class HexData {
	
	private final HexType hexType;
	private final HexResourceType hexResourceType;
	private final IRawHexType hexRawType;
	
	public HexData(HexType hexType, HexResourceType hexResourceType) {
		this.hexType = hexType;
		this.hexResourceType = hexResourceType;
		this.hexRawType = hexResourceType == HexResourceType.animals ? HexDatas.getRandomAnimalType(hexType) :
				hexResourceType == HexResourceType.ore ? HexDatas.getRandomOreType(hexType) : null;
	}
	
	public HexData(HexType hexType) {
		this(hexType, HexDatas.getRandomResourceType(hexType));
	}
	
	public HexType getHexType() {
		return hexType;
	}
	
	public HexResourceType getHexResourceType() {
		return hexResourceType;
	}
	
	public IRawHexType getHexRawType() {
		return hexRawType;
	}
	
	public enum HexType {
		none    (45, 20, 25, 10, 5, 8),
		water   (7, 120, 5, 2, 15, 5),
		hill    (20, 10, 40, 5, 2, 20),
		desert  (20, 2, 10, 50, 0, 3),
		swamp   (30, 15, 2, 0, 30, 0),
		mountain(15, 2, 40, 3, 0, 70),
		ocean   (0, 0, 0, 0, 0, 0);
		
		private final int chanceForNone, chanceForWater, chanceForHill, chanceForDesert, chanceForSwamp, chanceForMountain, all;
		
		private HexType(int chanceForNone, int chanceForWater, int chanceForHill, int chanceForDesert, int chanceForSwamp, int chanceForMountain) {
			this.chanceForNone = chanceForNone;
			this.chanceForWater = chanceForWater;
			this.chanceForHill = chanceForHill;
			this.chanceForDesert = chanceForDesert;
			this.chanceForMountain = chanceForMountain;
			this.chanceForSwamp = chanceForSwamp;
			all = chanceForNone + chanceForWater + chanceForHill + chanceForDesert + chanceForSwamp + chanceForMountain;
		}
		
		public int getChanceForNone() {
			return chanceForNone;
		}
		
		public int getChanceForWater() {
			return chanceForWater;
		}
		
		public int getChanceForHill() {
			return chanceForHill;
		}
		
		public int getChanceForDesert() {
			return chanceForDesert;
		}
		
		public int getChanceForSwamp() {
			return chanceForSwamp;
		}
		
		public int getChanceForMountain() {
			return chanceForMountain;
		}
		
		public int getAll() {
			return all;
		}
	}
	
	public enum HexResourceType { //TODO add rarity
		none,
		forest,
		fruit,
		ore,
		cotton,
		gems,
		dyes,
		animals,
		salt,
		spices;
	}
	
	public interface IRawHexType {
	}
	
	public enum HexAnimalType implements IRawHexType {
		cows,
		sheep,
		goats,
		clams,
		elephants,
		fish,
		whales,
		crabs,
		frogs;
	}
	
	public enum HexOreType implements IRawHexType {
		coal,
		iron,
		copper,
		silver,
		gold;
	}
	
	@Override
	public String toString() {
		return "(" + hexType + ", " + hexResourceType + ", " + hexRawType + ")";
	}
}
