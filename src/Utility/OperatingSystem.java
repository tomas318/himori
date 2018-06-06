package Utility;

public class OperatingSystem {
	
	private static final String OperatingSystem = System.getProperty("os.name").toUpperCase();
	
	public static String getOSName() {
		return OperatingSystem;
	}
	
	public static boolean isWindows() {
		return OperatingSystem.contains("WIN");
	}
	
	public static boolean isMac() {
		return OperatingSystem.contains("MAC");
	}
	
	public static boolean isUnix() {
		return OperatingSystem.contains("NIX")  || OperatingSystem.contains("NUX") || OperatingSystem.contains("AIX");
	}

}
