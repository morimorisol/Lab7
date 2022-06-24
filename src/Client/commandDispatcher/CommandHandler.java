package Client.commandDispatcher;

import Server.commands.CommandAbstract;

import java.io.IOException;
import java.util.List;

/**
 * Класс, инициализирующий команду по полученной строке
 */
public class CommandHandler {

    private CommandHandler() {
        //never used
    }

    public static CommandAbstract initCommand(String line) throws IOException {
        List<String> commandWithArgs = LineSplitter.smartSplit(line);
        String commandName = CommandHandler.getCommandName(commandWithArgs);
        List<String> commandArgs = CommandHandler.getCommandArguments(commandWithArgs);
        CommandFactory factory = new CommandFactory();
        return factory.createCommand(commandName, commandArgs);
    }

    public static List<String> getCommandArguments(List<String> line) {
        line.remove(0);
        return line;
    }

    public static String getCommandName(List<String> line) {
        return line.get(0);
    }
}
