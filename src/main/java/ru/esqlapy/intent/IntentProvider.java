package ru.esqlapy.intent;

import jakarta.annotation.Nonnull;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.util.Collection;
import java.util.List;

import static net.dv8tion.jda.api.requests.GatewayIntent.GUILD_VOICE_STATES;

public final class IntentProvider {

    private static final IntentProvider INSTANCE = new IntentProvider();
    private final Collection<GatewayIntent> intents = List.of(GUILD_VOICE_STATES);

    private IntentProvider() {
    }

    @Nonnull
    public static IntentProvider getInstance() {
        return INSTANCE;
    }

    @Nonnull
    public Collection<GatewayIntent> getIntents() {
        return intents;
    }
}
