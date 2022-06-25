package Client.commandDispatcher;

import Common.TextFormatter;
import Common.entities.SpaceMarine;
import Common.requestSystem.requests.*;
import javafx.util.Pair;

import java.util.List;

public class CommandFactory {

    private final ArgumentsListener listener = new ArgumentsListener();
    private final Pair<String, String> loginData;

    public CommandFactory(Pair<String, String> loginData) {
        this.loginData = loginData;
    }

    public CommandRequest createCommand(String name, List<String> args) throws Exception {
        SpaceMarine SpaceMarine;
        try {
            switch (name) {
                case "add":
                case "add_if_max":
                case "add_if_min":
                    if (args.size() != 3) {
                        return null;
                    }
                    SpaceMarine = listener.inputSpaceMarine(args.get(0), Long.parseLong(args.get(1)), args.get(2));
                    if (SpaceMarine == null) {
                        throw new IllegalArgumentException("Аргументы команды некорректны");
                    }
                    return new CommandRequestWithSpaceMarine(name, SpaceMarine, loginData);
                case "exit":
                case "help":
                case "history":
                case "clear":
                case "info":
                case "print_ascending":
                case "print_descending":
                case "max_by_cave":
                case "show":
                    //todo реализовать exit
                    if (args.size() != 0) {
                        return null;
                    }
                    return new CommandRequestWithoutArgs(name, loginData);
                case "remove_by_id":
                    if (args.size() != 1) {
                        return null;
                    }
                    long id = Long.parseLong(args.get(0));
                    return new CommandRequestWithId(name, id, loginData);
                case "update_by_id":
                    if (args.size() != 1) {
                        return null;
                    }
                    long idOfSpaceMarine = Long.parseLong(args.get(0));
                    SpaceMarine = listener.inputSpaceMarineWithPrimitives();
                    return new CommandRequestWithSpaceMarineAndId(name, SpaceMarine, idOfSpaceMarine, loginData);
                default:
                    throw new Exception("Command with current name not found");
            }
        } catch (IndexOutOfBoundsException e) {
            TextFormatter.printErrorMessage("Incorrect count of args");
            return null;
        }
    }
}
