package org.gasna.learningBDiscord.commands;

import org.gasna.learningBDiscord.utils.commands.Command;
import org.gasna.learningBDiscord.utils.commands.CommandExecutor;
import org.javacord.api.event.message.MessageCreateEvent;

import static org.gasna.learningBDiscord.utils.SendMessage.send_message;

public class CommandOctogone implements CommandExecutor {
    @Override
    public void run(MessageCreateEvent event, Command command, String[] args) {
        send_message(event, "Coucou c'est moi");
    }
}
