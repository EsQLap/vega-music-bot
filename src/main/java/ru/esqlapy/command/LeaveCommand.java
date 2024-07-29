package ru.esqlapy.command;

public final class LeaveCommand extends Command {

    private static final String COMMAND_NAME = "leave";
    private static final String COMMAND_DESCRIPTION = "Leave from the voice channel";

    LeaveCommand() {
        super(COMMAND_NAME, COMMAND_DESCRIPTION);
    }
}
