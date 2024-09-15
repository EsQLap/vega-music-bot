package ru.esqlapy.intent;

import jakarta.annotation.Nonnull;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.util.Collection;
import java.util.List;

import static net.dv8tion.jda.api.requests.GatewayIntent.GUILD_VOICE_STATES;

/**
 * A class that provides Discord-intents used in the system.
 */
public final class IntentProvider {

    /**
     * Instance of {@link IntentProvider}.
     */
    private static final IntentProvider INSTANCE = new IntentProvider();
    /**
     * Collection of Discord-intents used in the system.
     */
    private final Collection<GatewayIntent> intents = List.of(GUILD_VOICE_STATES);

    private IntentProvider() {
    }

    /**
     * Returns the instance of {@link IntentProvider}.
     *
     * @return instance of {@link IntentProvider}
     */
    @Nonnull
    public static IntentProvider getInstance() {
        return INSTANCE;
    }

    /**
     * Returns the collection of Discord-intents used in the system.
     *
     * @return collection of Discord-intents
     */
    @Nonnull
    public Collection<GatewayIntent> getIntents() {
        return intents;
    }
}
