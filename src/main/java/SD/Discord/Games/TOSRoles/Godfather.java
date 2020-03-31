package SD.Discord.Games.TOSRoles;

import java.util.ArrayList;
import java.util.List;

import net.dv8tion.jda.api.entities.User;

public class Godfather implements Role{

	public Godfather() {
		
	}
	
	
	@Override
	public String getRoleName() {
		return "Godfather";
	}
	
	@Override
	public String getNightMessage(List<Role> alivePlayers) {
		List<String> aliveList = new ArrayList<String>();
		for (Role r : alivePlayers) {
			aliveList.add(r.getPlayerName());
		}
		return "Respond with a player to kill: " + aliveList.toString() + ".";
	}
	
	public void setKilled(Role role) {
	}
	
	@Override
	public void runNightDuty() {
		
	}

	@Override
	public void runDayDuty() {
		
	}

	@Override
	public void kill(Role role) {
		RoleController.addToKillList(role, this);
	}

	@Override
	public void heal(Role role) {
		
	}

	@Override
	public String getPlayerName() {
		for (User u : RoleController.getRoleMap().keySet()) {
			if (RoleController.getRoleMap().get(u) instanceof Godfather) {
				return u.getName();
			}
		}
		return null;
	}

	@Override
	public Alliance getAlliance() {
		return Alliance.MAFIA;
	}


	@Override
	public String getDescription() {
		return "The Godfather is the leader of the Mafia. "
				+ "They choose which member of the town the mafia kills each night. "
				+ "If they choose to kill a member of the mafia the action will not be taken."
				+ "The Godfather wins when only Mafia are left standing.";
	}

}
