package ru.esqlapy.command;

public final class SkipCommand extends Command {

    private static final String COMMAND_NAME = "skip";
    private static final String COMMAND_DESCRIPTION = "Skip the current track";

    SkipCommand() {
        super(COMMAND_NAME, COMMAND_DESCRIPTION);
    }
}
