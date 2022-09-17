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
        //Optional<User> u = api.getCachedUserByDiscriminatedName("Gabrielle");
        //CompletableFuture<User> u = api.getUserById(474355372799557643L);
        //System.out.println(u.get());
        /*if (u.isPresent()) {
            System.out.printf("OK");
            System.out.printf(u.get().getDiscriminatedName());
        }*/
    }

    public static ConfigManager getConfigManager() {
        return configManager;
    }

}
