package SD.Discord.Util;

import java.util.List;

import SD.Discord.Bot.Variables;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Avatar extends ListenerAdapter{
	
	public void onMessageReceived(MessageReceivedEvent e) {
		if (e.getAuthor().isBot()) return;
		if (!e.getMessage().getContentRaw().toLowerCase().startsWith(Variables.getPrefix() + "avatar")) return;
		String toSearch = "";
		if (e.getMessage().getContentRaw().length() == Variables.getPrefix().length() + 6) {
			toSearch = e.getAuthor().getName();
		}
		else {
			toSearch = e.getMessage().getContentRaw().substring(Variables.getPrefix().length() + 7).toLowerCase().split(" ")[0];
		}
		List<Member> memList = e.getGuild().getMembersByEffectiveName(toSearch, true);
		for (Member m : memList) {
			String avatarURL = m.getUser().getAvatarUrl();
			e.getChannel().sendMessage(m.getEffectiveName() + "'s avatar can be found at " + avatarURL).queue();
		}
	}

}
