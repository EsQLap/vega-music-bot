package ru.esqlapy.command.handler;

import jakarta.annotation.Nonnull;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.callbacks.IReplyCallback;
import net.dv8tion.jda.api.managers.AudioManager;

public final class LeaveCommandHandler extends GuildCommandHandler {

    private static final String GOODBYE = "Goodbye, call me if you need";

    public void onLeaveCommand(@Nonnull Guild guild, @Nonnull IReplyCallback replyCallback) {
        AudioManager manager = guild.getAudioManager();
        globalMusicManager.clear(guild);
        manager.closeAudioConnection();
        replyCallback.reply(GOODBYE).queue();
    }
}
