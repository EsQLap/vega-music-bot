package ru.esqlapy.command;

public final class AboutCommand extends GlobalCommand {

    private static final String COMMAND_NAME = "about";
    private static final String COMMAND_DESCRIPTION = "Provide basic information about the bot";

    AboutCommand() {
        super(COMMAND_NAME, COMMAND_DESCRIPTION);
    }
}
