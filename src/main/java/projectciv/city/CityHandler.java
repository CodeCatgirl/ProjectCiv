package main.java.projectciv.city;

import java.util.ArrayList;
import java.util.List;

import main.java.projectciv.util.math.Vec3i;

public class CityHandler {
	private int coin, food, wood, stone;
	
	private final List<City> cities = new ArrayList<City>();
	
	public CityHandler() {
		try {
			cities.add(new City(new Vec3i()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<City> getCities() {
		return cities;
	}
}
