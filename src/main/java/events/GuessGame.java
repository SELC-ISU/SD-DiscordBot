package events;


import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.Color;
import java.util.Random;
import SD.Discord.Bot.Variables;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.EmbedBuilder;




public class GuessGame extends ListenerAdapter {
	boolean isPlaying = false;
	String answer;
	EmbedBuilder country;
	String [] countries = {
			"https://tshop.r10s.com/6a5/ee0/bbe1/af9d/a045/b446/3e23/11bbe7afce2c600c737667.jpg",
			"https://cdn.britannica.com/s:500x350/80/4480-004-83C31275/flag-Stars-and-Stripes-July-4-1818.jpg",
			"https://upload.wikimedia.org/wikipedia/en/thumb/c/c3/Flag_of_France.svg/1200px-Flag_of_France.svg.png",
			"https://upload.wikimedia.org/wikipedia/en/thumb/a/ae/Flag_of_the_United_Kingdom.svg/1200px-Flag_of_the_United_Kingdom.svg.png",
			"https://cdn.britannica.com/82/2982-004-ABEB3852/flag-prototype-Netherlands-countries-European-flags.jpg"};
	String [] answers = {
			"canada",
			"united states",
			"france",
			"england",
			"holland"
	};
	
	
	public void onMessageReceived(MessageReceivedEvent x)
	{
		if ( x.getAuthor().isBot()) return;
		String message = x.getMessage().getContentRaw();
		MessageChannel c = x.getChannel();
		
		if (isPlaying && message.equalsIgnoreCase(answer)) {
			c.sendMessage("Correct! " + x.getAuthor().getAsMention()).queue();;
			isPlaying = false;
		}
		
		if (!message.startsWith(Variables.getPrefix())) return;
		
		message = message.substring(Variables.getPrefix().length());
		
		if (message.equalsIgnoreCase("guessthecountry")) {
			country = new EmbedBuilder();
			Random rand = new Random();
			int number = rand.nextInt(countries.length);
			answer = answers[number];
			country.setImage(countries[number]).setDescription("Which country corresponds witht this flag");
			country.setColor(Color.red);
			c.sendMessage(country.build()).queue();
			isPlaying = true;
		}
	}
}
