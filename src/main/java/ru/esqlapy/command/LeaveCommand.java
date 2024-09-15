package ru.esqlapy.command;

/**
 * A {@link GuildCommand} that the user uses to kick the bot from the audio channel.
 */
public final class LeaveCommand extends GuildCommand {

    private static final String COMMAND_NAME = "leave";
    private static final String COMMAND_DESCRIPTION = "Leave from the voice channel";

    LeaveCommand() {
        super(COMMAND_NAME, COMMAND_DESCRIPTION);
    }
}
