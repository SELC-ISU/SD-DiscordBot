package SD.Discord.Util;

import java.io.IOException;
import java.util.Random;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import SD.Discord.Bot.Variables;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Meme extends ListenerAdapter{
	
	Random r = new Random();
	
	public void onMessageReceived(MessageReceivedEvent e) {
		if (e.getAuthor().isBot()) return;
		if (!e.getMessage().getContentRaw().toLowerCase().startsWith(Variables.getPrefix() + "meme")) return;
		try {
			Document page = Jsoup.connect("https://www.reddit.com/r/dankmemes/").get();
			Elements allMemes = page.getElementsByAttributeValue("alt", "Post Image");
			System.out.println(allMemes.size());
			int i = r.nextInt(allMemes.size());
			Element meme = allMemes.get(i);
			String url = meme.attr("src");
			e.getChannel().sendMessage(url).queue();
			
		} catch (IOException e1) {
			e.getChannel().sendMessage("Something went wrong! I couldn't find any memes :(").queue();
			e1.printStackTrace();
		}
		
	}

}
