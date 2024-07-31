package ru.esqlapy;

public final class Main {

    private static final String NEED_TOKEN_AS_PROGRAM_ARGUMENT = "You need pass token as first program argument";

    public static void main(String[] args) {
        if (args.length < 1) {
            throw new RuntimeException(NEED_TOKEN_AS_PROGRAM_ARGUMENT);
        }
        String token = args[0];
        Bot.register(token);
    }
}
