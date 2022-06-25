package Server.commands;
import Common.TextFormatter;
import Common.entities.SpaceMarine;
import Common.requestSystem.responses.CommandResponse;
import Server.CollectionManager;
import Server.databaseHandlers.DatabaseWorker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PrintDescendingCommand extends CommandAbstract {

    public PrintDescendingCommand(DatabaseWorker databaseWorker) {
        super("print_descending", databaseWorker);
    }

    @Override
    public CommandResponse execute(CollectionManager manager) {
        List<SpaceMarine> dragons = new ArrayList<>(manager.getSpaceMarines());
        dragons.sort(Collections.reverseOrder());
        return new CommandResponse(dragons, TextFormatter.colorInfoMessage("List of dragons compared of age (reverse): "));
    }
}
