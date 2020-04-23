package SD.Discord.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class Poll{
	
	String body;
	List<String> reacts;
	HashMap<String, String> reactDefs;
	String lastReact;
	TextChannel tc;
	Message m;
	
	public Poll(TextChannel c) {
		body = "";
		reacts = new ArrayList<String>();
		reactDefs = new HashMap<>();
		lastReact = "";
		tc = c;
	}
	
	public void setBody(String body) {
		this.body = body;
	}
	
	public void addEmoteToReact(String string) {
		reacts.add(string);
		lastReact = string;
	}
	
	public void addDefinitionToLastReact(String string) {
		reactDefs.put(lastReact, string);
	}
	
	public String getReactDefinition(String react) {
		return reactDefs.get(react);
	}
	
	public Message toMessage() {
		m = new MessageBuilder(this.toString()).build();
		return m;
	}
	
	public TextChannel getTextChannel() {
		return tc;
	}
	
	public List<String> getReacts(){
		return reacts;
	}
	
	@Override
	public String toString() {
		String finalStr = body + "\n";
		for (String react : reactDefs.keySet()) {
			finalStr += react + " - " + reactDefs.get(react) + "\n";
		}
		return finalStr;
	}

}
