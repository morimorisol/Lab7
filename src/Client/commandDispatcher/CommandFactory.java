package Client.commandDispatcher;

import Client.exceptions.CommandNotFoundException;
import Common.commands.*;
import Common.entities.SpaceMarine;
import Common.handlers.TextFormatter;

import java.util.List;

public class CommandFactory {

    private final ArgumentsListener listener = new ArgumentsListener();

    public CommandAbstract createCommand(String name, List<String> args) throws CommandNotFoundException {
        SpaceMarine spaceMarine;
        try {
            switch (name) {
                case "add":
                    if (args.size() != 3) {
                        return null;
                    }
                    spaceMarine = listener.inputDragon(args.get(0), args.get(1), args.get(2));
                    if (spaceMarine == null) {
                        throw new IllegalArgumentException("Аргументы команды некорректны");
                    }
                    return new Add(spaceMarine);
                case "add_if_max":
                    if (args.size() != 3) {
                        return null;
                    }
                    spaceMarine = listener.inputDragon(args.get(0), args.get(1), args.get(2));
                    if (spaceMarine == null) {
                        throw new IllegalArgumentException("Аргументы команды некорректны");
                    }
                    return new AddIfMax(spaceMarine);
                case "add_if_min":
                    if (args.size() != 3) {
                        return null;
                    }
                    spaceMarine = listener.inputDragon(args.get(0), args.get(1), args.get(2));
                    if (spaceMarine == null) {
                        throw new IllegalArgumentException("Аргументы команды некорректны");
                    }
                    return new AddIfMin(spaceMarine);
                case "clear":
                    if (args.size() != 0) {
                        return null;
                    }
                    return new Clear();
                case "exit":
                    if (args.size() != 0) {
                        return null;
                    }
                    return new Exit();
                case "help":
                    if (args.size() != 0) {
                        return null;
                    }
                    return new Help();
                case "history":
                    if (args.size() != 0) {
                        return null;
                    }
                    return new History();
                case "info":
                    if (args.size() != 0) {
                        return null;
                    }
                    return new Info();
                case "max_by_cave":
                    if (args.size() != 0) {
                        return null;
                    }
                    return new MaxByCave();
                case "print_ascending":
                    if (args.size() != 0) {
                        return null;
                    }
                    return new PrintAscending();
                case "print_descending":
                    if (args.size() != 0) {
                        return null;
                    }
                    return new PrintDescending();
                case "remove_by_id":
                    if (args.size() != 1) {
                        return null;
                    }
                    long id = Long.parseLong(args.get(0));
                    return new RemoveById(id);
                case "show":
                    if (args.size() != 0) {
                        return null;
                    }
                    return new Show();
                case "update_by_id":
                    if (args.size() != 1) {
                        return null;
                    }
                    long idOfDragon = Long.parseLong(args.get(0));
                    spaceMarine = listener.inputDragonWithPrimitives(astartesCategory);
                    return new UpdateById(idOfDragon, spaceMarine);
                default:
                    throw new CommandNotFoundException("Command with current name not found");
            }
        } catch (IndexOutOfBoundsException e) {
            TextFormatter.printErrorMessage("Incorrect count of args");
            return null;
        }
    }
}
