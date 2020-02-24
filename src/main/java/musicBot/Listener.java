package musicBot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

class Listener extends ListenerAdapter 
{
    private final CommandManager manager;
    private final Logger logger = LoggerFactory.getLogger(Listener.class);
    Listener(CommandManager manager) 
    {
        this.manager = manager;
    }

    @Override
    public void onReady(ReadyEvent event) 
    {
        logger.info(String.format("Logged in as %#s", event.getJDA().getSelfUser()));
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) 
    {
        User author = event.getAuthor();
        Message message = event.getMessage();
        String content = message.getContentDisplay();
        if (event.isFromType(ChannelType.TEXT)) 
        {
            Guild guild = event.getGuild();
            TextChannel textChannel = event.getTextChannel();
            logger.info(String.format("(%s)[%s]<%#s>: %s", guild.getName(), textChannel.getName(), author, content));
        }
        else if (event.isFromType(ChannelType.PRIVATE)) 
        {
            logger.info(String.format("[PRIV]<%#s>: %s", author, content));
        }
    }

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) 
  {
        String rw = event.getMessage().getContentRaw();
        if (rw.equalsIgnoreCase(Prefixes.PREFIX + "shutdown") &&
                event.getAuthor().getIdLong() == Prefixes.OWNER) 
        {
            shutdown(event.getJDA());
            return;
        }

        String prefix = Prefixes.PREFIXES.computeIfAbsent(event.getGuild().getIdLong(), (l) -> Prefixes.PREFIX);

        if (!event.getAuthor().isBot() && !event.getMessage().isWebhookMessage() && rw.startsWith(prefix)) 
        {
            manager.handleCommand(event);
        }
    }

    private void shutdown(JDA jda) 
    {
        jda.shutdown();
        System.exit(0);
    }
}
