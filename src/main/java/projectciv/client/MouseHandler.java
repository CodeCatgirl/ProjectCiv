package main.java.projectciv.client;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import main.java.projectciv.Main;
import main.java.projectciv.util.math.MathH;
import main.java.projectciv.util.math.Vec2i;

public class MouseHandler extends MouseAdapter {
	public static final Vec2i HUD_MOUSE_POS = new Vec2i(), WORLD_MOUSE_POS = new Vec2i(), HUD_LAST_CLICKED_POS = new Vec2i();
	private boolean isDragging;
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		int scroll = e.getWheelRotation();
		
		if (scroll == 1) {
			Main.getMain().getCamera().decreaseZoom();
		} else if (scroll == -1) {
			Main.getMain().getCamera().increaseZoom();
		}
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		HUD_LAST_CLICKED_POS.set(e.getX() - (Main.getMain().getExtraWidth() / 2), e.getY() - (Main.getMain().getExtraHeight() / 2));
		
		isDragging = false;
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		int mouse = e.getButton();
		updateMousePos(e);
		if (!isDragging && mouse == 1) {
			Main.getMain().getHexHandler().checkHexClicked(WORLD_MOUSE_POS);
		}
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		updateMousePos(e);
		if (HUD_LAST_CLICKED_POS.difference(HUD_MOUSE_POS) > 2) {
			isDragging = true;
		}
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		updateMousePos(e);
	}
	
	private void updateMousePos(MouseEvent e) {
		HUD_MOUSE_POS.set(e.getX() - (Main.getMain().getExtraWidth() / 2), e.getY() - (Main.getMain().getExtraHeight() / 2));
		
		if (Main.getMain().getCamera() != null) {
			WORLD_MOUSE_POS.set(MathH.floor(HUD_MOUSE_POS.getX() / Main.getMain().getCamera().getZoom()),
					MathH.floor(HUD_MOUSE_POS.getY() / Main.getMain().getCamera().getZoom()));
			WORLD_MOUSE_POS.add(MathH.floor(Main.getMain().getCamera().getPosX()), MathH.floor(Main.getMain().getCamera().getPosY()));
		}
	}
}
