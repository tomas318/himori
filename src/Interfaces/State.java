package Interfaces;

import java.awt.Graphics2D;

import Managers.GameStateManager;

public interface State {

		public void initialize();
		public void enterState();
		public void exitState();
		public void tick(GameStateManager gameStateManager);
		public void render(Graphics2D g);
		public String getStateName();
		
}
