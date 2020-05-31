package main.java.projectciv.init;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import main.java.projectciv.hex.HexData.HexAnimalType;
import main.java.projectciv.hex.HexData.HexOreType;
import main.java.projectciv.hex.HexData.HexResourceType;
import main.java.projectciv.hex.HexData.HexType;
import main.java.projectciv.hex.HexData.IRawHexType;
import main.java.projectciv.util.Console;
import main.java.projectciv.util.Console.WarningType;

public class HexDatas {
	private static final Map<HexType, Map<HexResourceType, List<IRawHexType>>> RESOURCE_TYPES = new HashMap<HexType, Map<HexResourceType, List<IRawHexType>>>();
	private static final Random r = new Random();
	
	public static void registerAll() {
		for (HexType type : HexType.values()) {
			RESOURCE_TYPES.put(type, new HashMap<HexResourceType, List<IRawHexType>>());
		}
		
		addResource(HexType.hill, HexResourceType.none);
		addResource(HexType.hill, HexResourceType.forest);
		addResource(HexType.hill, HexResourceType.salt);
		addResource(HexType.hill, HexResourceType.spices);
		addResource(HexType.hill, HexResourceType.dyes);
		addResource(HexType.hill, HexOreType.coal, HexOreType.copper, HexOreType.iron);
		addResource(HexType.hill, HexAnimalType.sheep);
		
		addResource(HexType.mountain, HexResourceType.none);
		addResource(HexType.mountain, HexResourceType.gems);
		addResource(HexType.mountain, HexResourceType.fruit);
		addResource(HexType.mountain, HexResourceType.salt);
		addResource(HexType.mountain, HexOreType.gold, HexOreType.iron, HexOreType.silver);
		addResource(HexType.mountain, HexAnimalType.goat);
		
		addResource(HexType.none, HexResourceType.none);
		addResource(HexType.none, HexResourceType.cotton);
		addResource(HexType.none, HexResourceType.desert);
		addResource(HexType.none, HexResourceType.forest);
		addResource(HexType.none, HexResourceType.fruit);
		addResource(HexType.none, HexAnimalType.cow, HexAnimalType.sheep, HexAnimalType.elephant);
		
		addResource(HexType.water, HexResourceType.none);
		addResource(HexType.water, HexAnimalType.clam, HexAnimalType.fish, HexAnimalType.whale);
	}
	
	public static List<HexResourceType> getResourceTypes(HexType type) {
		return Arrays.asList(RESOURCE_TYPES.get(type).keySet().toArray(new HexResourceType[0]));
	}
	
	public static List<IRawHexType> getOreTypes(HexType type) {
		return RESOURCE_TYPES.get(type).get(HexResourceType.ore);
	}
	
	public static List<IRawHexType> getAnimalTypes(HexType type) {
		return RESOURCE_TYPES.get(type).get(HexResourceType.animals);
	}
	
	public static HexResourceType getRandomResourceType(HexType type) {
		HexResourceType[] rr = RESOURCE_TYPES.get(type).keySet().toArray(new HexResourceType[0]);
		return rr[r.nextInt(rr.length)];
	}
	
	public static IRawHexType getRandomOreType(HexType type) {
		List<IRawHexType> rr = RESOURCE_TYPES.get(type).get(HexResourceType.ore);
		return rr.get(r.nextInt(rr.size()));
	}
	
	public static IRawHexType getRandomAnimalType(HexType type) {
		List<IRawHexType> rr = RESOURCE_TYPES.get(type).get(HexResourceType.animals);
		return rr.get(r.nextInt(rr.size()));
	}
	
	/** Do not use this method for {@link HexResourceType#animals} or {@link HexResourceType#ore}! */
	private static void addResource(HexType type, HexResourceType hexResourceType) {
		if (hexResourceType == HexResourceType.animals || hexResourceType == HexResourceType.ore) {
			Console.print(WarningType.Warning, "Do not use this method for HexResourceType#animals or HexResourceType#ore!");
			return;
		}
		
		Map<HexResourceType, List<IRawHexType>> m = new HashMap<HexResourceType, List<IRawHexType>>();
		m.putAll(RESOURCE_TYPES.get(type));
		m.put(hexResourceType, null);
		
		RESOURCE_TYPES.put(type, m);
		Console.print(WarningType.RegisterDebug, "Added '" + hexResourceType + "' to '" + type + "'!");
		return;
	}
	
	/** Automatically adds {@link HexResourceType#animals} */
	private static void addResource(HexType type, HexAnimalType... types) {
		Map<HexResourceType, List<IRawHexType>> m = new HashMap<HexResourceType, List<IRawHexType>>();
		m.putAll(RESOURCE_TYPES.get(type));
		m.put(HexResourceType.animals, Arrays.asList(types));
		
		RESOURCE_TYPES.put(type, m);
		Console.print(WarningType.RegisterDebug, "Added '" + HexResourceType.animals + "' to '" + type + "'! (also added " + Arrays.asList(types) + ")");
		return;
	}
	
	/** Automatically adds {@link HexResourceType#ore} */
	private static void addResource(HexType type, HexOreType... types) {
		Map<HexResourceType, List<IRawHexType>> m = new HashMap<HexResourceType, List<IRawHexType>>();
		m.putAll(RESOURCE_TYPES.get(type));
		m.put(HexResourceType.ore, Arrays.asList(types));
		
		RESOURCE_TYPES.put(type, m);
		Console.print(WarningType.RegisterDebug, "Added '" + HexResourceType.ore + "' to '" + type + "'! (also added " + Arrays.asList(types) + ")");
		return;
	}
}
