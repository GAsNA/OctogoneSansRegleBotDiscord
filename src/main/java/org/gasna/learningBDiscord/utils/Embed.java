package org.gasna.learningBDiscord.utils;

import org.javacord.api.entity.message.embed.EmbedBuilder;

import java.awt.*;

public class Embed {

    public static EmbedBuilder get_embed_help(String title, String description, String footer) {
        return new EmbedBuilder()
                    .setColor(Color.BLUE)
                    .setTitle(title)
                    .setDescription(description)
                    .setFooter(footer);
    }

}
