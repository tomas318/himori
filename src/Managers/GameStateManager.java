package Managers;

import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.Map;

import Interfaces.State;

public class GameStateManager{
	
	private Map<String, State> stateMap;
	private State currentState;
	
	public GameStateManager() {
		stateMap = new HashMap<String, State>();
	}
	
	public void addState(State newState) {
		stateMap.put(newState.getStateName().toUpperCase(), newState);
		newState.initialize();
		if (currentState == null) {
			newState.enterState();
			currentState = newState;
		}
	}
	
	public void setState(String stateName) {
		State newState = stateMap.get(stateName.toUpperCase());
		if (newState == null) {
			System.err.print("State " + stateName + " does not currently exist.");
			return;
		}
		currentState.exitState();
		newState.enterState();
		currentState = newState;
	}
	
	public void tick() {
		currentState.tick(this);
	}
	
	public void render(Graphics2D g) {
		currentState.render(g);
	}
}
