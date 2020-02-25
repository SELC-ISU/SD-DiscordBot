package SD.Discord.Bot;

import java.util.List;

import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class TOSGame extends ListenerAdapter
{
	//(List<String> lobby) 
	private int numTown;
	private int numNeut;
	private int numMafia;
	
	public TOSGame()
	{
		//if ( )
	}
	
	public void charSelect(List<String> lobby) 
	{
		if (lobby.size() <= 10)
		{
			numTown = 3;
			numMafia = 2;
			numNeut = 2;
			
			while ( (numTown + numMafia + numNeut) < lobby.size())
			{
				if ( numTown < 5)
				{
					numTown++;
				}
				else
				{
					numNeut++;
				}
			}
		}
		else if (lobby.size() < 13 && lobby.size() > 10)
		{
			numTown = 5;
		}
			
		//dm players their roles(abilities,winning goal, attributes)
		
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
