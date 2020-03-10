package SD.Discord.Music;

import java.util.List;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class PlayCommand implements ICommand 
{
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) 
    {
        PlayerManager manager = PlayerManager.getInstance();
        manager.loadAndPlay(event.getChannel(), "https://www.youtube.com/watch?v=thmCSki0Rb8");

        manager.getGuildMusicManager(event.getGuild()).player.setVolume(10);
    }

    @Override
    public String getHelp() 
    {
        return "Play an audio";
    }

    @Override
    public String getInvoke() 
    {
        return "play";
    }
}
