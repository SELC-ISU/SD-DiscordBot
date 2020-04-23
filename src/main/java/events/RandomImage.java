package events;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import java.awt.Color;
import java.util.Random;

import SD.Discord.Bot.Variables;
import net.dv8tion.jda.api.EmbedBuilder;

public class RandomImage extends ListenerAdapter  {
	public void onMessageReceived(MessageReceivedEvent image)
	{
		String [] images = {
				"https://www.dumpaday.com/wp-content/uploads/2017/01/random-pictures-116.jpg",
				"https://theawesomedaily.com/wp-content/uploads/2017/01/random-pictures-that-make-no-sense-18-1.jpeg",
				"https://www.dumpaday.com/wp-content/uploads/2018/06/photos-1.jpg"
		};
		if(image.getMessage().getContentRaw().equalsIgnoreCase(Variables.getPrefix() + "RandomImage")){
			Random rand = new Random();
			int number = rand.nextInt(images.length);
			EmbedBuilder guess = new EmbedBuilder();
			guess.setImage(images[number]);
			guess.setColor(Color.red);
			image.getChannel().sendMessage(guess.build()).queue();
		}
	}
}
