[release-shield]: https://img.shields.io/github/v/release/EsQLap/vega-music-bot?color=violet
[releases-link]: https://github.com/EsQLap/vega-music-bot/releases
[discord-shield]: https://img.shields.io/badge/official_bot-Invite-5865F2.svg
[discord-bot-invite-link]: https://discord.com/oauth2/authorize?client_id=1266768155007651923&permissions=0&integration_type=0&scope=applications.commands+bot
[license-shield]: https://img.shields.io/badge/license-MIT-blue.svg
[license-link]: https://github.com/EsQLap/vega-music-bot/blob/main/LICENSE
[youtube-link]: https://www.youtube.com/
[soundcloud-link]: https://soundcloud.com/
[bandcamp-link]: https://bandcamp.com/
[twitch-link]: https://www.twitch.tv/
[jda-official-repository]: https://github.com/discord-jda/JDA
[jda-user-guide-link]: https://jda.wiki/using-jda/getting-started/#creating-a-discord-bot

[![release-shield][]][releases-link]
[![discord-shield][]][discord-bot-invite-link]
[![license-shield][]][license-link]

# Vega: music bot for Discord

**Vega** is an open-source implementation of a music bot for **Discord**.
This application provides you with the most
simple functionality for listening to music in **Discord-channels** with your friends.

## 🕹️ Available commands

1. **Play** - Add track to playback queue;
2. **Skip** - Skip the current track;
3. **Loop** - Looping your currently playing track;
4. **Clear** - Clear the track queue;
5. **Leave** - Leave from the voice channel;
6. **About** - Provide you basic information about the bot (such as current version, supported music services,
terms of use).

## 🎶 Supported music services

1. [YouTube][youtube-link] (by video name or link);
2. [SoundCloud][soundcloud-link] (link only);
3. [Bandcamp][bandcamp-link] (link only);
4. [Twitch][twitch-link] streams (link only).

## 📨 Add Vega to your chanel

To add the bot to your **Discord-channel**, follow [this link][discord-bot-invite-link].

## 👩🏻‍💻 Implement self-hosted version

1. Create your application by using [step-by-step guide][jda-user-guide-link] from [JDA][jda-official-repository] command;
2. Download latest release from [releases page][releases-link];
3. Run application by using the following command in command line:
```
java -jar vega-$VERSION.jar $YOUR_BOT_TOKEN
```
> [!IMPORTANT]
> "\$VERSION" and "\$YOUR_BOT_TOKEN" are variables, the first one is identified with the version of
> the downloaded application from [releases page][releases-link], the second one is the bot token in your application,
> obtained when creating the application according to the [step-by-step guide][jda-user-guide-link].
