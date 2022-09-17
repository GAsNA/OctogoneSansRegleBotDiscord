package org.gasna.learningBDiscord.commands;

import com.vdurmont.emoji.EmojiParser;
import org.gasna.learningBDiscord.utils.Embed;
import org.gasna.learningBDiscord.utils.commands.Command;
import org.gasna.learningBDiscord.utils.commands.CommandExecutor;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.*;

import java.util.Arrays;
import java.util.Objects;

import static org.gasna.learningBDiscord.utils.SendMessage.send_message;

public class CommandOctogone implements CommandExecutor {

    public static long p1 = 0, p2 = 0;
    public static long channel = 0;
    public static long mes1 = 0, mes2 = 0;

    static final private long bot = 983086177282318406L;

    static boolean is_id(String s) {
        return s.length() == 21 && s.charAt(0) == '<' && s.charAt(1) == '@' && s.charAt(s.length() - 1) == '>';
    }
    static long get_id_players(String s) {
        return Long.parseLong(s.substring(2, s.length() - 1));
    }

    static boolean pre_check(MessageCreateEvent event) {
        if (p1 == 0 && p2 == 0) {
            send_message(event, "There is no current game.");
            return true;
        }
        else if (event.getMessageAuthor().getId() != p1 && event.getMessageAuthor().getId() != p2) {
            send_message(event, "You are not an actual player.");
            return true;
        }
        return false;
    }

    static void reinit(MessageCreateEvent event) {
        p1 = 0;
        p2 = 0;
        channel = 0;
        mes1 = 0;
        mes2 = 0;
        send_message(event, "Your game is ending...");
    }

    @Override
    public void run(MessageCreateEvent event, Command command, String[] args) {
        if (args.length != 1)
            send_message(event, "Write ``" + PREFIX + "octo help`` for more informations.");
        else if (Objects.equals(args[0], "help") || Objects.equals(args[0], "h")) {
            EmbedBuilder embed = Embed.get_embed_help("Help for Octogone Sans Regles", "Octogone Sans Regles game, here is the commands.", "Have fun!");
            embed.addInlineField("How to play", "When a game is lauched, you have 5 minutes to send an image with your incredible octagon on this channel with the ``" + PREFIX + "octo image``, then the others will have 2 minutes for reacting to them with " + EmojiParser.parseToUnicode(":white_check_mark:") + ". The more you get, the more you are likely to win!")
                    .addInlineField("Begin the game", "If no game are engaged, indicate your ennemy by tagging them. For example: ``" + PREFIX + "octo @player``")
                    .addInlineField("Stop the game", "To stop the current game, write a 'e' or 'end' behind the octo command. For example: ``" + PREFIX + "octo e`` or ``" + PREFIX + "octo end``");
            send_message(event, embed);
        } else if (Objects.equals(args[0], "end") || Objects.equals(args[0], "e")) {
            if (pre_check(event))
                return;
            reinit(event);
        } else if (Objects.equals(args[0], "image")) {
            if (pre_check(event))
                return;
            if (event.getMessageAuthor().getId() == p1)
                mes1 = event.getMessage().getId();
            else
                mes2 = event.getMessage().getId();
        } else {
            if (!is_id(args[0])) {
                send_message(event, "You need to enter an ID.");
                return;
            }
            if (p1 != 0 && p2 != 0) {
                send_message(event, "There is already a game in progress with <@" + p1 + "> and <@" + p2 + ">.");
            }
            long ac_p1 = event.getMessage().getAuthor().getId();
            long ac_p2 = get_id_players(args[0]);
            if (ac_p2 == bot) {
                send_message(event, "I can't play with you.");
                return;
            }
            p1 = ac_p1;
            p2 = ac_p2;
            channel = event.getChannel().getId();
            send_message(event, "Your game can begin!\nSend your images <@" + p1 + "> and <@" + p2 + ">. Only your first image will be taken into account.");
            //String id = String.valueOf(idl);
            //User user = event.getChannel().
            //event.getChannel().canManageMessages()
        }
    }
}
