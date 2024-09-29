package ru.esqlapy.audio.source;

import dev.lavalink.youtube.YoutubeAudioSourceManager;
import dev.lavalink.youtube.clients.AndroidMusicWithThumbnail;
import dev.lavalink.youtube.clients.AndroidTestsuiteWithThumbnail;
import dev.lavalink.youtube.clients.MusicWithThumbnail;
import dev.lavalink.youtube.clients.WebWithThumbnail;
import jakarta.annotation.Nonnull;

/**
 * A class that provides source for YouTube search requests.
 */
public final class YoutubeAudioSourceManagerProvider {

    /**
     * Instance of {@link YoutubeAudioSourceManagerProvider}.
     */
    private static final YoutubeAudioSourceManagerProvider INSTANCE = new YoutubeAudioSourceManagerProvider();
    /**
     * Source for YouTube search requests.
     */
    private final YoutubeAudioSourceManager youtubeSourceManager = new YoutubeAudioSourceManager(
            true,
            new MusicWithThumbnail(),
            new WebWithThumbnail(),
            new AndroidMusicWithThumbnail());

    private YoutubeAudioSourceManagerProvider() {
    }

    /**
     * Returns the instance of {@link YoutubeAudioSourceManagerProvider}.
     * @return instance of {@link YoutubeAudioSourceManagerProvider}
     */
    @Nonnull
    public YoutubeAudioSourceManager getYoutubeSourceManager() {
        return youtubeSourceManager;
    }

    /**
     * Returns the source for YouTube search requests.
     * @return the source for YouTube search requests
     */
    @Nonnull
    public static YoutubeAudioSourceManagerProvider getInstance() {
        return INSTANCE;
    }
}
