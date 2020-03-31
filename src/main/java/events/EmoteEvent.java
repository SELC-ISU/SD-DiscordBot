package events;

import java.awt.Color;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class EmoteEvent extends ListenerAdapter   {
	public void onMessageReceived(MessageReceivedEvent image)
	{
		EmbedBuilder emote;
		if(image.getMessage().getContentRaw().contains("!Creators")){
			
			emote = new EmbedBuilder();
			emote.setImage("https://media.tenor.com/images/81715b8fb5f186200c425573421353aa/tenor.gif").setDescription("We are the swagDragons").setColor(Color.BLACK);
			emote.setColor(Color.red);
			image.getChannel().sendMessage(emote.build()).queue();
			
		}
		else if(image.getMessage().getContentRaw().contains("!sad")) {
			emote = new EmbedBuilder();
			emote.setImage("https://media1.tenor.com/images/53e5f4f09f98a2b090ef3f588d53b2f7/tenor.gif?itemid=5586925").setDescription(image.getAuthor().getAsMention() + " is feeling sad \n Everyone cheer him up!").setColor(Color.BLACK);
			emote.setColor(Color.black);
			image.getChannel().sendMessage(emote.build()).queue();
		}
		else if(image.getMessage().getContentRaw().contains("!happy")) {
			emote = new EmbedBuilder();
			emote.setImage("https://media.tenor.com/images/c3ae4b5251f55ca6411225e69b74ba07/tenor.gif").setDescription(image.getAuthor().getAsMention() + " is feeling Happy").setColor(Color.BLACK);
			emote.setColor(Color.black);
			image.getChannel().sendMessage(emote.build()).queue();
		}
	}
}
