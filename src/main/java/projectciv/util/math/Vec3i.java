package main.java.projectciv.util.math;

public class Vec3i {
	
	public static final Vec3i ZERO = new Vec3i();
	protected int x, y, z;
	
	public Vec3i() {
		x = 0;
		y = 0;
		z = 0;
	}
	
	public Vec3i(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vec3i(int xyz) {
		this.x = xyz;
		this.y = xyz;
		this.z = xyz;
	}
	
	public Vec3i(Vec3i vec) {
		this(vec.getX(), vec.getY(), vec.getZ());
	}
	
	public void add(Vec3i vec) {
		this.x += vec.x;
		this.y += vec.y;
		this.z += vec.z;
	}
	
	public void add(int x, int y, int z) {
		this.x += x;
		this.y += y;
		this.z += z;
	}
	
	public void addX(int x) {
		this.x += x;
	}
	
	public void addY(int y) {
		this.y += y;
	}
	
	public void addZ(int z) {
		this.z += z;
	}
	
	public void set(Vec3i vec) {
		this.x = vec.x;
		this.y = vec.y;
		this.z = vec.z;
	}
	
	public void set(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public void setZ(int z) {
		this.z = z;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getZ() {
		return z;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Vec3i) {
			if (((Vec3i) obj).x == x && ((Vec3i) obj).y == y && ((Vec3i) obj).z == z) {
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
		result = prime * result + z;
		return result;
	}
	
	@Override
	public String toString() {
		return "(" + x + ", " + y + ", " + z + ")";
	}
}
