package ru.esqlapy.command.handler;

import ru.esqlapy.audio.GlobalMusicManager;

/**
 * Discord guild command handler abstract class.
 */
abstract class GuildCommandHandler {

    protected final GlobalMusicManager globalMusicManager = GlobalMusicManager.getInstance();
}
