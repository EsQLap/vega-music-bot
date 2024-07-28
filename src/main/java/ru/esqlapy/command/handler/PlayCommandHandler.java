package ru.esqlapy.command.handler;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import ru.esqlapy.audio.GlobalMusicManager;
import ru.esqlapy.command.PlayCommand;

import java.net.URI;

public final class PlayCommandHandler extends CommandHandler {

    private static final String NO_REQUEST_TEMPLATE = """
            Sorry, I can't find the content of you request.
            Please, make sure you filled in the field "%s" correctly and send the "%s" command again
            """;
    private static final String YOU_MUST_BE_IN_CHANNEL = "Sorry, I can only work if you are in the voice channel";
    private static final String YOUTUBE_SEARCH_TEMPLATE = "ytsearch:%s audio";
    private final GlobalMusicManager globalMusicManager = GlobalMusicManager.getInstance();

    public void onPlayCommand(@Nonnull PlayCommand playCommand, @Nonnull SlashCommandInteractionEvent event) {
        Guild guild = event.getGuild();
        Member member = event.getMember();
        if (guild == null || member == null) {
            sendMessage(
                    event.getChannel(),
                    WORK_ONLY_IN_CHANNEL
            );
            return;
        }
        VoiceChannel channel = findVoiceChannelWithMember(guild, member);
        if (channel == null) {
            sendMessage(
                    event.getChannel(),
                    YOU_MUST_BE_IN_CHANNEL
            );
            return;
        }
        guild.getAudioManager().openAudioConnection(channel);
        String requestOptionName = playCommand.getContentOption().name();
        OptionMapping option = event.getOption(requestOptionName);
        if (option == null) {
            sendMessage(
                    event.getChannel(),
                    NO_REQUEST_TEMPLATE.formatted(requestOptionName, playCommand.getName())
            );
            return;
        }
        String request = option.getAsString();
        if (!isUrl(request)) {
            request = YOUTUBE_SEARCH_TEMPLATE.formatted(request);
        }
        globalMusicManager.loadAndPlay(event.getChannel().asTextChannel(), request);
    }

    @Nullable
    private VoiceChannel findVoiceChannelWithMember(@Nonnull Guild guild, @Nonnull Member member) {
        for (VoiceChannel voiceChannel : guild.getVoiceChannels()) {
            if(voiceChannel.getMembers().contains(member)) {
                return voiceChannel;
            }
        }
        return null;
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
