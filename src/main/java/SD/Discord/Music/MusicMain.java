package SD.Discord.Music;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import SD.Discord.Bot.Variables;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;

public class MusicMain extends ListenerAdapter {

  private final AudioPlayerManager playerManager;
  private final Map<Long, GuildMusicManager> musicManagers;
  private static Member sender;

  public MusicMain() {
    this.musicManagers = new HashMap<>();

    this.playerManager = new DefaultAudioPlayerManager();
    AudioSourceManagers.registerRemoteSources(playerManager);
    AudioSourceManagers.registerLocalSource(playerManager);
  }

  private synchronized GuildMusicManager getGuildAudioPlayer(Guild guild) {
    long guildId = Long.parseLong(guild.getId());
    GuildMusicManager musicManager = musicManagers.get(guildId);

    if (musicManager == null) {
      musicManager = new GuildMusicManager(playerManager);
      musicManagers.put(guildId, musicManager);
    }

    guild.getAudioManager().setSendingHandler(musicManager.getSendHandler());

    return musicManager;
  }

  @Override
  public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
	  String requiredChannelName = Variables.getMusicChannel();
	  if (!requiredChannelName.equals("*") && !event.getChannel().getName().equals(requiredChannelName)) return;
	  
    String[] command = event.getMessage().getContentRaw().split(" ");
    
    if ((Variables.getPrefix() + "play").equalsIgnoreCase(command[0]) && command.length > 0) {
    	sender = event.getMember();
    	if (isValidURL(command[1])) {
    		loadAndPlay(event.getChannel(), command[1]);
    	}
    	else {
    		String search = "";
    		for (int i = 1; i < command.length; i++) {
    			search += command[i] + " ";
    		}
    		System.out.println("Attemption to play " + search);
    		try {
				String url = new MusicSearch(search).getURL();
				loadAndPlay(event.getChannel(), url);
			} catch (IOException e) {
				System.out.println("Could not find url");
				e.printStackTrace();
			}
    	}
    } else if ((Variables.getPrefix() + "skip").equalsIgnoreCase(command[0])) {
    	skipTrack(event.getChannel());
    } else if ((Variables.getPrefix() + "stop").equalsIgnoreCase(command[0])) {
        event.getGuild().getAudioManager().closeAudioConnection();
        
      }
    

    super.onGuildMessageReceived(event);
  }
  
  @SuppressWarnings("unused")
  private boolean isValidURL(String urlStr) {
	  try {
	      URL url = new URL(urlStr);
	      return true;
	    }
	    catch (MalformedURLException e) {
	        return false;
	    }
  }

  private void loadAndPlay(final TextChannel channel, final String trackUrl) {
    GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());

    playerManager.loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler() {
      @Override
      public void trackLoaded(AudioTrack track) {
        channel.sendMessage("Adding to queue " + track.getInfo().title).queue();

        play(channel.getGuild(), musicManager, track);
      }

      @Override
      public void playlistLoaded(AudioPlaylist playlist) {
        AudioTrack firstTrack = playlist.getSelectedTrack();

        if (firstTrack == null) {
          firstTrack = playlist.getTracks().get(0);
        }

        channel.sendMessage("Adding to queue " + firstTrack.getInfo().title + " (first track of playlist " + playlist.getName() + ")").queue();

        play(channel.getGuild(), musicManager, firstTrack);
      }

      @Override
      public void noMatches() {
        channel.sendMessage("Nothing found by " + trackUrl).queue();
      }

      @Override
      public void loadFailed(FriendlyException exception) {
        channel.sendMessage("Could not play: " + exception.getMessage()).queue();
      }
    });
  }

  private void play(Guild guild, GuildMusicManager musicManager, AudioTrack track) {
    connectToFirstVoiceChannel(guild.getAudioManager());

    musicManager.scheduler.queue(track);
  }

  private void skipTrack(TextChannel channel) {
    GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());
    musicManager.scheduler.nextTrack();

    channel.sendMessage("Skipped to next track.").queue();
  }

private static void connectToFirstVoiceChannel(AudioManager audioManager) {
    if (!audioManager.isConnected() && !audioManager.isAttemptingToConnect()) {
      for (VoiceChannel voiceChannel : audioManager.getGuild().getVoiceChannels()) {
    	  if (!voiceChannel.getMembers().contains(sender)) continue;
        audioManager.openAudioConnection(voiceChannel);
        break;
      }
    }
  }
}
