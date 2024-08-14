package ru.esqlapy.command.handler;

import ru.esqlapy.audio.GlobalMusicManager;

abstract class GuildCommandHandler {

    protected final GlobalMusicManager globalMusicManager = GlobalMusicManager.getInstance();
}
