package ru.esqlapy.command;

/**
 * A {@link GuildCommand} that the user uses to clear the queue of audio tracks.
 */
public final class ClearCommand extends GuildCommand {

    private static final String COMMAND_NAME = "clear";
    private static final String COMMAND_DESCRIPTION = "Clear the track queue";

    ClearCommand() {
        super(COMMAND_NAME, COMMAND_DESCRIPTION);
    }
}
