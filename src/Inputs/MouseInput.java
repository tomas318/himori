package Inputs;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseInput extends MouseAdapter{
	
	private static final int TOTALMOUSEBUTTONS = 15;
	private static final boolean[] MouseInputs = new boolean[TOTALMOUSEBUTTONS];
	private static final boolean[] LastMouseInputs = new boolean[TOTALMOUSEBUTTONS];
	private static int x = -1, y = -1;

	@Override
	public void mousePressed(MouseEvent e) {
		MouseInputs[e.getButton()] = true;
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		MouseInputs[e.getButton()] = false;
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		super.mouseDragged(e);
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		x = e.getX();
		y = e.getY();
	}
	
	public static void update() {
		for (int i = 0; i < TOTALMOUSEBUTTONS; i++) {
			LastMouseInputs[i] = MouseInputs[i];
		}
	}
	
	public static boolean isMouseButtonDown(int mousebutton) {
		return MouseInputs[mousebutton];
	}
	
	public static boolean wasMouseButtonPressed(int mousebutton) {
		return isMouseButtonDown(mousebutton) && !LastMouseInputs[mousebutton];
	}
	
	public static boolean wasMouseButtonReleased(int mousebutton) {
		return !isMouseButtonDown(mousebutton) && LastMouseInputs[mousebutton];
	}
	
	public static int getX() {
		return x;
	}
	
	public static int getY() {
		return y;
	}
}
