package musicBot;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class CommandManager 
{
    private final Map<String, ICommand> commands = new HashMap<>();
    CommandManager(Random random) 
    {
        addCommand(new JoinCommand());
        addCommand(new PlayCommand());
    }

    private void addCommand(ICommand command) 
    {
        if (!commands.containsKey(command.getInvoke())) 
        {
            commands.put(command.getInvoke(), command);
        }
    }

    public Collection<ICommand> getCommands() 
    {
        return commands.values();
    }

    public ICommand getCommand(@NotNull String name) 
    {
        return commands.get(name);
    }

    void handleCommand(GuildMessageReceivedEvent event) 
    {
        final String prefix = Prefixes.PREFIXES.get(event.getGuild().getIdLong());

        final String[] split = event.getMessage().getContentRaw().replaceFirst(
                "(?i)" + Pattern.quote(prefix), "").split("\\s+");
        final String invoke = split[0].toLowerCase();

        if (commands.containsKey(invoke)) {
            final List<String> args = Arrays.asList(split).subList(1, split.length);

            event.getChannel().sendTyping().queue();
            commands.get(invoke).handle(args, event);
        }
    }
}