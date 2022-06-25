package Server.commands;

import Common.TextFormatter;
import Common.entities.SpaceMarine;
import Common.requestSystem.responses.CommandResponse;
import Server.CollectionManager;
import Server.databaseHandlers.DatabaseWorker;

public class AddCommand extends CommandAbstract {

    private SpaceMarine spaceMarine;

    public AddCommand(SpaceMarine spaceMarine, DatabaseWorker databaseWorker) {
        super("add", databaseWorker);
        this.spaceMarine = spaceMarine;
    }

    @Override
    public CommandResponse execute(CollectionManager manager) {
        if (getDatabaseWorker().addSpaceMarine(spaceMarine)) {
            spaceMarine.setAuthorName(getDatabaseWorker().getUsername());
            manager.addSpaceMarine(spaceMarine);
            return new CommandResponse(TextFormatter.colorInfoMessage("SpaceMarine successfully added"));
        } else {
            return new CommandResponse(TextFormatter.colorErrorMessage("SQL problems, SpaceMarine didn't add"));
        }
    }

    public void setSpaceMarine(SpaceMarine SpaceMarine) {
        this.spaceMarine = spaceMarine;
    }
}
