package ru.esqlapy.audio.guild;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.playback.MutableAudioFrame;
import jakarta.annotation.Nonnull;
import net.dv8tion.jda.api.audio.AudioSendHandler;

import java.nio.ByteBuffer;

/**
 * Class used to send audio to Discord through JDA.
 */
final class MusicSendHandler implements AudioSendHandler {

    /**
     * An audio player that is capable of playing audio tracks and provides audio frames
     * from the currently playing track.
     */
    private final AudioPlayer audioPlayer;
    /**
     * A byte buffer containing information about the next 20 ms of an audio track.
     */
    private final ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
    /**
     * An audio frame of the audio track.
     */
    private final MutableAudioFrame audioFrame = new MutableAudioFrame();

    /**
     * Creates an object used to send audio to Discord via JDA.
     *
     * @param audioPlayer
     *         an audio player that is capable of playing audio tracks and provides audio frames
     *         from the currently playing track.
     */
    MusicSendHandler(@Nonnull AudioPlayer audioPlayer) {
        this.audioPlayer = audioPlayer;
        this.audioFrame.setBuffer(byteBuffer);
    }

    @Override
    public boolean canProvide() {
        return audioPlayer.provide(this.audioFrame);
    }

    @Nonnull
    @Override
    public ByteBuffer provide20MsAudio() {
        return this.byteBuffer.flip();
    }

    @Override
    public boolean isOpus() {
        return true;
    }
}
