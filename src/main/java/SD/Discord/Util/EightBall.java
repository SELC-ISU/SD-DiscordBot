package SD.Discord.Util;

import java.util.Random;

import SD.Discord.Bot.Variables;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class EightBall extends ListenerAdapter{
	
	public void onMessageReceived(MessageReceivedEvent e) {
		if (e.getAuthor().isBot()) return;
		if (!e.getMessage().getContentRaw().toLowerCase().startsWith(Variables.getPrefix() + "8ball ")) return;
		String question = e.getMessage().getContentRaw().substring(Variables.getPrefix().length() + 6).toLowerCase();
		if (question.startsWith("is") || question.startsWith("am") || question.startsWith("will") || question.startsWith("are") || question.startsWith("should")) {
			String[] answers = {
								"It is certain.",
								"It is decidedly so.",
								"Without a doubt.",
								"Yes - definitely.",
								"You may rely on it.",
								"As I see it, yes.",
								"Most likely.",
								"Outlook good.",
								"Yes.",
								"Signs point to yes.",
								"Reply hazy, try again.",
								"Ask again later.",
								"Better not tell you now.",
								"Cannot predict now.",
								"Concentrate and ask again.",
								"Don't count on it.",
								"My reply is no.",
								"My sources say no.",
								"Outlook not so good.",
								"Very doubtful."
								};
			int num = new Random().nextInt(answers.length);
			e.getChannel().sendMessage(answers[num]).queue();
		}
		else {
			e.getChannel().sendMessage("Please ask me a yes or no question.").queue();
		}
	}
}
