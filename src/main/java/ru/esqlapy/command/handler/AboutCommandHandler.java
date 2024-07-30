package ru.esqlapy.command.handler;

import jakarta.annotation.Nonnull;
import net.dv8tion.jda.api.interactions.callbacks.IReplyCallback;

public class AboutCommandHandler extends CommandHandler {

    private static final String MESSAGE = """
            Hi, my name is Vega (some users prefer to call me Allfather) and I am a music bot for Discord.
            I currently support playing music from the following services:
            - YouTube
            - SoundCloud
            - Bandcamp
            - Twitch streams
            By the way, by using my features, every user automatically agrees to buy one cheeseburger every month for [my creator](https://github.com/EsQLap) as payment.
            """;

    public void onAboutCommand(@Nonnull IReplyCallback replyCallback) {
        replyCallback.reply(MESSAGE).queue();
    }
}
