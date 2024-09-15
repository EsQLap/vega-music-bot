package ru.esqlapy.audio;

import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.callbacks.IReplyCallback;
import ru.esqlapy.audio.guild.GuildMusicManagerPool;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Manager of all audio activities related to Discord guilds.
 */
public final class GlobalMusicManager {

    /**
     * Interval for performing the task of cleaning useless activities.
     */
    private static final long DISPOSE_TASK_DELAY = 5;
    /**
     * Units of measurement for the interval of execution of the task of cleaning useless activities.
     */
    private static final TimeUnit DISPOSE_TASK_TIME_UNIT = TimeUnit.MINUTES;
    /**
     * Instance of {@link GlobalMusicManager}.
     */
    private static final GlobalMusicManager INSTANCE = new GlobalMusicManager();
    /**
     * Service that runs the task of cleaning useless activities.
     */
    private final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
    private final GuildMusicManagerPool guildMusicManagerPool = GuildMusicManagerPool.getInstance();

    {
        scheduledExecutorService.scheduleWithFixedDelay(
                guildMusicManagerPool::disposeFreeMusicManagers,
                DISPOSE_TASK_DELAY,
                DISPOSE_TASK_DELAY,
                DISPOSE_TASK_TIME_UNIT);
        Runtime.getRuntime().addShutdownHook(new Thread(scheduledExecutorService::shutdownNow));
    }

    private GlobalMusicManager() {
    }

    /**
     * Downloads an audio track from the Internet, adds it to the audio track queue and starts playing it
     * in the specified {@code guild}.
     *
     * @param guild
     *         an object containing all the information provided by Discord about the guild
     * @param trackUrl
     *         link to audio track in url format
     * @param replyCallback
     *         an object that allows the bot to respond to user messages
     */
    public void loadAndPlay(@Nonnull Guild guild, @Nonnull String trackUrl, @Nonnull IReplyCallback replyCallback) {
        guildMusicManagerPool.getMusicManager(guild).loadFromInternet(trackUrl, replyCallback);
    }

    /**
     * Skips the current audio track playing in the specified {@code guild}.
     *
     * @param guild
     *         an object containing all the information provided by Discord about the guild
     * @return {@code true} if the audio track was successfully skipped, otherwise {@code false}
     */
    public boolean skip(@Nonnull Guild guild) {
        return guildMusicManagerPool.getMusicManager(guild).skipTrack();
    }

    /**
     * Changes the loop states of the current audio track playing in the specified {@code guild}.
     *
     * @param guild
     *         an object containing all the information provided by Discord about the guild
     * @param enable
     *         {@code boolean} representation of the loop state of the current track
     * @return information about the missed track, {@code null} if the audio track queue was empty
     */
    @Nullable
    public AudioTrackInfo loopCurrentTrack(@Nonnull Guild guild, boolean enable) {
        return guildMusicManagerPool.getMusicManager(guild).setLoopCurrentTrack(enable);
    }

    /**
     * Clears the audio track queue in the specified {@code guild}.
     *
     * @param guild
     *         an object containing all the information provided by Discord about the guild
     */
    public void clear(@Nonnull Guild guild) {
        guildMusicManagerPool.getMusicManager(guild).clearQueue();
    }

    /**
     * Dispose all audio activities related with the specified {@code guild}.
     *
     * @param guild
     *         an object containing all the information provided by Discord about the guild
     */
    public void dispose(@Nonnull Guild guild) {
        guildMusicManagerPool.disposeMusicManager(guild);
    }

    /**
     * Returns the instance of {@link GlobalMusicManager}.
     *
     * @return instance of {@link GlobalMusicManager}
     */
    @Nonnull
    public static GlobalMusicManager getInstance() {
        return INSTANCE;
    }
}
