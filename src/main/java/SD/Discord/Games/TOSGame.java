package SD.Discord.Games;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.Timer;
import java.util.TimerTask;

import SD.Discord.Bot.Variables;
import SD.Discord.Games.TOSRoles.Role;
import SD.Discord.Games.TOSRoles.RoleController;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class TOSGame extends ListenerAdapter
{
	private boolean gameRunning;
	
	public void start()
	{
		gameRunning = true;
	}
	
	public void end()
	{
		gameRunning = false;
	}
	
	public TOSGame(List<User> lobby, TextChannel channel) throws InterruptedException
	{
		Runnable game = new Runnable() {

			@Override
			public void run() {
				
				start();
					
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
					int lobbySize = lobby.size();
					//Pre Night (Open to chatting)
					System.out.println("Graze Period!");
					channel.sendMessage("It is now Graze Period. You may now speak with the other players.").queue();
					try {
						TimeUnit.SECONDS.sleep(10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println("Night start!");
					channel.sendMessage("It is now Night Time. Please mute your microphones and go to your dm's for your night abilities").queue();
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
						pc.sendMessage("You have 20 seconds to perform your role.").queue();
						pc.sendMessage(r.getNightMessage(RoleController.getAliveRoles())).queue();
					}
					try {
						TimeUnit.SECONDS.sleep(20);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					RoleController.executeKillList();
					
					if(lobby.size() < lobbySize)
					{
						channel.sendMessage("The player list has been updated. Here are the remaining players: " + (RoleController.getAliveRoles()));
					}
					
					System.out.println("Day time!");
					RoleController.toggleNight();
					try {
						TimeUnit.SECONDS.sleep(30);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
						
					
					
							
				}
				channel.sendMessage("Game over!").queue();
			}
			
			
			
		};
		
		new Thread(game).start();
		
	}
	
}
