package SD.Discord.Bot;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

/*
 * Test Account Login
 * discordbot2779@gmail.com
 * Cyclones1
 */
public class Main {
	
	public static void main(String[] args) {
		
		JDA jda = null;
		try {
			jda = new JDABuilder("NjcwMDg0ODQ4NTA1MDYxMzc2.XipQuA.ksC9Qv4cXN1N6p7bBEULmY3bcMc")
					.addEventListeners()
					.build().awaitReady();
		} catch (LoginException e) {
			System.out.println("Could not login!");
		} catch (InterruptedException e) {
			System.out.println("Could not login!!");
		}
		
	}

}
