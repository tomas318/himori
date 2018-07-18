package Managers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import Data.SaveData;
import GameStates.LevelSelector;
import Inventory.Inventory;

public class ResourceManager {
	
	private byte[] keyBytes;
	private byte[] ivBytes;
	private byte[] input;
	private SecretKeySpec key;
	private IvParameterSpec ivSpec;
	private Cipher cipher;
	private int enc_len;
	
	public ResourceManager() {
		String keypass = "lv39eptlvuhaqqsr";
		keyBytes = keypass.getBytes();
		Random rand = new SecureRandom();
		ivBytes = new byte[16];
		rand.nextBytes(ivBytes);
		key = new SecretKeySpec(keyBytes, 0, keyBytes.length, "AES");
		ivSpec = new IvParameterSpec(ivBytes);
		try {
			cipher = Cipher.getInstance("AES");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		}
	}

	public void saveFile(SaveData data) {
		String saveData = "";
		for (int i = 0; i < data.getSaveData().length; i++) {
			saveData += "" + data.getSaveData()[i] + " ";
		}
		input = saveData.getBytes();
		try {
			cipher.init(Cipher.ENCRYPT_MODE, key);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}
		byte[] encrypted = new byte[cipher.getOutputSize(input.length)];
		try {
			enc_len = cipher.update(input, 0, input.length, encrypted, 0);
			enc_len += cipher.doFinal(encrypted, enc_len);
		} catch (ShortBufferException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		String encryptedData = new String(encrypted);
		try (BufferedWriter saveCreator = new BufferedWriter(new FileWriter(new File("./saveData/" + data.getFileName() + ".txt")))){
			saveCreator.write(encryptedData, 0, encryptedData.length());
			saveCreator.newLine();
			saveCreator.write("" + enc_len);
			saveCreator.flush();
			saveCreator.close();
		} catch (IOException x) {
			System.err.format("IOException: %s%n", x);
		}
	}
	
	public void loadFile(String fileName) {
		try (BufferedReader saveReader = new BufferedReader(new FileReader(new File("./saveData/" + fileName + ".txt")))){
			String data = saveReader.readLine();
			String enc_lens = saveReader.readLine();
			enc_len = Integer.parseInt(enc_lens);
			saveReader.close();
			try {
				cipher.init(Cipher.DECRYPT_MODE, key);
			} catch (InvalidKeyException e) {
				e.printStackTrace();
			}
			byte[] encrypted = data.getBytes();
			byte[] decrypted = new byte[cipher.getOutputSize(enc_len)];
			int dec_len = cipher.update(encrypted, 0, enc_len, decrypted, 0);
			dec_len += cipher.doFinal(decrypted, dec_len);
			String newdata = new String(decrypted);
			String tokens[] = newdata.split("\\s+");
			int[] realdata = new int[tokens.length];
			tokens[tokens.length - 1] = "0";
			for (int i = 0; i < tokens.length; i++) {
				realdata[i] = Integer.parseInt(tokens[i]);
			}
			Main.Main.currentWorld = realdata[0];
			Main.Main.currentLevel = realdata[1];
			Main.Main.unlockedWorld = realdata[2];
			Main.Main.unlockedLevel = realdata[3];
			LevelUtility.LevelMap.player.setLives(realdata[4]);
			LevelUtility.LevelMap.player.setInventory(new Inventory(320, 240));
			for (int i = 5; i < realdata.length; i = i + 2) {
				if (i != realdata.length - 1) {
					LevelUtility.LevelMap.player.getInventory().addItem(realdata[i], realdata[i+1]);
				}
			}
			Main.Main.gsm.addState(new LevelSelector());
			Main.Main.gsm.setState("LevelSelectorWorld1");
		} catch (IOException x) {
			System.err.format("IOException: %s%n", x);
		} catch (ShortBufferException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
	}
	
}
