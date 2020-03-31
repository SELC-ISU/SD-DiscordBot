package SD.Discord.Bot;

import java.util.List;

import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class TOSGame extends ListenerAdapter
{
	//(List<String> lobby) 
	private int numTown;
	private int numNeut;
	private int numMafia;
	private int numPeople;
	
	public TOSGame()
	{
		//if ( )
	}
	
	public void charSelect(List<User> lobby) 
	{
		numPeople = lobby.size();
		
		 numNeut = lobby.size() / 3;
		 numMafia = lobby.size() / 4;
		 numTown = numPeople - (numNeut + numMafia);
		 
			
		//dm players their roles(abilities,winning goal, attributes)
		
		for ( int i = 0; i < lobby.size() ; i++)
		{
			PrivateChannel pc = lobby.get(i).openPrivateChannel().complete();
			
			if ( numNeut > 0)
			{
				pc.sendMessage("You are a neutral player !!").queue();
			}
			else if ( numMafia > 0)
			{
				pc.sendMessage("You are a member of the mafia !!").queue();
			}
			else
			{
				pc.sendMessage("You are a friendly citizen of the town !!").queue();
			}
			
			pc.close();
		}
		
		
		
	}
	
	public void dayTime()
	{
		//first day is short is 10 seconds, all other days are 1 minute 10 seconds
		//stopped if accused is run, set time to 15 after accused 
		//day abilities
	}
	
	public void accused()
	{
		//only accused can speak for 20 seconds
		//rest of lobby has 20 seconds after to vote to kill or keep
		//set time to 15 if player is kept
		//print role if killed, set time to 15 seconds
	}
	
	public void nightTime()
	{
		//night abilities
		//only mafia can talk to eachother(no chat for others)
	}
	
	
	
	
	
}
