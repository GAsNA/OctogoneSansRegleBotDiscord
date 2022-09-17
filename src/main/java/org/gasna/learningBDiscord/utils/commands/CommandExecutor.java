package org.gasna.learningBDiscord.utils.commands;

import org.gasna.learningBDiscord.Main;
import org.javacord.api.event.message.MessageCreateEvent;

public interface CommandExecutor {

    String PREFIX = Main.getConfigManager().getToml().getString("bot.prefix");

    void run(MessageCreateEvent event, Command command, String[] args);

}
