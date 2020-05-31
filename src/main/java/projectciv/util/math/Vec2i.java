package main.java.projectciv.util.math;

import main.java.projectciv.util.ICopyable;

public class Vec2i implements ICopyable<Vec2i> {
	
	public static final Vec2i ZERO = new Vec2i();
	protected int x, y;
	
	public Vec2i() {
		x = 0;
		y = 0;
	}
	
	public Vec2i(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Vec2i(int xy) {
		this.x = xy;
		this.y = xy;
	}
	
	public Vec2i(Vec2f vec) {
		this((int) vec.getX(), (int) vec.getY());
	}
	
	public Vec2i(Vec2i vec) {
		this(vec.getX(), vec.getY());
	}
	
	public Vec2i add(Vec2i vec) {
		this.x += vec.x;
		this.y += vec.y;
		return this;
	}
	
	public Vec2i add(int x, int y) {
		this.x += x;
		this.y += y;
		return this;
	}
	
	public Vec2i addX(int x) {
		this.x += x;
		return this;
	}
	
	public Vec2i addY(int y) {
		this.y += y;
		return this;
	}
	
	public void set(Vec2i vec) {
		this.x = vec.x;
		this.y = vec.y;
	}
	
	public void set(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getBothMulti() {
		return x * y;
	}
	
	public int difference(Vec2i vec) {
		return Math.abs(x - vec.x) + Math.abs(y - vec.y);
	}
	
	@Override
	public Vec2i copy() {
		return new Vec2i(this);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Vec2i) {
			if (((Vec2i) obj).x == x && ((Vec2i) obj).y == y) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}
	
	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}
