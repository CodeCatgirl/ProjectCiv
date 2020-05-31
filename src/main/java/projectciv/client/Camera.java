package main.java.projectciv.client;

import main.java.projectciv.util.ITickable;
import main.java.projectciv.util.math.MathH;
import main.java.projectciv.util.math.Vec2f;

public class Camera implements ITickable {
	
	private final Vec2f pos = new Vec2f();
	private float moveX, moveY, zoom = 1;
	
	@Override
	public void tick() {
		pos.addX(moveX * 10f);
		pos.addY(moveY * 10f);
	}
	
	public void increaseZoom() {
		if (zoom < 2) {
			zoom += 0.05f;
		}
		
		zoom = MathH.roundTo(zoom, 3);
	}
	
	public void decreaseZoom() {
		if (zoom > 0.5f) {
			zoom -= 0.05f;
		}
		
		zoom = MathH.roundTo(zoom, 3);
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
	
	public float getZoom() {
		return zoom;
	}
}
