package SD.Discord.Music;

import java.lang.reflect.Member;
import java.util.List;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.IPermissionHolder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;

public class JoinCommand implements ICommand 
{
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) 
    {
    	 TextChannel channel = event.getChannel();
         AudioManager audioManager = event.getGuild().getAudioManager();
         if (audioManager.isConnected()) 
         {
             channel.sendMessage("I'm already connected to a channel").queue();
             return;
         }
         
         GuildVoiceState memberVoiceState = event.getMember().getVoiceState();
         if (!memberVoiceState.inVoiceChannel()) 
         {
             channel.sendMessage("Please join a voice channel first").queue();
             return;
         }

         VoiceChannel voiceChannel = memberVoiceState.getChannel();
         Member selfMember = (Member)event.getGuild().getSelfMember();
         if (!((IPermissionHolder) selfMember).hasPermission(voiceChannel, Permission.VOICE_CONNECT)) 
         {
             channel.sendMessageFormat("I am missing permission to join %s", voiceChannel).queue();
             return;
         }

         audioManager.openAudioConnection(voiceChannel);
         channel.sendMessage("Joining your voice channel").queue();         
    }

    @Override
    public String getHelp() 
    {
        return "Makes the bot join your channel";
    }

    @Override
    public String getInvoke() 
    {
        return "connect";
    }
}
