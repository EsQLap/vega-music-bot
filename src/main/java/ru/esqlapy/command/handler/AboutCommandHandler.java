package ru.esqlapy.command.handler;

import jakarta.annotation.Nonnull;
import net.dv8tion.jda.api.interactions.callbacks.IReplyCallback;

import java.util.Objects;

/**
 * Discord {@link ru.esqlapy.command.AboutCommand} handler.
 */
public final class AboutCommandHandler {

    /**
     * The application version taken from the {@code Manifest.MF} file. If the version is not specified,
     * then the value {@code NOT SPECIFIED} is returned.
     */
    private static final String APPLICATION_VERSION = Objects.requireNonNullElse(
            AboutCommandHandler.class.getPackage().getImplementationVersion(),
            "NOT SPECIFIED");
    /**
     * Basic information about the application available to the user.
     */
    private static final String MESSAGE = """
            Hi, my name is Vega (some users prefer to call me Allfather) and I am a music bot for Discord.
            You are presently using me with version %s
            I currently support playing music from the following services:
            - YouTube
            - SoundCloud
            - Bandcamp
            - Twitch streams
            By the way, by using my features, every user automatically agrees to buy one cheeseburger every month for [my creator](https://github.com/EsQLap) as payment.
            """.formatted(APPLICATION_VERSION);

    /**
     * Performs actions required when a bot receives the {@link ru.esqlapy.command.AboutCommand}:
     * <li>submit basic information about the application.</li>
     *
     * @param replyCallback
     *         an object that allows the bot to respond to user messages
     */
    public void onAboutCommand(@Nonnull IReplyCallback replyCallback) {
        replyCallback.reply(MESSAGE).queue();
    }
}
