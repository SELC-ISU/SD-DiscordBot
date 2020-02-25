package SD.Discord.Bot;

import java.util.ArrayList;
import java.util.List;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;

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
	
	public static String replaceWithRoles(String messageInit, Guild guild) {
		String message = messageInit;
		List<String> possibleMentions = new ArrayList<String>();
		String tempString = "";
		boolean building = false;
		for (int i = 0; i < message.length(); i++) {
			if (message.charAt(i) == '@') {
				building = true;
				continue;
			}
			if (building) {
				if (message.charAt(i) == ' ') {
					building = false;
					possibleMentions.add(tempString);
					tempString = "";
					continue;
				}
				else if (i == (message.length() - 1)) {
					tempString += message.charAt(i);
					building = false;
					possibleMentions.add(tempString);
					tempString = "";
					continue;
				}
				else {
					if (message.charAt(i) == '_') {
						tempString += " ";
						continue;
					}
					tempString += message.charAt(i);
					continue;
				}
			}
		}
		for (String mention : possibleMentions) {
			List<Role> roleList = guild.getRolesByName(mention, true);
			List<Member> userList = guild.getMembersByName(mention, true);
			if (!roleList.isEmpty()) {
				Role roleToMention = roleList.get(0);
				message = message.replace("@" + mention.replace(" ", "_"), roleToMention.getAsMention());
			}
			else if (!userList.isEmpty()) {
				Member memberToMention = userList.get(0);
				message = message.replace("@" + mention.replace(" ", "_"), memberToMention.getAsMention());
			}
		}
		return message;
	}
	
}
