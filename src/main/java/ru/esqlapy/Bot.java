package ru.esqlapy;

import jakarta.annotation.Nonnull;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import ru.esqlapy.command.CommandDataFactory;
import ru.esqlapy.command.CommandProvider;
import ru.esqlapy.command.listener.CommandListener;
import ru.esqlapy.event.listener.EventListener;
import ru.esqlapy.intent.IntentProvider;

import java.util.Collection;

/**
 * A class used to create a Discord-bot instance.
 */
public final class Bot {

    /**
     * Flags which enable or disable specific events from the discord gateway.
     */
    private final Collection<GatewayIntent> discordIntents = IntentProvider.getInstance().getIntents();
    /**
     * Commands used by the user when interacting with the bot.
     */
    private final Collection<CommandData> discordCommands = new CommandDataFactory()
            .createCommandDataCollection(
                    CommandProvider.getInstance().getSystemCommands()
            );

    /**
     * Creates a Discord-bot implementation and registers it in the system.
     *
     * @param token
     *         Discord-bot token
     */
    private Bot(@Nonnull String token) {
        JDA jda = createJDA(token);
        jda.addEventListener(new CommandListener(), new EventListener());
        updateCommands(jda);
    }

    /**
     * Creates an object of {@link JDA} with the provided intents from {@link Bot#discordIntents}.
     *
     * @param token
     *         Discord-bot token
     * @return implementation of {@link JDA}
     */
    @Nonnull
    private JDA createJDA(@Nonnull String token) {
        return JDABuilder.createDefault(token, discordIntents)
                .enableCache(CacheFlag.VOICE_STATE)
                .build();
    }

    /**
     * Updates the bot commands on the server according to {@link Bot#discordCommands}.
     *
     * @param jda
     *         implementation of {@link JDA}
     * @throws net.dv8tion.jda.api.exceptions.InvalidTokenException
     *         if the provided token is invalid
     */
    private void updateCommands(@Nonnull JDA jda) {
        CommandListUpdateAction commands = jda.updateCommands();
        commands.addCommands(discordCommands).queue();
    }

    /**
     * Registers a new instance of the bot in the system.
     *
     * @throws net.dv8tion.jda.api.exceptions.InvalidTokenException
     *         if the provided token is invalid
     */
    public static void register(@Nonnull String token) {
        new Bot(token);
    }
}
