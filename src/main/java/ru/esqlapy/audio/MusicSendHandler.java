package ru.esqlapy.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.playback.MutableAudioFrame;
import jakarta.annotation.Nonnull;
import net.dv8tion.jda.api.audio.AudioSendHandler;

import java.nio.ByteBuffer;

public final class MusicSendHandler implements AudioSendHandler {

    private final AudioPlayer audioPlayer;
    private final ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
    private final MutableAudioFrame audioFrame = new MutableAudioFrame();

    public MusicSendHandler(@Nonnull AudioPlayer audioPlayer) {
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
