package SD.Discord.Bot;

import java.util.ArrayList;
import java.util.List;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class TOSPreGame extends ListenerAdapter{

	//Create lists for players and messages sent
	List<String> playerList = new ArrayList<String>();
	List<String> messageIds = new ArrayList<String>();
	
	public void onMessageReceived(MessageReceivedEvent e)
	{
		//Initialize player
		String player = "";
		
		//add all messages sent to message id list
		messageIds.add(e.getMessageId());
		
		//if bot sends a message, ignore
		if ( e.getAuthor().isBot()) return;
		
		//process all messages 
		System.out.println(e.getMessage().getContentRaw());
		
		/**
		 * !join adds player to player list
		 * makes the user that entered !join the new player
		 * prints out message confirming
		 */
		if ( e.getMessage().getContentRaw().contains(Variables.getPrefix() + "join") )
		{
			player = e.getAuthor().getName();
			if ( playerList.contains(player) == true)
			{
				e.getChannel().sendMessage(player + " has already joined the game").queue();
			}
			else
			{
				e.getChannel().sendMessage(player + " has joined the game!").queue();
			
				playerList.add(e.getAuthor().getName());
				e.getChannel().sendMessage("Players on the list: " + playerList.toString()).queue();
			}
		}
		
		/**
		 * !start begins the game
		 * must have between 7 and 15 players
		 */
		if (e.getMessage().getContentRaw().contains(Variables.getPrefix() + "start"))
		{
			player = e.getAuthor().getName();
			if (playerList.contains(player) == true)
			{
				if (playerList.size() < 2)
				{
					e.getChannel().sendMessage("The minimum amount of players is 2").queue();
				}
				else if (playerList.size() > 15)
				{
					e.getChannel().sendMessage("The maximum amount of players is 15").queue();
				}
			
				else
				{
					e.getChannel().sendMessage("The game has now started! You will now recieve a DM of your role").queue();
					//Call a seperate function that listens to commands (quit, accuse *playerName*, vote, abilities)
					e.getPrivateChannel().sendFile(file, options);
				}
			}
			else
			{
				e.getChannel().sendMessage("You cannot start the game if you are not playing").queue();
			}
		}
		
		/**
		 * !leave takes you out of the player list
		 * does not take you out if you were not apart of the list
		 */
		if (e.getMessage().getContentRaw().contains(Variables.getPrefix() + "leave"))
		{
			player = e.getAuthor().getName();
			
			if ( playerList.contains(player) == false)
			{
				e.getChannel().sendMessage(player + " is not in the game").queue();
			}
			else
			{
				e.getChannel().sendMessage(player + " was removed from the game").queue();
			
				playerList.remove(e.getAuthor().getName());
				e.getChannel().sendMessage("Players on the list: " + playerList.toString()).queue();

			}
		}
		/**
		 * !clear erases all messages sent while the bot is active
		 */
		if (e.getMessage().getContentRaw().contains(Variables.getPrefix() + "clear"))
		{
			for ( String messageLog : messageIds )
			{
				e.getChannel().deleteMessageById(messageLog).queue();
			}
		}
		
		
	}
	
	
	
}