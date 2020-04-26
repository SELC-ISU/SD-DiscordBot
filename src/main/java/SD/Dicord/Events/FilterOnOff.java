package SD.Dicord.Events;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import SD.Discord.Bot.Variables;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class FilterOnOff extends ListenerAdapter{
	
	public void onMessageReceived(MessageReceivedEvent OnOff) {
		
		if(OnOff.getMessage().getContentRaw().equalsIgnoreCase(Variables.getPrefix() + "togglefilter") && Variables.isProfanityEnabled()) {
			OnOff.getChannel().sendMessage("The curse filter has been disables by: " + OnOff.getAuthor().getAsMention()).queue();
			Variables.setProfanityEnabled("false");;
		}
		else if(OnOff.getMessage().getContentRaw().equalsIgnoreCase(Variables.getPrefix() + "togglefilter") && !Variables.isProfanityEnabled()) {
			OnOff.getChannel().sendMessage("The curse filter has been enable by: " + OnOff.getAuthor().getAsMention()).queue();
			Variables.setProfanityEnabled("true");;
		}
	}
}
