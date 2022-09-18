package org.gasna.learningBDiscord.utils.commands;

import org.gasna.learningBDiscord.Main;
import org.gasna.learningBDiscord.commands.Command3T;
import org.gasna.learningBDiscord.commands.CommandHelp;
import org.gasna.learningBDiscord.commands.CommandOctogone;
import org.gasna.learningBDiscord.commands.CommandPing;
import org.javacord.api.entity.message.MessageAttachment;
import org.javacord.api.event.message.MessageCreateEvent;

import java.util.Arrays;
import java.util.List;

public class MessageManager {

    private static final CommandRegistry registry = new CommandRegistry();

    static {
        registry.addCommand(new Command(
                "ping",
                "Pings the bot",
                new CommandPing(),
                "ping", "p?"
        ));
        /*registry.addCommand(new Command(
                "tic-tac-toe",
                "Play tic-tac-toe with friend",
                new Command3T(),
                "3t", "tic-tac-toe", "t3", "ttt"
        ));*/
        registry.addCommand(new Command(
                "help",
                "Help for Learning bot",
                new CommandHelp(),
                "help", "h"
        ));
        registry.addCommand(new Command(
                "octogone sans regle",
                "Octogone sans regle tmtc",
                new CommandOctogone(),
                "octogone", "octo", "8"
        ));
    }

    private static final String PREFIX = Main.getConfigManager().getToml().getString("bot.prefix");

    public static boolean octogoneActive = false;

    public static void create(MessageCreateEvent event) {
        if (event.getMessageContent().startsWith(PREFIX)) {
            String[] args = event.getMessageContent().split(" ");
            String commandName = args[0].substring(PREFIX.length());
            args = args.length == 1 ? new String[0] : Arrays.copyOfRange(args, 1, args.length);

            String[] finalArgs = args;
            registry.getByAlias(commandName).ifPresent((cmd) -> {
                cmd.getExecutor().run(event, cmd, finalArgs);
            });
        }
        /*if (Objects.equals(event.getMessageAuthor().getDiscriminatedName(), "Learning#4864")) {
            event.addReactionsToMessage(EmojiParser.parseToUnicode(":white_check_mark:")).join();
        } else if (Objects.equals(event.getMessageAuthor().getDiscriminatedName(), //"")) {
            event.addReactionsToMessage(EmojiParser.parseToUnicode("Flex:789904635443544075")).join();
        }*/
    }

}
