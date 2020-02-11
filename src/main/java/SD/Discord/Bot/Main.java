package SD.Discord.Bot;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.security.auth.login.LoginException;

import com.esotericsoftware.yamlbeans.YamlReader;
import com.esotericsoftware.yamlbeans.YamlWriter;

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

/*
 * 		GRADLE DEPENDENCIES
 * 
 * 		Discord
 *  	compile 'net.dv8tion:JDA:4.1.0_100'
 *  
 *  	YamlBeans
 *  	compile "com.esotericsoftware.yamlbeans:yamlbeans:1.06"
 */

/*
 * 
 * Music bot
 * Backend UI
 * Customizability
 * Post pictures
 * Minigames:
 * 		- TOS
 * 		- Dice Roller
 * 		- Coin flipper
 * 		- Wheel decider
 * Currency (MAYBE)
 * Miscellaneous small stuff
 * 
 */

/*
 *ROLES:
 *	Ed - Music Bot
 *  Felipe - Image grabber thingy
 *  Nate - File system stuff
 *  Dylan - Minigames
 *  
 */
public class Main {
	
	public static void main(String[] args) throws IOException {
		
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
		
		
		/*
		 * Start up the config!!
		 */
		File myObj = null;
		boolean newConfig = false;
		try {
		      myObj = new File("config.yml");
		      if (myObj.createNewFile()) {
		        System.out.println("File created: " + myObj.getName());
		        newConfig = true;
		      } else {
		        System.out.println("File already exists.");
		      }
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		  }
	
		ConfigControl configControlInit = new ConfigControl(new YamlReader(new FileReader("config.yml")), null);
		List<Object> r = configControlInit.getConfigObjects();
		List<Object> configInit = new ArrayList<Object>();
		YamlWriter yamlW = new YamlWriter(new FileWriter("config.yml"));
		for (Object obj : r) {
			yamlW.write(obj);
			configInit.add(r);
		}
		
		ConfigControl configControl = new ConfigControl(new YamlReader(new FileReader("config.yml")), yamlW);
		
		if (newConfig) {
			YamlWriter writer = new YamlWriter(new FileWriter("config.yml"));
			Map<String, String> map = new HashMap<String, String>();
			map.put("prefix", "!");
			configControl.setWriter(writer);
		}
		//configControl.addValue(configInit, "fort", "nite");
		
		configControl.getWriter().close();
		
		List<Object> configObj = configControl.getConfigObjects();
		//System.out.println(configObj);
		//System.out.println(configControl.getValue(configObj, "prefix"));
		
		/*
		 * END of the config!!
		 */
	}

}
