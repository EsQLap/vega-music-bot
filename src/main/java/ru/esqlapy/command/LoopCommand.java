package ru.esqlapy.command;

import jakarta.annotation.Nonnull;
import ru.esqlapy.command.option.CommandOption;

import static net.dv8tion.jda.api.interactions.commands.OptionType.BOOLEAN;

public final class LoopCommand extends Command {

    private static final String COMMAND_NAME = "loop";
    private static final String COMMAND_DESCRIPTION = "Looping your currently playing track";
    private static final String ENABLE_OPTION_NAME = "enable";
    private static final String ENABLE_OPTION_DESCRIPTION = "Track looping enable status";
    private static final boolean ENABLE_OPTION_IS_REQUIRED = true;
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

    @Nonnull
    public CommandOption getEnableOption() {
        return enableOption;
    }
}
