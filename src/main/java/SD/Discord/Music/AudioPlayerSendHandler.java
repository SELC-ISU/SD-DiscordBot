package SD.Discord.Music;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.playback.AudioFrame;
import net.dv8tion.jda.api.audio.AudioSendHandler;

/**
 * This is a wrapper around AudioPlayer which makes it behave as an AudioSendHandler for JDA. As JDA calls canProvide
 * before every call to provide20MsAudio(), we pull the frame in canProvide() and use the frame we already pulled in
 * provide20MsAudio().
 */
public class AudioPlayerSendHandler implements AudioSendHandler 
{
    private final AudioPlayer audioPlayer;
    private AudioFrame lastFrame;
    private final ByteBuffer buffer;

    /**
     * @param audioPlayer Audio player to wrap.
     */
    public AudioPlayerSendHandler(AudioPlayer audioPlayer) 
    {
		this.audioPlayer = audioPlayer;
		this.buffer = ByteBuffer.allocate(1024);
    }

    @Override
    public boolean canProvide() 
    {
        if (lastFrame == null) 
        {
            lastFrame = audioPlayer.provide();
        }

        return lastFrame != null;
    }

    @Override
    public ByteBuffer provide20MsAudio() 
    {
      // flip to make it a read buffer
      ((Buffer) buffer).flip();
      return buffer;
    }

    @Override
    public boolean isOpus() 
    {
        return true;
    }
}