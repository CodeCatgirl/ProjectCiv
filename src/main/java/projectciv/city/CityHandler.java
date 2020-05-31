package main.java.projectciv.city;

import java.util.ArrayList;
import java.util.List;

import main.java.projectciv.util.math.Vec3i;

public class CityHandler {
	private float coinsPT, foodPT, woodPT, stonePT; //TODO cost = time / resourcePT
	
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
	
	public float getCoinsPT() {
		return coinsPT;
	}
	
	public float getFoodPT() {
		return foodPT;
	}
	
	public float getWoodPT() {
		return woodPT;
	}
	
	public float getStonePT() {
		return stonePT;
	}
}
