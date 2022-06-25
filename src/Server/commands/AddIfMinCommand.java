package Server.commands;

import Common.TextFormatter;
import Common.entities.SpaceMarine;
import Common.requestSystem.responses.CommandResponse;
import Server.CollectionManager;
import Server.databaseHandlers.DatabaseWorker;

public class AddIfMinCommand extends CommandAbstract {

    private final SpaceMarine spaceMarine;

    public AddIfMinCommand(SpaceMarine spaceMarine, DatabaseWorker databaseWorker) {
        super("add_if_min", databaseWorker);
        this.spaceMarine = spaceMarine;
    }

    @Override
    public CommandResponse execute(CollectionManager manager) {
        long minHealth = manager.getMin().getHealth();
        if (spaceMarine.getHealth() < minHealth && getDatabaseWorker().addSpaceMarine(spaceMarine)) {
            spaceMarine.setAuthorName(getDatabaseWorker().getUsername());
            manager.addSpaceMarine(spaceMarine);
            return new CommandResponse(TextFormatter.colorInfoMessage("SpaceMarine successfully added"));
        } else {
            return new CommandResponse(TextFormatter.colorInfoMessage("В коллекции есть дракон помладше!"));
        }
    }
}
