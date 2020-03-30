package SD.Discord.Bot;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.security.auth.login.LoginException;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.esotericsoftware.yamlbeans.YamlReader;
import com.esotericsoftware.yamlbeans.YamlWriter;

import SD.Discord.Games.Clapify;
import SD.Discord.Games.EightBall;
import SD.Discord.Games.GuessTheNumber;
import SD.Discord.Games.RandomGames;
import SD.Discord.Games.TOSPreGame;
import SD.Discord.Games.TOSRoles.ResponseListener;
import SD.Discord.Music.MusicMain;
import events.EmoteEvent;
import events.GuildMemberJoin;
import events.ImageEvent;
import events.RandomImage;
import net.dv8tion.jda.api.AccountType;
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
 *  
 *  	Music Bot
 *  	implementation 'com.sedmelluq:lavaplayer:1.3.34'
 *  
 *  	GRADLE PLUGINS
 *  	id 'java'
 *		id 'com.github.johnrengelman.shadow' version '5.2.0'
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
 *  Nate - File system stuff and UI
 *  Dylan - Minigames
 *  
 */
public class Main {
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException {
		
		/*
		 * Start up the config!!
		 */
		File myObj = null;
		boolean newConfig = false;
		
		// Try to create a new file if one doesn't exist yet
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
		
		// Start up the writer without resetting the whole config
		ConfigControl configControlInit = new ConfigControl(new YamlReader(new FileReader("config.yml")), null);
		List<Object> r = configControlInit.getConfigObjects();
		List<Object> configInit = new ArrayList<Object>();
		YamlWriter yamlW = new YamlWriter(new FileWriter("config.yml"));
		for (Object obj : r) {
			yamlW.write(obj);
			configInit.add(r);
		}
		
		// Start up a new config control instance
		ConfigControl configControl = new ConfigControl(new YamlReader(new FileReader("config.yml")), yamlW);
		
		// If its a new config, fill it with default values
		if (newConfig) {
			YamlWriter writer = new YamlWriter(new FileWriter("config.yml"));
			Map<String, String> map = new HashMap<String, String>();
			map.put("token", null);
			map.put("prefix", "!");
			map.put("ping", "pong");
			map.put("custom", "");
			map.put("anotherexample", "Create new lines by typing \"&n;\"!");
			map.put("deletethis", "To delete a command just erase the command name.");
			map.put("mention", "If you are going to mention, use the '_' to represent a space in the name.");
			map.put("dm", "##DM## Start a command response like so for it to be DM'ed to the author.");
			map.put("music-channel", "*");
			map.put("mg-channel", "*");
			writer.write(map);
			configControl.setWriter(writer);
		}
		
		// Close the writer
		configControl.getWriter().close();
		
		/*
		 * UI
		 */
	
		// Initialize some variables that will be useful
		List<Object> configObj = configControl.getConfigObjects();
		String prefixChars = "";
		String botToken = "";
		String music = "";
		String tos = "";
		HashMap<String, Object> commandList = new HashMap<String, Object>();
		List<JPanel> commandEntries = new ArrayList<JPanel>();

		// Fill the UI fields with config values
		for (Object o : configObj) {
			if (o instanceof HashMap<?, ?>) {
				HashMap<String, Object> map = (HashMap<String, Object>) o;
				for (String key : map.keySet()) {
					if (key.equalsIgnoreCase("commands02779")) continue;
					if (key.equalsIgnoreCase("prefix")) {
						prefixChars = (String) map.get(key);
					}
					else if (key.equalsIgnoreCase("token")) {
						botToken = (String) map.get(key);
					}
					else if (key.equalsIgnoreCase("music-channel")) {
						music = (String) map.get(key);
					}
					else if (key.equalsIgnoreCase("mg-channel")) {
						tos = (String) map.get(key);
					}
					else {
						commandList.put(key, map.get(key));
					}
				}
			}
		}
		
		// Open the window
		JFrame frame = ConfigUI.openFirstWindow();
		Container content = frame.getContentPane();
		content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
		
		//Header
		JPanel header = ConfigUI.addHeader(frame);
		content.add(header, BorderLayout.NORTH);
		
		
		//Token Entry
		JPanel tokenPanel = ConfigUI.addTokenField(frame, botToken);
		content.add(tokenPanel, BorderLayout.SOUTH);
		
		
		//Prefix Entry
		JPanel prefixPanel = ConfigUI.addPrefixField(frame, prefixChars);
		content.add(prefixPanel, BorderLayout.SOUTH);
		
		//Music Entry
		JPanel musicPanel = ConfigUI.addMusicField(frame, music);
		content.add(musicPanel, BorderLayout.SOUTH);
				
		//Minigame Entry
		JPanel tosPanel = ConfigUI.addTOSField(frame, tos);
		content.add(tosPanel, BorderLayout.SOUTH);
		
		//Custom Commands
		for (String key : commandList.keySet()) {
			JPanel newEntry = ConfigUI.customCommandEntry(key, commandList.get(key).toString());
			content.add(newEntry);
			commandEntries.add(newEntry);
		}
		
		JPanel submitCancel = new JPanel();
		
		//Add Custom Command Addition Button
		content.add(ConfigUI.addAddButton(frame, content, submitCancel, commandEntries));
		
		//Submit Button
		MouseAdapter submitEvent = new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) { //This is what happens when the fields are submitted
				try {
					super.mouseClicked(e);
					YamlWriter yamlW = new YamlWriter(new FileWriter("config.yml"));
					HashMap<String, Object> newMap = new HashMap<String, Object>();
					List<String> commands = new ArrayList<String>();
					String newToken = ConfigUI.getTextFromField(tokenPanel);
					newMap.put("token", newToken);
					String newPrefix = ConfigUI.getTextFromField(prefixPanel);
					newMap.put("prefix", newPrefix);
					String newMusic = ConfigUI.getTextFromField(musicPanel);
					newMap.put("music-channel", newMusic);
					String newTOS = ConfigUI.getTextFromField(tosPanel);
					newMap.put("mg-channel", newTOS);
					for (JPanel custom : commandEntries) {
						String key = null;
						String value = null;
						Component[] comps = custom.getComponents();
						for (Component c : comps) {
							boolean cont = true;
							if (c instanceof JTextField) {
								String text = ((JTextField) c).getText();
								if (text.equals("")) {
									cont = false;
									break;
								}
								key = ((JTextField) c).getText();
								continue;
							}
							if (c instanceof JScrollPane && cont) {
								value = ConfigUI.getTextFromArea((JScrollPane) c);
								newMap.put(key, value);
								commands.add(key);
							}
						}
					}
					newMap.put("commands02779", commands);
					yamlW.write(newMap);
					yamlW.close();
					Variables.setPrefix(newPrefix);
					Variables.setMusicChannel(newMusic);
					Variables.setMinigameChannel(newTOS);
					
					frame.dispose();
					
					JFrame startingFrame = ConfigUI.openStartingFrame();
					startingFrame.pack();

					new Thread(new Runnable() {

                        @Override
                        public void run() {
                            try {
                                Thread.sleep(2100);
                            } catch (InterruptedException ex) {
                            }
                            SwingUtilities.invokeLater(new Runnable() {

                                @Override
                                public void run() {
                                	boolean ableToLogin = runBot(newToken);
        							
        							startingFrame.dispose();
        							
        							JFrame newFrame = ConfigUI.openRunningFrame(ableToLogin);
        							newFrame.pack();
                                }
                            });
                        }
                    }).start();
					
				}
				catch (IOException ex) {
					ex.printStackTrace();
				}
			}
			
		};
		content.add(ConfigUI.addSubmitAndCancel(frame, submitCancel, commandEntries, commandList, submitEvent), BorderLayout.SOUTH);
		
		//Scrollbar
		JScrollPane scroll = new JScrollPane(content, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		frame.setContentPane(scroll);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.pack();
		/*
		 * END of the config!!
		 */
	}
	
	@SuppressWarnings("unused")
	public static synchronized boolean runBot(String token) {
		JDA jda = null;
		
        Logger logger = LoggerFactory.getLogger(Main.class);
		
		try {
			jda = new JDABuilder(AccountType.BOT)
					.setToken(token)
					.addEventListeners(new CustomCommandListener(),
							new RandomGames(),
							new EightBall(),
							new RandomGames(), 
							new GuessTheNumber(),
							new Clapify(),
							new TOSPreGame(),
							new ResponseListener(),
							new GuildMemberJoin(),
							new ImageEvent(),
							new RandomImage(),
							new EmoteEvent(),
							new MusicMain())
					.build().awaitReady();
			return true;
		} catch (LoginException ex) {
			System.out.println("Could not login!");
			return false;
		} catch (InterruptedException ex) {
			System.out.println("Could not login!!");
			return false;
		}
	}
	
}