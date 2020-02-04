package SD.Discord.Bot;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class WordListener extends ListenerAdapter{

	public void onMessageReceived(MessageReceivedEvent e)
	{
		//bot messages are ignored
		
		if ( e.getAuthor().isBot()) return;
		System.out.println(e.getMessage().getContentRaw());
		if ( e.getMessage().getContentRaw().contains("lit") )
		{
			
			e.getChannel().sendMessage("WOOHOO").queue();
			
		
		}
	}
	
	
}







	
	