package Server.commands;

import Common.TextFormatter;
import Common.requestSystem.responses.CommandResponse;
import Server.CollectionManager;
import Server.databaseHandlers.DatabaseWorker;
public class ClearCommand extends CommandAbstract {

    public ClearCommand(DatabaseWorker databaseWorker) {
        super("clear", databaseWorker);
    }

    @Override
    public CommandResponse execute(CollectionManager manager) {
        if (getDatabaseWorker().clear()) {
            manager.clear(getDatabaseWorker().getUsername());
            return new CommandResponse(TextFormatter.colorInfoMessage("Your collection successfully cleared"));
        }
        return new CommandResponse(TextFormatter.colorInfoMessage("Your collection is empty :("));
    }
}
