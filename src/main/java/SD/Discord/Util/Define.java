package SD.Discord.Util;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import SD.Discord.Bot.Variables;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Define extends ListenerAdapter{
	
	public void onMessageReceived(MessageReceivedEvent e) {
		if (e.getAuthor().isBot()) return;
		if (!e.getMessage().getContentRaw().toLowerCase().startsWith(Variables.getPrefix() + "define ")) return;
		String word = e.getMessage().getContentRaw().substring(Variables.getPrefix().length() + 7).toLowerCase().split(" ")[0];
		String def = null;
		try {
			def = lookUp(word);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		e.getChannel().sendMessage("__The definition of *" + word + "* is__" + def).queue();
	}
	
	public String lookUp(String word) throws IOException {
		Document doc = Jsoup.connect("https://www.merriam-webster.com/dictionary/" + word).get();
		Element firstDef = doc.selectFirst(".dtText");
		return firstDef.text();
	}

}
