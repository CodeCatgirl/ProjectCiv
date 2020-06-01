package main.java.projectciv.client;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;

import javax.swing.JPanel;

public class AspectRatioKeeperLayout implements LayoutManager {
	private static Component fakeComponent = new JPanel();
	
	public AspectRatioKeeperLayout() {
		fakeComponent.setPreferredSize(new Dimension(0, 0));
	}
	
	@Override
	public void layoutContainer(Container cont) {
		Insets insets = cont.getInsets();
		int maxWidth = cont.getWidth() - (insets.left + insets.right);
		int maxHeight = cont.getHeight() - (insets.top + insets.bottom);
		
		Dimension targetDim = getScaledDimension(getSingleComponent(cont).getPreferredSize(), new Dimension(maxWidth, maxHeight));
		
		getSingleComponent(cont).setBounds((int) (maxWidth - targetDim.getWidth()) / 2, (int) (maxHeight - targetDim.getHeight()) / 2, (int) targetDim.getWidth(),
				(int) targetDim.getHeight());
	}
	
	private Component getSingleComponent(Container cont) {
		if (cont.getComponentCount() > 1) {
			throw new IllegalArgumentException(getClass().getSimpleName() + " can not handle more than one component");
		}
		
		return (cont.getComponentCount() == 1) ? cont.getComponent(0) : fakeComponent;
	}
	
	private Dimension getScaledDimension(Dimension imageSize, Dimension boundary) {
		double ratio = Math.min(boundary.getWidth() / imageSize.getWidth(), boundary.getHeight() / imageSize.getHeight());
		return new Dimension((int) (imageSize.width * ratio), (int) (imageSize.height * ratio));
	}
	
	@Override
	public Dimension minimumLayoutSize(Container comp) {
		return preferredLayoutSize(comp);
	}
	
	@Override
	public Dimension preferredLayoutSize(Container comp) {
		return getSingleComponent(comp).getPreferredSize();
	}
	
	@Override public void removeLayoutComponent(Component comp) {}
	@Override public void addLayoutComponent(String str, Component comp) {}
}
