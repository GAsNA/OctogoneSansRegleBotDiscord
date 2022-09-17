package org.gasna.learningBDiscord.commands;

import org.gasna.learningBDiscord.utils.commands.Command;
import org.gasna.learningBDiscord.utils.commands.CommandExecutor;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

import static org.gasna.learningBDiscord.utils.Embed.get_embed_help;
import static org.gasna.learningBDiscord.utils.SendMessage.send_message;

public class CommandHelp implements CommandExecutor {
    @Override
    public void run(MessageCreateEvent event, Command command, String[] args) {
        EmbedBuilder embed = get_embed_help("Help", "Discover the commands", "Enjoy!");
        embed.addInlineField("Ping the bot", "You can ping the bot with the command ``&ping`` or ``&p?``.")
                .addInlineField("Tic-tac-toe", "You can play tic-tac-toe with: ``&3t``, ``&t3``, ``&ttt`` or ``&tic-tac-toe``. Kwon how to play with ``&t3 help`` or ``&t3 h``.")
                .addInlineField("Octogone sans regle!", "");
        send_message(event, embed);
    }
}
