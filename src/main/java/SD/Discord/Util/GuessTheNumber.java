package SD.Discord.Util;

import java.util.Random;

import SD.Discord.Bot.Variables;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuessTheNumber extends ListenerAdapter{
	
	boolean isSearching = false;
	Random r;
	int num;
	
	public void onMessageReceived(MessageReceivedEvent e) {
		if ( e.getAuthor().isBot()) return;
		String message = e.getMessage().getContentRaw();
		MessageChannel c = e.getChannel();
		if (isSearching && message.equalsIgnoreCase(Integer.toString(num))) {
			c.sendMessage("Correct!").queue();;
			isSearching = false;
		}
		if (!message.startsWith(Variables.getPrefix())) return;
		message = message.substring(Variables.getPrefix().length());
		if (message.equalsIgnoreCase("guessthenumber")) {
			r = new Random();
			num = r.nextInt(10) + 1;
			c.sendMessage("Guess a number between 1 and 10...").queue();
			isSearching = true;
		}
	}

}
