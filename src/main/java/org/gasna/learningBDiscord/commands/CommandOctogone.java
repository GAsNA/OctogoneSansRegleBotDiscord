package org.gasna.learningBDiscord.commands;

import com.vdurmont.emoji.EmojiParser;
import org.gasna.learningBDiscord.utils.Embed;
import org.gasna.learningBDiscord.utils.commands.Command;
import org.gasna.learningBDiscord.utils.commands.CommandExecutor;
import org.gasna.learningBDiscord.utils.commands.MessageManager;
import org.gasna.learningBDiscord.utils.commands.MyThread;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageAttachment;
import org.javacord.api.entity.message.Reaction;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.gasna.learningBDiscord.utils.SendMessage.send_message;

public class CommandOctogone implements CommandExecutor {

    private static long p1 = 0, p2 = 0;
    private static TextChannel channel = null;
    private static Message mes1 = null, mes2 = null;

    static final private long bot = 1020053493484097618L;

    static boolean is_id(String s) {
        return s.length() >= 21 && s.charAt(0) == '<' && s.charAt(1) == '@' && s.charAt(s.length() - 1) == '>';
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
        channel = null;
        mes1 = null;
        mes2 = null;
        send_message(event, "Your game is ending...");
    }

    static void reinit(TextChannel event) {
        p1 = 0;
        p2 = 0;
        channel = null;
        mes1 = null;
        mes2 = null;
        MessageManager.octogoneActive = false;
        send_message(event, "Your game is ending...");
    }

    static public void finished() {
        if (mes1 == null && mes2 == null)
            send_message(channel, "Nobody played! What a shame...");
        else if (mes1 == null)
            send_message(channel, "<@" + p2 + "> wins! (They are the only one who participated)\nThank you for playing.");
        else if (mes2 == null)
            send_message(channel, "<@" + p1 + "> wins! (They are the only one who participated)\nThank you for playing.");
        else {
            int mes1_check = 0;
            if (mes1.getReactionByEmoji(EmojiParser.parseToUnicode(":white_check_mark:")).isPresent())
                mes1_check = mes1.getReactionByEmoji(EmojiParser.parseToUnicode(":white_check_mark:")).get().getCount();
            int mes2_check = 0;
            if (mes2.getReactionByEmoji(EmojiParser.parseToUnicode(":white_check_mark:")).isPresent())
                mes2_check = mes1.getReactionByEmoji(EmojiParser.parseToUnicode(":white_check_mark:")).get().getCount();
            if (mes1_check == mes2_check)
                send_message(channel, "It's a perfect equality!\nThank you for playing.");
            else
                send_message(channel, "<@" + (mes1_check > mes2_check ? p1 : p2) + "> wins!\nThank you for playing.");
        }
        reinit(channel);
    }

    @Override
    public void run(MessageCreateEvent event, Command command, String[] args) {
        if (args.length != 1)
            send_message(event, "Write ``" + PREFIX + "octo help`` for more informations.");
        else if (Objects.equals(args[0], "help") || Objects.equals(args[0], "h")) {
            EmbedBuilder embed = Embed.get_embed_help("Help for Octogone Sans Regles", "Octogone Sans Regles game, here is the commands.", "Have fun!");
            embed.addInlineField("How to play", "When a game is lauched, you have 5 minutes to send an image with your incredible octagon on this channel with the ``" + PREFIX + "octo image`` ; the others need to react to them in the same time with " + EmojiParser.parseToUnicode(":white_check_mark:") + ". The more you get, the more you are likely to win!")
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
            if (!MessageManager.octogoneActive) {
                send_message(event, "You can't post any image for now.");
                return;
            }
            if (event.getChannel().getId() != channel.getId()) {
                send_message(event, "You're not in the good channel.");
                return;
            }
            List<MessageAttachment> l = event.getMessage().getAttachments();
            if (l.size() != 1 || !l.get(0).isImage()) {
                send_message(event, "You need to add one unique image.");
                return;
            }
            if (event.getMessageAuthor().getId() == p1 && mes1 == null)
                mes1 = event.getMessage();
            else if (mes2 == null)
                mes2 = event.getMessage();
        } else {
            if (!is_id(args[0])) {
                send_message(event, "You need to enter an ID.");
                return;
            }
            if (p1 != 0 && p2 != 0) {
                send_message(event, "There is already a game in progress with <@" + p1 + "> and <@" + p2 + ">.");
                return;
            }
            long ac_p1 = event.getMessage().getAuthor().getId();
            long ac_p2 = get_id_players(args[0]);
            if (ac_p2 == bot) {
                send_message(event, "I can't play with you.");
                return;
            }
            if (ac_p2 == ac_p1) {
                send_message(event, "You can't play with yourself.");
                return;
            }
            p1 = ac_p1;
            p2 = ac_p2;
            channel = event.getChannel();
            send_message(event, "Your game can begin!\nSend your images <@" + p1 + "> and <@" + p2 + ">. Only your first image will be taken into account.");
            MyThread t = new MyThread("Thread");
            t.start();
        }
    }
}
