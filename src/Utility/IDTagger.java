package Utility;

public class IDTagger {
	
	private int baseID;
	
	public IDTagger(int baseID) {
		this.baseID = baseID;
	}
	
	public int nextID() {
		return baseID++;
	}
	
	public int getCurrentID() {
		return baseID;
	}

}
