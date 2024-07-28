package ru.esqlapy;

import jakarta.annotation.Nonnull;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import ru.esqlapy.command.Command;
import ru.esqlapy.command.CommandDataFactory;
import ru.esqlapy.command.CommandProvider;
import ru.esqlapy.command.listener.CommandListener;
import ru.esqlapy.intent.IntentProvider;

import java.util.Collection;

public final class Bot {

    private final Collection<GatewayIntent> discordIntents = IntentProvider.getInstance().getIntents();
    private final Collection<CommandData> discordCommands;
    private final JDA jda;

    {
        Collection<Command> systemCommands = CommandProvider.getInstance().getSystemCommands();
        CommandDataFactory commandDataFactory = new CommandDataFactory();
        discordCommands = commandDataFactory.createCommandDataCollection(systemCommands);
    }

    private Bot(String token) {
        this.jda = createJDA(token);
        updateCommands();
    }

    @Nonnull
    private JDA createJDA(String token) {
        return JDABuilder.createDefault(token, discordIntents)
                .enableCache(CacheFlag.VOICE_STATE)
                .addEventListeners(new CommandListener())
                .build();
    }

    private void updateCommands() {
        CommandListUpdateAction commands = jda.updateCommands();
        commands.addCommands(discordCommands).queue();
    }

    public static void register(String token) {
        new Bot(token);
    }
}
