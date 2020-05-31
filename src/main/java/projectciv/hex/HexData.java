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
		none    (35, 15, 25),
		water   (10, 70, 10),
		hill    (20, 10, 45),
		mountain(10, 10, 40);
		
		private final int chanceForNone, chanceForWater, chanceForHill;
		
		private HexType(int chanceForNone, int chanceForWater, int chanceForHill) {
			this.chanceForNone = chanceForNone;
			this.chanceForWater = chanceForWater;
			this.chanceForHill = chanceForHill;
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
	}
	
	public enum HexResourceType {
		none,
		forest,
		fruit,
		ore,
		desert,
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
		cow,
		sheep,
		goat,
		clam,
		elephant,
		fish,
		whale;
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
