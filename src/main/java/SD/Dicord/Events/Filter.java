package SD.Dicord.Events;
import SD.Discord.Bot.Variables;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
public class Filter extends ListenerAdapter {
	@Override
	public void onMessageReceived(MessageReceivedEvent filter) {
		if(Variables.isProfanityEnabled()) {
			if ( filter.getAuthor().isBot()) return;
			String [] BAD_WORDS = {"anal", "ass", "fuck", "motherfucker", "bitch", "balls" ,"cock"};
			String [] message = filter.getMessage().getContentRaw().split(" ");
			MessageChannel f = filter.getChannel();
			
			for (int i = 0; i < message.length; i++) {
				for( int j = 0; j < BAD_WORDS.length; j++) {
					if(message[i].equalsIgnoreCase(BAD_WORDS[j])) {
						f.sendMessage(filter.getAuthor().getAsMention() + "\n" +  "This is a warning please don't say that" + "\n" + "Your message has been deleted").queue();
						filter.getMessage().delete().queue();
					}
				}
				
			}
		}
	}
}
