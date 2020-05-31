package main.java.projectciv.client.ui.button;

import java.awt.Rectangle;

import main.java.projectciv.util.math.Vec2i;

public abstract class Button {
	protected final Vec2i pos, size;
	private final Rectangle bounds;
	
	public Button(Vec2i pos, Vec2i size) {
		this.pos = pos;
		this.size = size;
		bounds = new Rectangle(pos.getX(), pos.getY(), size.getX(), size.getY());
	}
	
	public Vec2i getPos() {
		return pos;
	}
	
	public Vec2i getSize() {
		return size;
	}
	
	public Rectangle getBounds() {
		return bounds;
	}
}
