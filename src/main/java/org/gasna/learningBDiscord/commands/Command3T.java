package org.gasna.learningBDiscord.commands;

import org.gasna.learningBDiscord.utils.Embed;
import org.gasna.learningBDiscord.utils.commands.Command;
import org.gasna.learningBDiscord.utils.commands.CommandExecutor;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

import java.util.Arrays;
import java.util.Objects;

import static org.gasna.learningBDiscord.utils.SendMessage.send_message;

public class Command3T implements CommandExecutor {

    static private long p1 = 0, p2 = 0;
    static final private long bot = 983086177282318406L;
    static private int np = 1;
    static private final int[][] map = {
            {0, 0, 0},
            {0, 0, 0},
            {0, 0, 0}
    };

    static boolean is_id(String s) {
        return s.length() == 21 && s.charAt(0) == '<' && s.charAt(1) == '@' && s.charAt(s.length() - 1) == '>';
    }
    static long get_id_players(String s) {
        return Long.parseLong(s.substring(2, s.length() - 1));
    }

    static boolean is_emplacement(String s) {
        return s.length() == 2 && Character.isDigit(s.charAt(0)) && Character.isDigit(s.charAt(1));
    }

    static boolean all_emplacement_marked(MessageCreateEvent event) {
        for (int[] ints : map)
            for (int anInt : ints)
                if (anInt == 0)
                    return false;
        send_message(event, "There is an equality.");
        return true;
    }

    static boolean check_lines() {
        for (int[] ints : map) {
            int c = ints[0];
            if (c == 0)
                continue;
            int ok = 1;
            for (int anInt : ints) {
                if (c != anInt) {
                    ok = 0;
                    break;
                }
            }
            if (ok == 1)
                return true;
        }
        return false;
    }

    static boolean check_columns() {
        for (int i = 0; i < map.length; i++) {
            int c = map[0][i];
            if (c == 0)
                continue;
            int ok = 1;
            for (int[] ints : map) {
                if (c != ints[i]) {
                    ok = 0;
                    break;
                }
            }
            if (ok == 1)
                return true;
        }
        return false;
    }

    static boolean check_diags() {
        int ok = 0;
        int c = map[0][0];
        for (int i = 0; i < map.length; i++) {
            if (c == 0)
                break;
            ok = 1;
            if (map[i][i] != c) {
                ok = 0;
                break;
            }
        }
        if (ok == 1)
            return true;
        c = map[0][map.length - 1];
        for (int i = map.length - 1; i >= 0; i--) {
            if (c == 0)
                break;
            ok = 1;
            if(map[map.length - 1 - i][i] != c) {
                ok = 0;
                break;
            }
        }
        return ok == 1;
    }
    static boolean there_is_a_win(MessageCreateEvent event) {
        if (check_lines() || check_columns() || check_diags()) {
            send_message(event, "Player <@" + (np == 1 ? p2 : p1) + "> wins!");
            return true;
        }
        return false;
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

    static StringBuilder display_map() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == 1)
                    s.append("x   ");
                else if (map[i][j] == 2)
                    s.append("o   ");
                else
                    s.append("n   ");
                if (j < map[i].length - 1)
                    s.append("|   ");
            }
            if (i < map.length - 1)
                s.append("\n---------------\n");
        }
        return s;
    }

    static void reinit(MessageCreateEvent event) {
        p1 = 0;
        p2 = 0;
        np = 1;
        for (int[] ints : map) {
            Arrays.fill(ints, 0);
        }
        send_message(event, "Your game is ending...");
    }

    @Override
    public void run(MessageCreateEvent event, Command command, String[] args) {
        if (args.length == 0 || args.length > 2)
            send_message(event, "Write ``" + PREFIX + "t3 help`` for more informations.");
        else if (args.length == 2) {
            if (!is_id(args[0]) || !is_id(args[1])) {
                send_message(event, "You need to enter two ids.");
                return;
            }
            if (Objects.equals(args[0], args[1])) {
                send_message(event, "You need two different players.");
                return;
            }
            long ac_p1 = get_id_players(args[0]), ac_p2 = get_id_players(args[1]);
            if ((ac_p1 == p1 || ac_p2 == p1) && (ac_p1 == p2 || ac_p2 == p2)) {
                send_message(event, "Your game is already in progress.\nPlease choose an action (``" + PREFIX + "t3 help`` for more informations).");
                return;
            }
            if (p1 != 0 && p2 != 0) {
                send_message(event, "There is already a game in progress with <@" + p1 + "> and <@" + p2 + ">.");
                return;
            }
            if (ac_p1 == bot || ac_p2 == bot) {
                send_message(event, "I can't play with you.");
                return;
            }
            p1 = ac_p1;
            p2 = ac_p2;
            send_message(event, "Your game can begin !");
        } else if (Objects.equals(args[0], "help") || Objects.equals(args[0], "h")) {
            EmbedBuilder embed = Embed.get_embed_help("Help for tic-tac-toe", "Tic-tac-toe game, here is the commands.", "Have fun!");
            embed.addInlineField("How to play", "Call the tic-tac-toe command and indicate your next move by writing the row number, then the column number. For example: ``" + PREFIX + "t3 13`` for the first row and the third column.")
                 .addInlineField("Display the map", "To display the map of the current game, write a 'm' or 'map' behind the 3t command. For example: ``" + PREFIX + "t3 m`` or ``" + PREFIX + "t3 map``")
                 .addInlineField("Know the next player", "To know who is the next player, write 'np'. For example: ``" + PREFIX + "t3 np``")
                 .addInlineField("Begin the game", "If no game are engaged, indicate the two players by tagging them. For example: ``" + PREFIX + "t3 @p1 @p2``")
                 .addInlineField("Stop the game", "To stop the current game, write a 'e' or 'end' behind the 3t command. For example: ``" + PREFIX + "3t e`` or ``" + PREFIX + "3t end``");
            send_message(event, embed);
        } else if (Objects.equals(args[0], "end") || Objects.equals(args[0], "e")) {
            if (pre_check(event))
                return;
            reinit(event);
        } else if (Objects.equals(args[0], "np")) {
            if (pre_check(event))
                return;
            send_message(event, "The next player is <@" + (np == 1 ? p1 : p2) + ">.");
        } else if (Objects.equals(args[0], "m") || Objects.equals(args[0], "map")) {
            if (pre_check(event))
                return;
            send_message(event, String.valueOf(display_map()));
        } else if (is_emplacement(args[0])) {
            if (pre_check(event))
                return;
            if (event.getMessageAuthor().getId() != (np == 1 ? p1 : p2)) {
               send_message(event, "It is not your turn.");
                return;
            }
            int r = args[0].charAt(0) - '0' - 1, c = args[0].charAt(1) - '0' - 1;
            if (r < 0 || r > 2 || c < 0 || c > 2) {
                send_message(event, "Send a actual position.");
                return;
            }
            if (map[r][c] != 0) {
                send_message(event, "This emplacement is already taken.");
                return;
            }
            map[r][c] = np;
            np = np == 1 ? 2 : 1;
            send_message(event, "Your emplacement is marked.\n" + display_map());
            if (there_is_a_win(event) || all_emplacement_marked(event))
                reinit(event);
        } else
            send_message(event, "Unknown command. Please write ``" + PREFIX + "t3 help`` for more informations.");
    }
}
