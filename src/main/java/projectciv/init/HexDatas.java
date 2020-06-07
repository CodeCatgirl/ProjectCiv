package main.java.projectciv.init;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import main.java.projectciv.hex.Hex;
import main.java.projectciv.hex.HexData;
import main.java.projectciv.hex.HexData.HexAnimalType;
import main.java.projectciv.hex.HexData.HexOreType;
import main.java.projectciv.hex.HexData.HexResourceType;
import main.java.projectciv.hex.HexData.HexType;
import main.java.projectciv.hex.HexData.IRawHexType;
import main.java.projectciv.hex.HexHandler;
import main.java.projectciv.hex.HexUtils;
import main.java.projectciv.util.CollectionUtils;
import main.java.projectciv.util.Console;
import main.java.projectciv.util.Console.WarningType;
import main.java.projectciv.util.EnumDirection;
import main.java.projectciv.util.math.MathH;
import main.java.projectciv.util.math.Vec3i;

public class HexDatas {
	private static final Map<HexType, Map<HexResourceType, List<IRawHexType>>> RESOURCE_TYPES = new HashMap<HexType, Map<HexResourceType, List<IRawHexType>>>();
	private static final Random r = new Random();
	
	public static void registerAll() {
		for (HexType type : HexType.values()) {
			RESOURCE_TYPES.put(type, new HashMap<HexResourceType, List<IRawHexType>>());
		}
		
		addResource(HexType.hills, HexResourceType.none);
		addResource(HexType.hills, HexResourceType.forest);
		addResource(HexType.hills, HexResourceType.salt);
		addResource(HexType.hills, HexResourceType.spices);
		addResource(HexType.hills, HexResourceType.dyes);
		addResource(HexType.hills, HexOreType.coal, HexOreType.copper, HexOreType.iron);
		addResource(HexType.hills, HexAnimalType.sheep);
		
		addResource(HexType.mountains, HexResourceType.none);
		addResource(HexType.mountains, HexResourceType.gems);
		addResource(HexType.mountains, HexResourceType.fruit);
		addResource(HexType.mountains, HexResourceType.salt);
		addResource(HexType.mountains, HexOreType.gold, HexOreType.iron, HexOreType.silver);
		addResource(HexType.mountains, HexAnimalType.goats);
		
		addResource(HexType.none, HexResourceType.none);
		addResource(HexType.none, HexResourceType.cotton);
		addResource(HexType.none, HexResourceType.forest);
		addResource(HexType.none, HexResourceType.fruit);
		addResource(HexType.none, HexAnimalType.cows, HexAnimalType.sheep);
		
		addResource(HexType.water, HexResourceType.none);
		addResource(HexType.water, HexAnimalType.clams, HexAnimalType.fish);
		
		addResource(HexType.ocean, HexResourceType.none);
		addResource(HexType.ocean, HexAnimalType.fish, HexAnimalType.whales);
		
		addResource(HexType.desert, HexResourceType.none);
		addResource(HexType.desert, HexResourceType.dyes);
		addResource(HexType.desert, HexResourceType.spices);
		addResource(HexType.desert, HexResourceType.salt);
		addResource(HexType.desert, HexResourceType.gems);
		addResource(HexType.desert, HexAnimalType.elephants);
		addResource(HexType.desert, HexOreType.gold);
		
		addResource(HexType.swamp, HexResourceType.none);
		addResource(HexType.swamp, HexResourceType.dyes);
		addResource(HexType.swamp, HexResourceType.forest);
		addResource(HexType.swamp, HexResourceType.fruit);
		addResource(HexType.swamp, HexOreType.coal);
		addResource(HexType.swamp, HexAnimalType.fish, HexAnimalType.crabs, HexAnimalType.frogs);
	}
	
	public static List<HexResourceType> getResourceTypes(HexType type) {
		return CollectionUtils.unmodifiableCopyList(Arrays.asList(RESOURCE_TYPES.get(type).keySet().toArray(new HexResourceType[0])));
	}
	
	public static List<IRawHexType> getOreTypes(HexType type) {
		return CollectionUtils.unmodifiableCopyList(RESOURCE_TYPES.get(type).get(HexResourceType.ore));
	}
	
	public static List<IRawHexType> getAnimalTypes(HexType type) {
		return CollectionUtils.unmodifiableCopyList(RESOURCE_TYPES.get(type).get(HexResourceType.animals));
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
	
	public static HexData getSmartHexType(Vec3i hexPos) {
		if (hexPos.equals(new Vec3i(0, HexHandler.RADIUS, -HexHandler.RADIUS)) || hexPos.equals(Vec3i.ZERO)) {
			return new HexData(HexType.none, getRandomResourceType(HexType.none));
		}
		
		Random r = new Random();
		List<HexType> types = new ArrayList<HexType>();
		
		for (EnumDirection dir : EnumDirection.values()) {
			Hex h = HexUtils.getHexAt(hexPos, dir);
			if (h != null) {
				types.add(h.getHexData().getHexType());
			}
		}
		
		List<HexType> toPick = new ArrayList<HexType>();
		
		for (HexType type : types) {
			int hills = type.getChanceForHill(), none = type.getChanceForNone(), water = type.getChanceForWater(), desert = type.getChanceForDesert(),
					swamp = type.getChanceForSwamp(), mountains = type.getChanceForMountain();
			int all = type.getAll(), ri = r.nextInt(all);
			
			water += none;
			hills += water;
			desert += hills;
			swamp += desert;
			mountains += swamp;
			
			if (MathH.within(ri, 0, none)) {
				toPick.add(HexType.none);
			} else if (MathH.within(ri, none + 1, water)) {
				toPick.add(HexType.water);
			} else if (MathH.within(ri, water + 1, hills)) {
				toPick.add(HexType.hills);
			} else if (MathH.within(ri, hills + 1, desert)) {
				toPick.add(HexType.desert);
			} else if (MathH.within(ri, desert + 1, swamp)) {
				toPick.add(HexType.swamp);
			} else if (MathH.within(ri, swamp + 1, mountains)) {
				toPick.add(HexType.mountains);
			}
		}
		
		HexType hh = toPick.get(r.nextInt(toPick.size()));
		return new HexData(hh, getRandomResourceType(hh));
	}
}
