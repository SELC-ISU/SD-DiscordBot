package SD.Discord.Games.TOSRoles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

public class RoleController {
	
	private static List<Role> roleList;
	private static List<Role> aliveList;
	private static HashMap<Role, Role> killList;
	private static List<Role> healList;
	private static HashMap<User, Role> roleMap;
	private static List<Role> allRoleList;
	
	private static TextChannel channel;
	private static boolean night;
	
	public RoleController(List<Role> roles, List<Role> rolesAlive, HashMap<User, Role> rolesMap, TextChannel channel) {
		roleList = roles;
		aliveList = rolesAlive;
		healList = new ArrayList<Role>();
		RoleController.channel = channel;
		roleMap = rolesMap;
		night = false;
		List<Role> allRolesList = new ArrayList<Role>();
		allRolesList.add(new Godfather());
		allRolesList.add(new Doctor());
		allRoleList = allRolesList;
		killList = new HashMap<Role, Role>();
	}
	
	public static void setRoleList(List<Role> roleList){
		RoleController.roleList = roleList;
	}
	
	public static void setAliveList(List<Role> aliveList){
		RoleController.aliveList = aliveList;
	}
	
	public static void newKillList(){
		killList = new HashMap<Role, Role>();
	}
	
	public static void setAliveList(TextChannel channel){
		RoleController.channel = channel;
	}
	
	public static void kill(Role killed, Role killer) {
		if (aliveList.contains(allRoleList.get(1))) {
			Role r = allRoleList.get(1);
			if (r instanceof Doctor) {
				Doctor d = (Doctor) r;
				if (d.getHealed().equals(killed)) return;
			}
		}
		if (roleList.contains(killed)) {
			aliveList.remove(killed);
		}
	}
	
	public static void addToKillList(Role killed, Role killer) {
		killList.put(killer, killed);
	}
	
	public static void resetKillList() {
		killList = new HashMap<Role, Role>();
	}
	
	public static void executeKillList() {
		for (Role killer : killList.keySet()) {
			Role killed = killList.get(killer);
			if (isHealed(killed)) continue;
			kill(killed, killer);
			channel.sendMessage("A **" + killer.getAlliance().getAlliance() + "** member has killed **" + killed.getPlayerName() + "** their role was **" + killed.getRoleName() + "**.");
		}
		resetKillList();
		resetHealed();
	}
	
	public static boolean isHealed(Role role) {
		return healList.contains(role);
	}
	
	public static void resetHealed() {
		healList = new ArrayList<Role>();
	}
	
	public static Role getPlayerRole(String userName) {
		for (Role r : roleList) {
			if (r.getPlayerName().equals(userName)) {
				return r;
			}
		}
		return null;
	}
	
	public static List<String> getRolesAsString() {
		List<String> roles = new ArrayList<String>();
		for (Role r : roleList) {
			roles.add(r.getRoleName());
		}
		return roles;
	}
	
	public static List<String> getAliveAsString() {
		List<String> alive = new ArrayList<String>();
		for (Role r : aliveList) {
			alive.add(r.getRoleName());
		}
		return alive;
	}
	
	public static List<String> getAlivePlayersAsString() {
		List<String> alive = new ArrayList<String>();
		for (Role r : aliveList) {
			alive.add(r.getPlayerName());
		}
		return alive;
	}
	
	public static List<Role> getRoleList() {
		return roleList;
	}
	
	public static List<Role> getAliveRoles() {
		return aliveList;
	}
	
	public static List<Role> allPossibleRoles() {
		List<Role> list = new ArrayList<Role>();
		
		list.add(new Godfather());
		list.add(new Doctor());
		
		return list;
	}
	
	public static Role getRole(Role role) {
		for (Role r : roleList) {
			if (r instanceof Role) {
				return r;
			}
		}
		return null;
	}
	
	public static HashMap<User, Role> getRoleMap(){
		return roleMap;
	}
	
	public static List<Role> getHealList(){
		return healList;
	}
	
	public static void setRoleMap(HashMap<User, Role> roleMap){
		RoleController.roleMap = roleMap;
	}
	
	public static User getUserByRole(Role role) {
		for (User u : roleMap.keySet()) {
			if (roleMap.get(u) == role) {
				return u;
			}
		}
		return null;
	}
	
	public static boolean isNight() {
		return night;
	}
	
	public static void toggleNight() {
		night = !night;
	}

}
