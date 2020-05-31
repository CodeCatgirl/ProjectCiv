package main.java.projectciv.util.math;

import main.java.projectciv.util.ICopyable;

public class Vec2f implements ICopyable<Vec2f> {
	
	public static final Vec2f ZERO = new Vec2f();
	protected float x, y;
	
	public Vec2f() {
		x = 0;
		y = 0;
	}
	
	public Vec2f(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public Vec2f(float xy) {
		this.x = xy;
		this.y = xy;
	}
	
	public Vec2f(Vec2i vec) {
		this(vec.getX(), vec.getY());
	}
	
	public Vec2f(Vec2f vec) {
		this(vec.getX(), vec.getY());
	}
	
	public Vec2f add(Vec2f vec) {
		this.x += vec.x;
		this.y += vec.y;
		return this;
	}
	
	public Vec2f add(float x, float y) {
		this.x += x;
		this.y += y;
		return this;
	}
	
	public Vec2f addX(float x) {
		this.x += x;
		return this;
	}
	
	public Vec2f addY(float y) {
		this.y += y;
		return this;
	}
	
	public void set(Vec2f vec) {
		this.x = vec.x;
		this.y = vec.y;
	}
	
	public void set(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public float getBothMulti() {
		return x * y;
	}
	
	public float difference(Vec2i vec) {
		return Math.abs(x - vec.x) + Math.abs(y - vec.y);
	}
	
	@Override
	public Vec2f copy() {
		return new Vec2f(this);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Vec2f) {
			if (((Vec2f) obj).x == x && ((Vec2f) obj).y == y) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(x);
		result = prime * result + Float.floatToIntBits(y);
		return result;
	}
	
	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}
