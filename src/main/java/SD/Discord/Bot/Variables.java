package SD.Discord.Bot;

public class Variables {
	
	private static String prefix;
	
	public Variables() {
		prefix = "!";
	}
	
	public static String getPrefix() {
		return prefix;
	}
	
	public static boolean setPrefix(String prefix) {
		if (prefix.length() > 4 || containsLetters(prefix)) {
			return false;
		}
		Variables.prefix = prefix;
		return true;
	}
	
	private static boolean containsLetters(String string) {
		for (char c : string.toCharArray()) {
			if ((c > 64 && c < 91) || (c > 96 && c < 123)) {
				return true;
			}
		}
		return false;
	}
	
}
