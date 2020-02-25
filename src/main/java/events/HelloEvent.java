package events;

import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class HelloEvent extends ListenerAdapter 
{
	public void onGuildMessagedReceived(GuildMessageReceivedEvent event)
	{
		String messageSent = event.getMessage().getContentRaw();
		
		if(messageSent.equalsIgnoreCase("hello")) 
		{
			event.getChannel().sendMessage("Hi!").queue();
		}
		
		
	}
	

}
