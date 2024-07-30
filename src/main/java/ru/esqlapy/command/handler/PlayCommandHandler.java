package ru.esqlapy.command.handler;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.exceptions.InsufficientPermissionException;
import net.dv8tion.jda.api.interactions.callbacks.IReplyCallback;
import ru.esqlapy.audio.GlobalMusicManager;

import java.net.URI;

public final class PlayCommandHandler extends CommandHandler {

    private static final String YOU_MUST_BE_IN_CHANNEL = "Sorry, I can only work if you are in the voice channel";
    private static final String AUDIO_IS_DISABLED_DUE_TO_INTERNAL_ERROR = """
            Sorry, audio is disabled due to a my internal error
            """;
    private static final String I_HAVE_NOT_PERMISSION_TO_CONNECT_TEMPLATE = """
            Sorry, I haven't permission to connect to "%s" chanel
            """;
    private static final String YOUTUBE_SEARCH_TEMPLATE = "ytsearch:%s audio";
    private final GlobalMusicManager globalMusicManager = GlobalMusicManager.getInstance();

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

    @Nullable
    private VoiceChannel findVoiceChannelWithMember(@Nonnull Guild guild, @Nonnull Member member) {
        for (VoiceChannel voiceChannel : guild.getVoiceChannels()) {
            if (voiceChannel.getMembers().contains(member)) {
                return voiceChannel;
            }
        }
        return null;
    }

    private String requireIsUrlElseCreateYoutubeSearchRequest(String request) {
        if (isUrl(request)) {
            return request;
        }
        return YOUTUBE_SEARCH_TEMPLATE.formatted(request);
    }

    private boolean isUrl(@Nonnull String url) {
        try {
            new URI(url).toURL();
            return true;
        } catch (Throwable e) {
            return false;
        }
    }
}
