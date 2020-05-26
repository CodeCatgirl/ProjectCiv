package main.java.projectciv.client;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import main.java.projectciv.Main;
import main.java.projectciv.util.math.MathH;
import main.java.projectciv.util.math.Vec2i;

public class MouseHandler extends MouseAdapter {
	public static final Vec2i HUD_MOUSE_POS = new Vec2i(), WORLD_MOUSE_POS = new Vec2i();
	
	@Override
	public void mouseReleased(MouseEvent e) {
		updateMousePos(e);
		Main.getMain().getHexHandler().checkHexClicked(WORLD_MOUSE_POS);
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		updateMousePos(e);
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		updateMousePos(e);
	}
	
	private void updateMousePos(MouseEvent e) {
		HUD_MOUSE_POS.set(MathH.floor(e.getX() / Main.getMain().getScale()) - (Main.getMain().getExtraWidth() / 2),
				MathH.floor(e.getY() / Main.getMain().getScale()) - (Main.getMain().getExtraHeight() / 2));
		
		if (Main.getMain().getCamera() != null) {
			WORLD_MOUSE_POS.set(HUD_MOUSE_POS.getX(), HUD_MOUSE_POS.getY());
			WORLD_MOUSE_POS.add(MathH.floor(Main.getMain().getCamera().getPosX()), MathH.floor(Main.getMain().getCamera().getPosY()));
		}
	}
}
