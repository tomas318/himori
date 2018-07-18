package Data;

import java.io.Serializable;
import java.util.ArrayList;

import Entities.Entity;
import Entities.Player;

public class SaveData implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String saveFile;
	private int[] saveData = new int[50];
	
	public SaveData(String fileName, int currentWorld, int currentLevel, int unlockedWorld, int unlockedLevel, int playerLives, Player player) {
		saveData[0] = currentWorld;
		saveData[1] = currentLevel;
		saveData[2] = unlockedWorld;
		saveData[3] = unlockedLevel;
		saveData[4] = playerLives;
		for (int i = 0; i < player.getInventory().getInventorySize() *  2; i = i + 2) {
			saveData[i + 5] = player.getInventory().getItems()[i/2];
			saveData[i + 6] = player.getInventory().getItemCount()[i/2];
		}
		saveFile = fileName;
	}
	
	public int[] getSaveData() {
		return saveData;
	}
	
	public String getFileName() {
		return saveFile;
	}
}
