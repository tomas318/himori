package Inputs;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter{
	
	private static final int TOTALKEYS = 256;
	private static final boolean[] KeyInputs = new boolean [TOTALKEYS];
	private static final boolean[] LastKeyInputs = new boolean[TOTALKEYS];

	@Override
	public void keyPressed(KeyEvent e) {
		KeyInputs[e.getKeyCode()] = true;
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		KeyInputs[e.getKeyCode()] = false;
	}
	
	public static void update() {
		for (int i = 0; i < TOTALKEYS; i++) {
			LastKeyInputs[i] = KeyInputs[i];
		}
	}
	
	public static boolean isKeyDown(int keyIndex) {
		return KeyInputs[keyIndex];
	}
	
	public static boolean wasKeyPressed(int keyIndex) {
		return isKeyDown(keyIndex) && !LastKeyInputs[keyIndex];
	}
	
	public static boolean wasKeyReleased(int keyIndex) {
		return !isKeyDown(keyIndex) && LastKeyInputs[keyIndex];
	}
	
	public static void whatKey() {
		for (int i = 0; i < TOTALKEYS; i++) {
			System.out.println("" + KeyInputs[i]);
		}
	}
}
