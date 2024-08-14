package ru.esqlapy.event.handler;

import jakarta.annotation.Nonnull;
import net.dv8tion.jda.api.entities.Member;
import ru.esqlapy.audio.GlobalMusicManager;

abstract class GuildEventHandler {

    protected final GlobalMusicManager globalMusicManager = GlobalMusicManager.getInstance();

    protected boolean isItMe(@Nonnull Member member) {
        return member.getIdLong() == member.getJDA().getSelfUser().getIdLong();
    }
}
