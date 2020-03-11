package SD.Discord.Games;


import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import SD.Discord.Bot.Variables;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class RandomGames extends ListenerAdapter{

	public void onMessageReceived(MessageReceivedEvent e)
	{
		//bot messages are ignored
		
		if ( e.getAuthor().isBot()) return;
		System.out.println(e.getMessage().getContentRaw());
		
		Random r = new Random();
		
		if ( e.getMessage().getContentRaw().equalsIgnoreCase(Variables.getPrefix() + "dice"))
		{
			e.getChannel().sendMessage("The dice value was " + (r.nextInt(6) + 1)).queue();
		}
		
		if ( e.getMessage().getContentRaw().equalsIgnoreCase(Variables.getPrefix() + "coin"))
		{	
			if ( r.nextInt(2) == 0)
			{
				e.getChannel().sendMessage("The coin was heads").queue();
			}
			else
			{
				e.getChannel().sendMessage("The coin was tails").queue();
			}
			
		}
		
		if ( e.getMessage().getContentRaw().equalsIgnoreCase(Variables.getPrefix() + "topic"))
		{
			List<String> topicList = readFile("SwagDragonsTopicList.txt");
			int length = topicList.size();
			int j = new Random().nextInt(length);
			String topic = topicList.get(j);
			e.getChannel().sendMessage(topic).queue();
		}
	}
	
	/**
	 * Open and read a file, and return the lines in the file as a list
	 * of Strings.
	 * (Demonstrates Java FileReader, BufferedReader, and Java5.)
	 */
	private List<String> readFile(String fileName)
	{
	  List<String> topicList = new ArrayList<String>();
	  try
	  {
	    BufferedReader reader = new BufferedReader(new FileReader(fileName));
	    String line;
	    while ((line = reader.readLine()) != null)
	    {
	      topicList.add(line);
	    }
	    reader.close();
	    return topicList;
	  }
	  catch (Exception e)
	  {
	    System.err.format("Exception occurred trying to read '%s'.", fileName);
	    e.printStackTrace();
	    return null;
	  }
	}
	
}







	
	