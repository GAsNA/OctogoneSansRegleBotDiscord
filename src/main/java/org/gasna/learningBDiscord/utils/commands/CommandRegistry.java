package org.gasna.learningBDiscord.utils.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

public class CommandRegistry {

    private final ArrayList<Command> commands;

    public CommandRegistry() {
        this.commands = new ArrayList<Command>();
    }

    public void addCommand(Command cmd) {
        commands.add(cmd);
    }

    public void removeCommand(String id) {
        commands.removeIf((cmd)->cmd.getId().equalsIgnoreCase(id));
    }

    public Optional<Command> getByAlias(String alias) {
        for (Command command : commands) {
            if (Arrays.asList(command.getAliases()).contains(alias)) {
                return Optional.of(command);
            }
        }

        return Optional.empty();
    }
}
