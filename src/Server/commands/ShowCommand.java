package Server.commands;

import Common.entities.SpaceMarine;
import Common.requestSystem.responses.CommandResponse;
import Server.CollectionManager;
import Server.databaseHandlers.DatabaseWorker;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ShowCommand extends CommandAbstract {

    public ShowCommand(DatabaseWorker databaseWorker) {
        super("show", databaseWorker);
    }

    @Override
    public CommandResponse execute(CollectionManager manager) {
        List<SpaceMarine> sortedList = new ArrayList<>(manager.getSpaceMarines());
        sortedList = sortedList.stream().sorted(SpaceMarine::compareByName).collect(Collectors.toList());
        return new CommandResponse(sortedList, "List of dragons: ");
    }
}
