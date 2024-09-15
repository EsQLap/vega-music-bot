package ru.esqlapy.command;

/**
 * A {@link GuildCommand} that the user uses to skip the current audio track.
 */
public final class SkipCommand extends GuildCommand {

    private static final String COMMAND_NAME = "skip";
    private static final String COMMAND_DESCRIPTION = "Skip the current track";

    SkipCommand() {
        super(COMMAND_NAME, COMMAND_DESCRIPTION);
    }
}
