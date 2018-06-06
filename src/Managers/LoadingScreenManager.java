package Managers;

import GameStates.LoadingScreen;
import Graphical.Texture;

public class LoadingScreenManager {

	private LoadingScreen loadingScreen;

	public LoadingScreenManager() {
		loadingScreen = new LoadingScreen(new Texture("loadingscreen"));
		loadingScreen.setLocationRelativeTo(null);
		loadingScreen.setMaxLoad(1000);
		loadingScreen.setVisible(true);
		
		for(int i = 0; i < 1000; i++) {
			for (int j = 0; j <= 100000; j++) {
				String test = "ewf" + (i+j);
			}
			loadingScreen.setLoad(i);
		}
		loadingScreen.setVisible(true);
	}
}
