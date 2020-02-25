package SD.Discord.Bot;

import javax.security.auth.login.LoginException;


import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import events.GuildMemberJoin;
import events.HelloEvent;

/*
 * --------------------------------
 * 			IMPORTANT INFO
 * --------------------------------
 * 
 * Test Account Login:
 * 		discordbot2779@gmail.com
 * 		Cyclones1
 * 
 * JDA Wiki:
 * 		https://github.com/DV8FromTheWorld/JDA/wiki
 * 
 * JDA Javadocs:
 * 		https://ci.dv8tion.net/job/JDA/javadoc/
 */
public class Main {
	
	public static void main(String[] args) {
		
		JDA jda = null;
		String token = "NjcwMDg0ODQ4NTA1MDYxMzc2.XkLPOw.OYkpAZJMG-AfWdNWfvEtNgpTvPY";
		
		
		try {
			jda = new JDABuilder(token).addEventListeners().build().awaitReady();
			
			
		
		} catch (LoginException e) {
			System.out.println("Could not login!");
		} catch (InterruptedException e) {
			System.out.println("Could not login!!");
		}
		
		jda.addEventListener(new HelloEvent());
		jda.addEventListener(new GuildMemberJoin());
	}

}
