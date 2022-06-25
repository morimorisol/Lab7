package Server.commands;

import Common.requestSystem.responses.CommandResponse;
import Server.CollectionManager;
import Server.databaseHandlers.DatabaseWorker;

public class InfoCommand extends CommandAbstract {

    public InfoCommand(DatabaseWorker databaseWorker) {
        super("info", databaseWorker);
    }

    @Override
    public CommandResponse execute(CollectionManager manager) {
        return new CommandResponse(manager.showInfo());
    }
}
