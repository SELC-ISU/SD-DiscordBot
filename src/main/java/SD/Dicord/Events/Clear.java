package SD.Dicord.Events;
import java.awt.Color;
import java.util.List;

import SD.Discord.Bot.Variables;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
public class Clear extends ListenerAdapter {
	public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
		String [] args = e.getMessage().getContentRaw().split("\\s+");
		
		if(args[0].equalsIgnoreCase( Variables.getPrefix() + "Clear")) {
			if(args.length < 2) {
				EmbedBuilder usage = new EmbedBuilder();
				usage.setColor(0xff3923);
				usage.setTitle("Specify amount to delete");
				usage.setDescription("Usage: !Clear [# of messages]");
				e.getChannel().sendMessage(usage.build()).queue();
			}
			else {
				
				try {
					List<Message> messages = e.getChannel().getHistory().retrievePast(Integer.parseInt(args[1])).complete();
					e.getChannel().deleteMessages(messages).queue();
					//success
					EmbedBuilder success = new EmbedBuilder();
					success.setColor(Color.GREEN);
					success.setTitle("Succesully deleted " + args[1] + " messages.");
					e.getChannel().sendMessage(success.build()).queue();
				}
				catch(IllegalArgumentException a) {
					//too many messages
					if(a.toString().startsWith("java.lang.IllegalArgumentException: Message retrieval")) {
						EmbedBuilder error = new EmbedBuilder();
						error.setColor(0xff3923);
						error.setTitle("Too many messages selected");
						error.setDescription("Between 1-100 message can be deleted at once");
						e.getChannel().sendMessage(error.build()).queue();
					}
					else {
						//messages too old 
						EmbedBuilder error = new EmbedBuilder();
						error.setColor(0xff3923);
						error.setTitle("Selected messages are older than 2 weeks");
						error.setDescription("Messages older than 2 weeks cannot be deleted");
						e.getChannel().sendMessage(error.build()).queue();
					}
				}	
			}
		}
	}
}
