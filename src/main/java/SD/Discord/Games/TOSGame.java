package SD.Discord.Games;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import SD.Discord.Games.TOSRoles.Role;
import SD.Discord.Games.TOSRoles.RoleController;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class TOSGame extends ListenerAdapter
{
	//(List<String> lobby) 
	private int numTown;
	private int numNeut;
	private int numMafia;
	private boolean gameRunning;
	
	public TOSGame() throws InterruptedException
	{
		Runnable game = new Runnable() {

			@Override
			public void run() {
				gameRunning = true;
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
					
				List<Role> assignedRoles = new ArrayList<Role>();
				List<Role> possibleRoles = RoleController.allPossibleRoles();
				List<Role> alivePlayers = new ArrayList<Role>();
				HashMap<User, Role> roleMap = new HashMap<User, Role>();
				for (int i = 0; i < lobby.size(); i++) {
					User u = lobby.get(i);
					roleMap.put(u, possibleRoles.get(i));
					assignedRoles.add(possibleRoles.get(i));
					alivePlayers.add(possibleRoles.get(i));
					PrivateChannel pc = u.openPrivateChannel().complete();
					pc.sendMessage("You are the " + possibleRoles.get(i).getRoleName() + ".").queue();
					pc.sendMessage("**Role Description:** " + possibleRoles.get(i).getDescription()).queue();
				}
				
				new RoleController(assignedRoles, alivePlayers, roleMap, channel);
				
				while(gameRunning) {
					//Pre Night (Open to chatting)
					System.out.println("Graze Period!");
					try {
						TimeUnit.SECONDS.sleep(10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println("Night start!");
					RoleController.toggleNight();
					//Night time
					for (Role r : RoleController.getAliveRoles()) {
						List<Member> memList = channel.getMembers();
						User u = null;
						for (Member mem : memList) {
							if (mem.getUser().getName().equals(r.getPlayerName())) {
								u = mem.getUser();
								break;
							}
						}
						if (u == null) continue;
						PrivateChannel pc = u.openPrivateChannel().complete();
						pc.sendMessage("You have 30 seconds to perform your role.").queue();
						pc.sendMessage(r.getNightMessage(RoleController.getAliveRoles())).queue();
					}
					try {
						TimeUnit.SECONDS.sleep(30);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					RoleController.executeKillList();
					
					System.out.println("Day time!");
					RoleController.toggleNight();
					//TimeUnit.SECONDS.sleep(30);
					
				}
			}
			
		};
		
		new Thread(game).start();
		
	}
	
	public void charSelect(List<String> lobby) 
	{
		/*
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
			
		List<Role> assignedRoles = new ArrayList<Role>();
		// assign Roles
		
		List<Role> alivePlayers = new ArrayList<Role>();
		
		rc = new RoleController(assignedRoles, alivePlayers, )
		
		//dm players their roles(abilities,winning goal, attributes)
		*/
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
