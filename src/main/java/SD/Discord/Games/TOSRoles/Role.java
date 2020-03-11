package SD.Discord.Games.TOSRoles;

import java.util.List;

public interface Role {
	
	public String getRoleName();
	
	public String getNightMessage(List<Role> alivePlayers);
	
	public void runNightDuty();
	
	public void runDayDuty();
	
	public void kill(Role role);
	
	public void heal(Role role);
	
	public String getPlayerName();
	
	public Alliance getAlliance();
	
	public String getDescription();
	
}
