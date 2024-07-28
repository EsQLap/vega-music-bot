package ru.esqlapy.audio.source;

import dev.lavalink.youtube.YoutubeAudioSourceManager;
import dev.lavalink.youtube.clients.AndroidTestsuiteWithThumbnail;
import dev.lavalink.youtube.clients.MusicWithThumbnail;
import dev.lavalink.youtube.clients.WebWithThumbnail;
import jakarta.annotation.Nonnull;

public final class YoutubeAudioSourceManagerProvider {

    private static final YoutubeAudioSourceManagerProvider INSTANCE = new YoutubeAudioSourceManagerProvider();
    private final YoutubeAudioSourceManager youtubeSourceManager = new YoutubeAudioSourceManager(
            true,
            new MusicWithThumbnail(),
            new WebWithThumbnail(),
            new AndroidTestsuiteWithThumbnail());

    private YoutubeAudioSourceManagerProvider(){
    }

    @Nonnull
    public YoutubeAudioSourceManager getYoutubeSourceManager() {
        return youtubeSourceManager;
    }

    @Nonnull
    public static YoutubeAudioSourceManagerProvider getInstance() {
        return INSTANCE;
    }
}
