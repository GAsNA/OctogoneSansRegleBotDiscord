package org.gasna.learningBDiscord.utils;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

public class SendMessage {

    public static void send_message(MessageCreateEvent event, String s) {
        event.getChannel().sendMessage(s);
    }

    public static void send_message(MessageCreateEvent event, EmbedBuilder embed) {
        event.getChannel().sendMessage(embed);
    }

    public static void send_message(TextChannel channel, String s) {
        channel.sendMessage(s);
    }

}
