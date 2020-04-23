package SD.Dicord.Events;

import net.dv8tion.jda.api.hooks.ListenerAdapter;
//import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class HelloEvent extends ListenerAdapter 
{

	public void onMessageReceived(MessageReceivedEvent event)
	{
		if ( event.getMessage().getContentRaw().contains("hello"))
		{
			event.getChannel().sendMessage("Hello welcome " + event.getMember().getAsMention()).queue();
		}
		
	}
}