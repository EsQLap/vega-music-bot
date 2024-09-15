package ru.esqlapy.event.handler;

import jakarta.annotation.Nonnull;
import net.dv8tion.jda.api.entities.Member;

/**
 * Discord guild event handler abstract class.
 */
abstract class GuildEventHandler {

    /**
     * Checks if the current bot is the specified user.
     *
     * @param member
     *         an object containing all guild-specific information about a user
     * @return {@code true} if the current bot is the specified user, {@code false} otherwise
     */
    protected boolean isItMe(@Nonnull Member member) {
        return member.getIdLong() == member.getJDA().getSelfUser().getIdLong();
    }
}
