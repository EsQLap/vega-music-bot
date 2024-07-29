package ru.esqlapy.command;

import jakarta.annotation.Nonnull;
import ru.esqlapy.command.option.CommandOption;

import static net.dv8tion.jda.api.interactions.commands.OptionType.STRING;

public final class PlayCommand extends Command {

    private static final String COMMAND_NAME = "play";
    private static final String COMMAND_DESCRIPTION = "Add track to playback queue";
    private static final String CONTENT_OPTION_NAME = "content";
    private static final String CONTENT_OPTION_DESCRIPTION = "What the bot should say";
    private static final boolean CONTENT_OPTION_IS_REQUIRED = true;
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

    @Nonnull
    public CommandOption getContentOption() {
        return contentOption;
    }
}
