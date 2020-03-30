package SD.Discord.Games.TOSRoles;

public enum Alliance {

	TOWN("Town"), MAFIA("Mafia"), NEUTRAL("Neutral");
	
	private String allName;
	
	Alliance(String string){
		allName = string;
	}
	
	public String getAlliance() {
		return allName;
	}
	
}
