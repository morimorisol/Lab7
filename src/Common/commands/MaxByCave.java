package Common.commands;

import Common.entities.CollectionManager;
import Common.entities.SpaceMarine;
import Common.handlers.TextFormatter;
import Common.requestSystem.Response;

import java.util.ArrayList;
import java.util.List;

public class MaxByCave extends CommandAbstract {

    public MaxByCave() {
        super("max_by_cave", "Вывести любого дракона из коллекции, глубина пещеры которого является максимальной", 0);
    }

    @Override
    public Response execute(CollectionManager manager) {
        SpaceMarine dragon = manager.getMaxByCave();
        List<SpaceMarine> dragons = new ArrayList<>();
        dragons.add(dragon);
        return new Response(dragons, TextFormatter.colorMessage("Info about dragon with deepest cave: "));
    }
}
