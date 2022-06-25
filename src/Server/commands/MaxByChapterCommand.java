package Server.commands;

import Common.TextFormatter;
import Common.entities.SpaceMarine;
import Common.requestSystem.responses.CommandResponse;
import Server.CollectionManager;
import Server.databaseHandlers.DatabaseWorker;

import java.util.ArrayList;
import java.util.List;

public class MaxByChapterCommand extends CommandAbstract {

    public MaxByChapterCommand(DatabaseWorker databaseWorker) {
        super("max_by_cave", databaseWorker);
    }

    @Override
    public CommandResponse execute(CollectionManager manager) {
        SpaceMarine SpaceMarine = manager.getMaxByChapter(getDatabaseWorker().getUsername());
        List<SpaceMarine> SpaceMarines = new ArrayList<>();
        SpaceMarines.add(SpaceMarine);
        return new CommandResponse(SpaceMarines, TextFormatter.colorMessage("Info about SpaceMarine with deepest cave: "));
    }
}
