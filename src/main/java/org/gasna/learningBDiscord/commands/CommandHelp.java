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
        embed.addInlineField("Ping the bot", "You can ping the bot with the command ``" + PREFIX + "ping`` or ``" + PREFIX +"p?``.")
                //.addInlineField("Tic-tac-toe", "You can play tic-tac-toe with: ``" + PREFIX + "3t``, ``" + PREFIX + "t3``, ``" + PREFIX + "ttt`` or ``" + PREFIX + "tic-tac-toe``. Kwon how to play with ``" + PREFIX + "t3 help`` or ``" + PREFIX + "t3 h``.")
                .addInlineField("Octogone sans regle!", "You can battle your friend in an Octogone Sans Regle with: ``" + PREFIX + "octogone``, ``" + PREFIX + "octo`` or ``" + PREFIX + "8``. Know how to play with ``" + PREFIX + "octo help`` or ``" + PREFIX + "octo h``.");
        send_message(event, embed);
    }
}
