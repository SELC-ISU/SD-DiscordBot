package SD.Discord.Music;
import java.util.List;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;

public class PlayCommand implements ICommand 
{
	@Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) 
    {
		TextChannel channel = event.getChannel();
		AudioManager audioManager = event.getGuild().getAudioManager();
        PlayerManager manager = PlayerManager.getInstance();
        GuildVoiceState memberVoiceState = event.getMember().getVoiceState();
        VoiceChannel voiceChannel = memberVoiceState.getChannel();
        String input = String.join(" ", args);
        
        if (audioManager.isConnected())
        {
        	manager.loadAndPlay(event.getChannel(), input);
        	manager.getGuildMusicManager(event.getGuild()).player.setVolume(10);
        }
        else if (!memberVoiceState.inVoiceChannel()) 
        {
            channel.sendMessage("Please join a voice channel first").queue();
        }
        else if (audioManager.getConnectedChannel() != voiceChannel)
        {
        	audioManager.openAudioConnection(voiceChannel);
        	channel.sendMessage("Joining your voice channel").queue();
        }
        else
        {
            manager.loadAndPlay(event.getChannel(), input);
            manager.getGuildMusicManager(event.getGuild()).player.setVolume(10);
        }
    }

    @Override
    public String getHelp() 
    {
        return "Connect and play an audio";
    }

    @Override
    public String getInvoke() 
    {
        return "play";
    }
}
