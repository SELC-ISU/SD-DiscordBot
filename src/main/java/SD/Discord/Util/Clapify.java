package SD.Discord.Util;

import SD.Discord.Bot.Variables;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Clapify extends ListenerAdapter{
	
	public void onMessageReceived(MessageReceivedEvent e) {
		if (e.getAuthor().isBot()) return;
		String message = e.getMessage().getContentRaw();
		if (!message.startsWith(Variables.getPrefix())) return;
		message = message.substring(Variables.getPrefix().length());
		if (!message.startsWith("clapify")) return;
		message = message.substring(8);
		String[] seperable = message.split(" ");
		String finalStr = seperable[0].toUpperCase() + " 👏 ";
		for (int i = 1; i < seperable.length - 1; i++) {
			finalStr += seperable[i].toUpperCase() + " 👏 ";
		}
		finalStr += seperable[seperable.length - 1].toUpperCase();
		MessageChannel c = e.getChannel();
		c.sendMessage(finalStr).queue();;
	}

}
