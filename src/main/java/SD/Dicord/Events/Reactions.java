package SD.Dicord.Events;
import SD.Discord.Bot.Variables;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
public class Reactions extends ListenerAdapter {
	public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
		String [] args = e.getMessage().getContentRaw().split("\\s+");
		int n = Integer.parseInt(args[2]);
		String r = "";
		String heart = "💖";
		if(args[0].equalsIgnoreCase( Variables.getPrefix() +"heart")) {
			if(args[1].equalsIgnoreCase("stair")) {
				for(int i=0; i<n; i++) 
		        { 
		           
		            for(int j=0; j<=i; j++) 
		            { 
		                // printing num with a space  
		                r+= heart + " "; 
		  
		                //incrementing value of num 
		            
		            } 
		           r+= "\n";
		        } 
			}
			else if(args[1].equalsIgnoreCase(Variables.getPrefix() + "square")) {
				int m = n;
				for (int i = 1; i <= n; i++) 
		        { 
		            for (int j = 1; j <= m; j++) 
		            { 
		                if (i == 1 || i == n ||  
		                    j == 1 || j == m)             
		                    r +="🤍";             
		                else
		                    r+= "✔";             
		            } 
		            r += "\n";
		        } 
			}
		}
		
    
		e.getChannel().sendMessage(r).queue();
	}
}
