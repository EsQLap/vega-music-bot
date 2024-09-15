package ru.esqlapy.command;

import jakarta.annotation.Nonnull;
import ru.esqlapy.command.option.CommandOption;

import static net.dv8tion.jda.api.interactions.commands.OptionType.STRING;

/**
 * A {@link GuildCommand} that the user uses to add an audio track to the queue.
 */
public final class PlayCommand extends GuildCommand {

    private static final String COMMAND_NAME = "play";
    private static final String COMMAND_DESCRIPTION = "Add track to playback queue";
    private static final String CONTENT_OPTION_NAME = "request";
    private static final String CONTENT_OPTION_DESCRIPTION = "What the bot should say";
    private static final boolean CONTENT_OPTION_IS_REQUIRED = true;
    /**
     * An option containing a request with information about the audio track being added
     * (track name or {@code url} to it).
     */
    private final CommandOption contentOption;

    PlayCommand() {
        super(COMMAND_NAME, COMMAND_DESCRIPTION);
        this.contentOption = new CommandOption(
                STRING,
                CONTENT_OPTION_NAME,
                CONTENT_OPTION_DESCRIPTION,
                CONTENT_OPTION_IS_REQUIRED
        );
    }

    /**
     * Returns option containing a request with information about the audio track being added
     * (track name or {@code url} to it).
     *
     * @return option containing a request with information about the audio track being added
     */
    @Nonnull
    public CommandOption getContentOption() {
        return contentOption;
    }
}
