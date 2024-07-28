package ru.esqlapy.command;

public final class ClearCommand extends Command {

    private static final String COMMAND_NAME = "clear";
    private static final String COMMAND_DESCRIPTION = "Clear the queue";

    ClearCommand() {
        super(COMMAND_NAME, COMMAND_DESCRIPTION);
    }
}