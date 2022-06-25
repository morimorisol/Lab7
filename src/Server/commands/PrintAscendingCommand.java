package Server.commands;

import Common.TextFormatter;
import Common.entities.SpaceMarine;
import Common.requestSystem.responses.CommandResponse;
import Server.CollectionManager;
import Server.databaseHandlers.DatabaseWorker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PrintAscendingCommand extends CommandAbstract {

    public PrintAscendingCommand(DatabaseWorker databaseWorker) {
        super("print_ascending", databaseWorker);
    }

    @Override
    public CommandResponse execute(CollectionManager manager) {
        List<SpaceMarine> dragons = new ArrayList<>(manager.getSpaceMarines());
        Collections.sort(dragons);
        return new CommandResponse(dragons, TextFormatter.colorInfoMessage("List of dragons compared of age: "));
    }
}
