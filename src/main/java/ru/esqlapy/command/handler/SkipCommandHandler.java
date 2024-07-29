package ru.esqlapy.command.handler;

import jakarta.annotation.Nonnull;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.callbacks.IReplyCallback;
import ru.esqlapy.audio.GlobalMusicManager;

public class SkipCommandHandler extends CommandHandler {

    private static final String TRACK_SUCCESSFULLY_SKIPPED = "Track successfully skipped";
    private static final String NO_TRACK_IN_QUEUE = "I can't skip track in empty queue";
    private final GlobalMusicManager globalMusicManager = GlobalMusicManager.getInstance();

    public void onSkipCommand(@Nonnull Guild guild, @Nonnull IReplyCallback replyCallback) {
        boolean result = globalMusicManager.skip(guild);
        String message = result ? TRACK_SUCCESSFULLY_SKIPPED : NO_TRACK_IN_QUEUE;
        replyCallback.reply(message).queue();
    }
}
