package musicBot;

import java.util.Random;
import javax.security.auth.login.LoginException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDABuilder;

public class MusicBot 
{
    private final Random random = new Random();

    private MusicBot()
    {
        CommandManager commandManager = new CommandManager(random);
        Listener listener = new Listener(commandManager);
        Logger logger = LoggerFactory.getLogger(MusicBot.class);

        try 
        {
        	String token = "NjcwMDg0ODQ4NTA1MDYxMzc2.XlDLeQ.37DMzXPMPouxi4tR_ThY-fJ8BmY";
        	
            logger.info("Booting");
            new JDABuilder(AccountType.BOT)
                    .setToken(token).addEventListeners(listener)
                    .build().awaitReady();
            logger.info("Running");
        } 
        catch (LoginException | InterruptedException e) 
        {
            e.printStackTrace();
        } 
    }

    public static void main(String[] args) {
        new MusicBot();
    }

}