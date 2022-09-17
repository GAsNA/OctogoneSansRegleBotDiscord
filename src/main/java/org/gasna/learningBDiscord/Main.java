package org.gasna.learningBDiscord;

import org.gasna.learningBDiscord.utils.ConfigManager;
import org.gasna.learningBDiscord.utils.commands.MessageManager;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

import java.io.File;
import java.util.concurrent.ExecutionException;

public class Main {

    private static ConfigManager configManager;

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        configManager = new ConfigManager(new File(System.getProperty("user.dir"), "config.toml"));

        DiscordApi api = new DiscordApiBuilder()
                .setToken(configManager.getToml().getString("bot.token"))
                .login().join();

        api.addMessageCreateListener(MessageManager::create);
    }

    public static ConfigManager getConfigManager() {
        return configManager;
    }

}
