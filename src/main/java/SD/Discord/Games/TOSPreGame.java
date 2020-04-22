package SD.Discord.Games;

import java.util.ArrayList;
import java.util.List;

import SD.Discord.Bot.Variables;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class TOSPreGame extends ListenerAdapter{

	//Create lists for players and messages sent
	List<User> playerList = new ArrayList<User>();
	List<String> messageIds = new ArrayList<String>();
	
	@SuppressWarnings("unused")
	public void onMessageReceived(MessageReceivedEvent e)
	{

		//Initialize player
		String player = "";
		String playersInGame = "";
		
		//Boolean gameActive;
		
		//add all messages sent to message id list
		messageIds.add(e.getMessageId());
		
		//if bot sends a message, ignore
		if ( e.getAuthor().isBot()) return;
		
		if (!Variables.getMinigameChannel().equals("*")) {
			if (!e.getChannel().getName().equals(Variables.getMinigameChannel())) return;
		}
		
		/**
		 * !join adds player to player list
		 * makes the user that entered !join the new player
		 * prints out message confirming
		 */

		if ( e.getMessage().getContentRaw().equalsIgnoreCase(Variables.getPrefix() + "join") )

		{
			player = e.getAuthor().getName();

			playersInGame += player  + ", " ;
			
			if ( playerList.contains(e.getAuthor()) == true)
			{
				e.getChannel().sendMessage(player + " has already joined the game").queue();
			}
			else
			{
				e.getChannel().sendMessage(player + " has joined the game!").queue();
			
				playerList.add(e.getAuthor());

				List<String> names = new ArrayList<String>();
				for (User u : playerList) {
					names.add(u.getName());
				}
				e.getChannel().sendMessage("Players on the list: " + names).queue();

			}
		}
		
		/**
		 * !start begins the game
		 * must have between 7 and 15 players
		 */
		if (e.getMessage().getContentRaw().equalsIgnoreCase(Variables.getPrefix() + "start"))
		{
			player = e.getAuthor().getName();
			if (playerList.contains(e.getAuthor()) == true)
			{

				/*if (playerList.size() < 2)
=======
				if (playerList.size() < 1)
>>>>>>> b20fdf692f3a3d99f2007f8a89730753b1a467f2:src/main/java/SD/Discord/Games/TOSPreGame.java
				{
					e.getChannel().sendMessage("The minimum amount of players is 1").queue();
				}
				else */if (playerList.size() > 15)
				{
					e.getChannel().sendMessage("The maximum amount of players is 15").queue();
				}
				else
				{
					//gameActive = true;
					e.getChannel().sendMessage("The game has now started! You will now recieve a DM of your role").queue();
				
					try {
						TOSGame game = new TOSGame(playerList, e.getTextChannel());
					} catch (InterruptedException e1) {
						e.getChannel().sendMessage("Something went hella wrong");
					}
				
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
		if (e.getMessage().getContentRaw().equalsIgnoreCase(Variables.getPrefix() + "leave"))
		{
			player = e.getAuthor().getName();

			if ( playerList.contains(e.getAuthor()) == false)
			{
				e.getChannel().sendMessage(player + " is not in the game").queue();
			}
			else
			{
				e.getChannel().sendMessage(player + " was removed from the game").queue();
			
				playerList.remove(e.getAuthor());

				List<String> names = new ArrayList<String>();
				for (User u : playerList) {
					names.add(u.getName());
				}
				e.getChannel().sendMessage("Players on the list: " + names).queue();


			}
		}
		/**
		 * !clear erases all messages sent while the bot is active
		 */
		if (e.getMessage().getContentRaw().equalsIgnoreCase(Variables.getPrefix() + "clear"))
		{
			for ( String messageLog : messageIds )
			{
				e.getChannel().deleteMessageById(messageLog).submit();
			}
		}
		
		if ( e.getMessage().getContentRaw().contains(Variables.getPrefix() + "accused"))
		{
			player = e.getAuthor().getName();
		}
	
		
	}
}
