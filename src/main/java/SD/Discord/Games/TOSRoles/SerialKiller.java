package SD.Discord.Games.TOSRoles;

import java.util.ArrayList;
import java.util.List;

import net.dv8tion.jda.api.entities.User;

public class SerialKiller implements Role{

	public SerialKiller() {
		
	}
	
	
	@Override
	public String getRoleName() {
		return "Serial Killer";
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
			if (RoleController.getRoleMap().get(u) instanceof SerialKiller) {
				return u.getName();
			}
		}
		return null;
	}

	@Override
	public Alliance getAlliance() {
		return Alliance.NEUTRAL;
	}


	@Override
	public String getDescription() {
		return "The Serial Killer is a psychotic criminal who wants everyone to die. "
				+ "He chooses which member of the town will die that night. "
				+ "If he chooses to kill the Godfather, nothing will happen as he is immune to attacks."
				+ "The Serial Killer wins if he is the last one standing.";
	}

}
