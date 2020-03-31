package events;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import java.time.*; 





	public class ClockEvent extends ListenerAdapter 
	{
		public void onMessageReceived(MessageReceivedEvent event)
		{
			
			if(event.getMessage().getContentRaw().contains("!time")) {
				 // create a Zone Id for Europe/Paris 
		        ZoneId zoneId = ZoneId.of("US/Central"); 
		  
		        // create Clock with system(zoneId) method 
		        Clock clock = Clock.system(zoneId); 
		  
		        // get instant of class 
		        Instant instant = clock.instant(); 
		  
		        // get ZonedDateTime object from instantObj to get date time 
		        ZonedDateTime time = instant.atZone(clock.getZone()); 
		        event.getChannel().sendMessage("Hello " + event.getMember().getAsMention() + "\n Clock: " + time.toString()).queue();
			}
			
		}
	}
		


