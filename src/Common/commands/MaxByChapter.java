package Common.commands;

import Common.entities.CollectionManager;
import Common.entities.SpaceMarine;
import Common.handlers.TextFormatter;
import Common.requestSystem.Response;

import java.util.ArrayList;
import java.util.List;

public class MaxByChapter extends CommandAbstract {

    public MaxByChapter() {
        super("max_by_chapter", "Вывести любой корабль из коллекции, принадлежащий самой большой части", 0);
    }

    @Override
    public Response execute(CollectionManager manager) {
        SpaceMarine spaceMarine = manager.getMaxByChapter();
        List<SpaceMarine> spaceMarines = new ArrayList<>();
        spaceMarines.add(spaceMarine);
        return new Response(spaceMarines, TextFormatter.colorMessage("Информация о корабле, принадлежащему самой большой части : "));
    }
}
