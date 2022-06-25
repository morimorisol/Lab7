package Server.commands;

import Common.TextFormatter;
import Common.entities.SpaceMarine;
import Common.requestSystem.responses.CommandResponse;
import Server.CollectionManager;
import Server.databaseHandlers.DatabaseWorker;

public class UpdateByIdCommand extends CommandAbstract {

    private final long id;
    private final SpaceMarine spaceMarine;

    public UpdateByIdCommand(long id, SpaceMarine spaceMarine, DatabaseWorker databaseWorker) {
        super("update_by_id", databaseWorker);
        this.spaceMarine = spaceMarine;
        this.id = id;
    }

    @Override
    public CommandResponse execute(CollectionManager manager) {
        if (getDatabaseWorker().updateById(spaceMarine, id)) {
            for (SpaceMarine elem : manager.getSpaceMarines()) {
                if (elem.getId() == id) {
                    manager.removeById(id);
                    manager.addSpaceMarine(spaceMarine);
                    return new CommandResponse(TextFormatter.
                                colorInfoMessage("Info about dragon successfully updated"));
                }
            }
        } else {
            return new CommandResponse(TextFormatter.colorInfoMessage("ID not found"));
        }
        return null; //never used
    }
}
