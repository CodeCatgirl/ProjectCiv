package main.java.projectciv.client;

import main.java.projectciv.util.ITickable;
import main.java.projectciv.util.math.Vec2f;

public class Camera implements ITickable {
	
	private final Vec2f pos = new Vec2f();
	private float moveX, moveY;
	
	@Override
	public void tick() {
		pos.addX(moveX * 10f);
		pos.addY(moveY * 10f);
	}
	
	public void setMoveX(float moveX) {
		this.moveX = moveX;
	}
	
	public void setMoveY(float moveY) {
		this.moveY = moveY;
	}
	
	public float getPosX() {
		return pos.getX();
	}
	
	public float getPosY() {
		return pos.getY();
	}
}
