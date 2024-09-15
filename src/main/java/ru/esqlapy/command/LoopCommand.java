package ru.esqlapy.command;

import jakarta.annotation.Nonnull;
import ru.esqlapy.command.option.CommandOption;

import static net.dv8tion.jda.api.interactions.commands.OptionType.BOOLEAN;

/**
 * A {@link GuildCommand} that the user uses to loop the current audio track.
 */
public final class LoopCommand extends GuildCommand {

    private static final String COMMAND_NAME = "loop";
    private static final String COMMAND_DESCRIPTION = "Looping your currently playing track";
    private static final String ENABLE_OPTION_NAME = "enable";
    private static final String ENABLE_OPTION_DESCRIPTION = "Track looping enable status";
    private static final boolean ENABLE_OPTION_IS_REQUIRED = true;
    /**
     * Option containing the new loop state of the current track.
     * <p>It has a {@code boolean} type and signals that:
     * <li>if value is {@code true} then loop current audio track;</li>
     * <li>if value is {@code false} then stop looping current audio track.</li>
     */
    private final CommandOption enableOption;

    LoopCommand() {
        super(COMMAND_NAME, COMMAND_DESCRIPTION);
        this.enableOption = new CommandOption(
                BOOLEAN,
                ENABLE_OPTION_NAME,
                ENABLE_OPTION_DESCRIPTION,
                ENABLE_OPTION_IS_REQUIRED
        );
    }

    /**
     * Returns option containing the new loop state of the current track.
     * <p>It has a {@code boolean} type and signals that:
     * <li>if value is {@code true} then loop current audio track;</li>
     * <li>if value is {@code false} then stop looping current audio track.</li>
     *
     * @return option containing the new loop state of the current track
     */
    @Nonnull
    public CommandOption getEnableOption() {
        return enableOption;
    }
}
