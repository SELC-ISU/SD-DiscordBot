package SD.Discord.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import SD.Discord.Bot.Variables;
import net.dv8tion.jda.api.entities.Activity.Emoji;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageReaction;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class PollListener extends ListenerAdapter{

	HashMap<User, Poll> poll = new HashMap<User, Poll>();
	HashMap<User, Integer> stage = new HashMap<User, Integer>();
	HashMap<User, Message> userPoll = new HashMap<User, Message>();
	
	public void onMessageReceived(MessageReceivedEvent e) {
		if (e.getAuthor().isBot()) return;
		if (e.getMessage().getContentRaw().toLowerCase().startsWith(Variables.getPrefix() + "poll"))  {
		PrivateChannel pc = e.getAuthor().openPrivateChannel().complete();
		if (!(e.getChannel() instanceof PrivateChannel)) {
			if (poll.containsKey(e.getAuthor())) {
				if (stage.get(e.getAuthor()) == 2) {
					pc.sendMessage("Error > Please finish your poll.").queue();
					return;
				}
				Message m = e.getTextChannel().retrieveMessageById(userPoll.get(e.getAuthor()).getId()).complete();
				List<MessageReaction> results = m.getReactions();
				List<MessageReaction> winner = new ArrayList<>();
				winner.add(results.get(0));
				for (MessageReaction r : results) {
					if (r.equals(results.get(0))) continue;
					if (r.getCount() == winner.get(0).getCount()) {
						winner.add(r);
						continue;
					}
					else if (r.getCount() > winner.get(0).getCount()) {
						winner.clear();
						winner.add(r);
						continue;
					}
				}
				if (winner.size() > 1) {
					String finalStr = "Results > It's a tie\n";
					for (MessageReaction r : winner) {
						finalStr += poll.get(e.getAuthor()).getReactDefinition(r.getReactionEmote().getEmoji()) + " (" + r.getReactionEmote().getEmoji() + ") " + " had " + results.get(results.indexOf(r)).getCount() + " votes.";
					}
					e.getChannel().sendMessage(finalStr).queue();
					poll.remove(e.getAuthor());
					stage.remove(e.getAuthor());
					userPoll.remove(e.getAuthor());
				}
				else {
					String msg = "Results > Winner! " + poll.get(e.getAuthor()).getReactDefinition(winner.get(0).getReactionEmote().getEmoji()) + " (" + winner.get(0).getReactionEmote().getEmoji() + ") " + " had " + winner.get(0).getCount() + " votes.";
					e.getChannel().sendMessage(msg).queue();
					poll.remove(e.getAuthor());
					stage.remove(e.getAuthor());
					userPoll.remove(e.getAuthor());
				}
			}
			else {
			pc.sendMessage("Poll Creator > Please send me the following the following information.\n"
					+ "First, the body text of the poll.\n"
					+ "Then, any reaction emoji, press enter, then title the reaction with what that vote is for.\n"
					+ "Type done, when you're done.").queue();
			poll.put(e.getAuthor(), new Poll(e.getTextChannel()));
			stage.put(e.getAuthor(), 0);
			}
		}
		}
		if (e.getChannel() instanceof PrivateChannel) {
			if (poll.containsKey(e.getAuthor())) {
				int stageNum = stage.get(e.getAuthor());
				if (stageNum == 0) {
					String body = e.getMessage().getContentRaw();
					poll.get(e.getAuthor()).setBody(body);
					stage.put(e.getAuthor(), 1);
				}
				else if (stageNum == 1) {
					if (e.getMessage().getContentRaw().equalsIgnoreCase("done")) {
						Message m = poll.get(e.getAuthor()).toMessage();
						Message complete = poll.get(e.getAuthor()).getTextChannel().sendMessage(m).complete();
						for (String em : poll.get(e.getAuthor()).getReacts()) {
							complete.addReaction(em).queue();
						}
						userPoll.put(e.getAuthor(), complete);
					}
					else {
					boolean emoji = new Emoji(e.getMessage().getContentRaw()).isEmoji();
					if (e.getMessage().getContentRaw().matches("^[a-zA-Z0-9]*$")) emoji = false;
					for (char c : e.getMessage().getContentRaw().toCharArray()) {
						if (Character.UnicodeBlock.of(c) == Character.UnicodeBlock.BASIC_LATIN) {
							 emoji = false;
						}
					}
					System.out.println(emoji);
					if (!emoji) {
						e.getChannel().sendMessage("Error > You must enter an emoji.").queue();
						return;
					}
					poll.get(e.getAuthor()).addEmoteToReact(e.getMessage().getContentDisplay());
					stage.put(e.getAuthor(), 2);
					}
				}
				else if (stageNum == 2) {
					if (e.getMessage().getContentRaw().equalsIgnoreCase("done")) {
						e.getChannel().sendMessage("Error > You can't be done yet. Add a reaction description.").queue();
					}
					else {
					poll.get(e.getAuthor()).addDefinitionToLastReact(e.getMessage().getContentDisplay());
					stage.put(e.getAuthor(), 1);
					}
				}
			}
		}
	}
	
}
