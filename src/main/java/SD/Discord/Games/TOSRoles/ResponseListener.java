package SD.Discord.Games.TOSRoles;

import java.util.List;

import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ResponseListener extends ListenerAdapter{

	@Override
	public void onMessageReceived(MessageReceivedEvent e) {
		if (!(e.getChannel() instanceof PrivateChannel)) return;
		if (e.getAuthor().isBot()) return;
		if (!RoleController.isNight()) return;
		List<String> aliveList = RoleController.getAlivePlayersAsString();
		String senderName = e.getAuthor().getName();
		String message = e.getMessage().getContentRaw();
		if (aliveList.contains(senderName)) {
			Role role = RoleController.getPlayerRole(senderName);
			if (role instanceof Godfather) {
				Godfather r = (Godfather) role;
				if (aliveList.contains(message)) {
					if (RoleController.getPlayerRole(message).getAlliance() != Alliance.MAFIA) {
						r.kill(RoleController.getPlayerRole(message));
						e.getChannel().sendMessage("You have chosen to kill " + message).queue();
					}
					else {
						e.getChannel().sendMessage("Sorry that doesn't work.").queue();
					}
				}
				else {
					e.getChannel().sendMessage("Sorry that doesn't work.").queue();
				}
			}
			if (role instanceof Doctor) {
				Doctor r = (Doctor) role;
				if (aliveList.contains(message)) {
					r.setHealed(RoleController.getPlayerRole(message));
					e.getChannel().sendMessage("You have chosen to save " + message + ". Keep in mind if you heal yourself more than once, it won't work.").queue();
				}
				else {
					e.getChannel().sendMessage("Sorry that doesn't work.").queue();
				}
			}
		}
	}
	
}
