import java.util.ArrayList;
import java.util.List;

import net.dv8tion.jda.api.entities.User;

public class Investigator implements Role{

	public Investigator() {
		
	}
	
	
	@Override
	public String getRoleName() {
		return "Investigator";
	}
	
	@Override
	public String getNightMessage(List<Role> alivePlayers) {
		List<String> aliveList = new ArrayList<String>();
		for (Role r : alivePlayers) {
			aliveList.add(r.getPlayerName());
		}
		return "Respond with a player to investigate: " + aliveList.toString() + ".";
	}
	
	public void setInvest(Role role) {
	}
	
	@Override
	public void runNightDuty() {
		
	}

	@Override
	public void runDayDuty() {
		
	}

	//@Override
	//public void Investigator(Role role) {
		//RoleController.addToKillList(role, this);
	//}

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
		return "The Investigator is a private eye who secretly gathers information. "
				+ "They choose which member of the town they will investigate. "
				+ "Investigating tells you what the persons class does, not who they are."
				+ "The Investigator wins when only Town are left standing.";
	}

}
