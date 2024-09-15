package ru.esqlapy.command.handler;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.exceptions.InsufficientPermissionException;
import net.dv8tion.jda.api.interactions.callbacks.IReplyCallback;

import java.net.URI;

/**
 * Discord {@link ru.esqlapy.command.PlayCommand} handler.
 */
public final class PlayCommandHandler extends GuildCommandHandler {

    /**
     * A response template for a situation where the user who sent the command is not a member of any voice channel.
     */
    private static final String YOU_MUST_BE_IN_CHANNEL = "Sorry, I can only work if you are in the voice channel";
    /**
     * A response template for when the bot receives an unexpected playback error.
     */
    private static final String AUDIO_IS_DISABLED_DUE_TO_INTERNAL_ERROR = """
            Sorry, audio is disabled due to a my internal error
            """;
    /**
     * A template for responding to a situation where a bot does not have permission to connect to a voice channel.
     */
    private static final String I_HAVE_NOT_PERMISSION_TO_CONNECT_TEMPLATE = """
            Sorry, I haven't permission to connect to "%s" chanel
            """;
    /**
     * Template of {@link dev.lavalink.youtube.YoutubeAudioSourceManager} search format.
     */
    private static final String YOUTUBE_SEARCH_TEMPLATE = "ytsearch:%s audio";

    /**
     * Performs actions required when a bot receives the {@link ru.esqlapy.command.PlayCommand}:
     * <li>add audio track to queue and start playing it if possible;</li>
     * <li>otherwise, inform the user in a response message about a negative result.</li>
     *
     * @param guild
     *         information about the guild from which the command was sent
     * @param member
     *         an object containing all guild-specific information about a user
     * @param request
     *         information about the audio track being added (track name or {@code url} to it)
     * @param replyCallback
     *         an object that allows the bot to respond to user messages
     */
    public void onPlayCommand(
            @Nonnull Guild guild,
            @Nonnull Member member,
            @Nonnull String request,
            @Nonnull IReplyCallback replyCallback) {
        VoiceChannel channel = findVoiceChannelWithMember(guild, member);
        if (channel == null) {
            replyCallback.reply(YOU_MUST_BE_IN_CHANNEL).queue();
            return;
        }
        try {
            guild.getAudioManager().openAudioConnection(channel);
        } catch (UnsupportedOperationException e) {
            replyCallback.reply(AUDIO_IS_DISABLED_DUE_TO_INTERNAL_ERROR).queue();
            return;
        } catch (InsufficientPermissionException e) {
            replyCallback.reply(I_HAVE_NOT_PERMISSION_TO_CONNECT_TEMPLATE.formatted(channel.getName())).queue();
            return;
        }
        String trackUrl = requireIsUrlElseCreateYoutubeSearchRequest(request);
        globalMusicManager.loadAndPlay(guild, trackUrl, replyCallback);
    }

    /**
     * Find the voice channel the user is currently in.
     *
     * @param guild
     *         information about the guild from which the command was sent
     * @param member
     *         an object containing all guild-specific information about a user
     * @return {@link VoiceChannel} the user is currently in, {@code null} if the user is not in the voice channel
     */
    @Nullable
    private VoiceChannel findVoiceChannelWithMember(@Nonnull Guild guild, @Nonnull Member member) {
        for (VoiceChannel voiceChannel : guild.getVoiceChannels()) {
            if (voiceChannel.getMembers().contains(member)) {
                return voiceChannel;
            }
        }
        return null;
    }

    /**
     * Converts the request string to a format supported by the search engine.
     *
     * @param request
     *         information about the audio track being added (track name or {@code url} to it)
     * @return same value to {@code request} parameter if it matches the {@code url} pattern, convert to request in
     * {@link dev.lavalink.youtube.YoutubeAudioSourceManager} search format
     */
    private String requireIsUrlElseCreateYoutubeSearchRequest(@Nonnull String request) {
        if (isUrl(request)) {
            return request;
        }
        return YOUTUBE_SEARCH_TEMPLATE.formatted(request);
    }

    /**
     * Checks if a string matches a {@code url} pattern.
     *
     * @param url
     *         text being checked
     * @return {@code true} if the string matches the {@code url} pattern, otherwise {@code false}
     */
    private boolean isUrl(@Nonnull String url) {
        try {
            new URI(url).toURL();
            return true;
        } catch (Throwable e) {
            return false;
        }
    }
}
