package SD.Discord.Bot;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

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
		String token = "NjcwMDg0ODQ4NTA1MDYxMzc2.XjmY3g._LXfCZFfSnm7h2hLjoZ8AlqCtak";
		try {
			jda = new JDABuilder(token)
					.addEventListeners(new WordListener())
					.build().awaitReady();
		} catch (LoginException e) {
			System.out.println("Could not login!");
		} catch (InterruptedException e) {
			System.out.println("Could not login!!");
		}
		
	}

}
