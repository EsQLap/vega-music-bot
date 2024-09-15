package ru.esqlapy;

import jakarta.annotation.Nonnull;

/**
 * Main class with entry point to the program.
 */
public final class Main {

    /**
     * Message to the user that he should pass the Discord-bot token as an argument when running the program.
     */
    private static final String NEED_TOKEN_AS_PROGRAM_ARGUMENT = "You need pass token as first program argument";

    /**
     * Method with entry point into the program.
     *
     * @param args
     *         arguments passed to the program when it is launched. The first of these must contain the Discord-bot
     *         token
     * @throws RuntimeException
     *         if no arguments were passed when the program was started
     * @throws net.dv8tion.jda.api.exceptions.InvalidTokenException
     *         if the provided token is invalid
     */
    public static void main(@Nonnull String[] args) {
        if (args.length < 1) {
            throw new RuntimeException(NEED_TOKEN_AS_PROGRAM_ARGUMENT);
        }
        String token = args[0];
        Bot.register(token);
    }
}
