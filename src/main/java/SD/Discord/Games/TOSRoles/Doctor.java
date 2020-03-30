package SD.Discord.Games.TOSRoles;

import java.util.ArrayList;
import java.util.List;

import net.dv8tion.jda.api.entities.User;

public class Doctor implements Role{

	private Role healed;
	private boolean healedSelf;
	
	public Doctor() {
		healedSelf = false;
		healed = null;
	}
	
	@Override
	public String getRoleName() {
		return "Doctor";
	}

	@Override
	public String getNightMessage(List<Role> alivePlayers) {
		List<String> aliveList = new ArrayList<String>();
		for (Role r : alivePlayers) {
			aliveList.add(r.getPlayerName());
		}
		return "Respond with a player to heal: " + aliveList.toString() + ".";
	}

	
	public void setHealed(Role role) {
		if (role instanceof Doctor) {
			if (healedSelf) {
				healed = null;
			}
			healedSelf = true;
			healed = role;
		}
		else {
			healed = role;
		}
		RoleController.getHealList().add(healed);
	}
	
	public Role getHealed() {
		return healed;
	}
	
	@Override
	public void runNightDuty() {
		heal(healed);
	}

	@Override
	public void runDayDuty() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void kill(Role role) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void heal(Role role) {
		
	}

	@Override
	public String getPlayerName() {
		for (User u : RoleController.getRoleMap().keySet()) {
			if (RoleController.getRoleMap().get(u) instanceof Doctor) {
				return u.getName();
			}
		}
		return null;
	}

	@Override
	public Alliance getAlliance() {
		return Alliance.TOWN;
	}

	@Override
	public String getDescription() {
		return "The doctor can save people from attacks."
				+ "As the doctor, you may heal yourself once but others as many times as you want.";
	}



}
