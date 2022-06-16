package Common.commands;


import Common.entities.CollectionManager;
import Common.entities.SpaceMarine;
import Common.handlers.TextFormatter;
import Common.requestSystem.Response;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PrintAscending extends CommandAbstract {

    public PrintAscending() {
        super("print_ascending", "Вывести корабли коллекции от менее здоровому к более", 0);
    }

    @Override
    public Response execute(CollectionManager manager) {
        List<SpaceMarine> spaceMarines = new ArrayList<>(manager.getSpaceMarines());
        Collections.sort(spaceMarines);
        return new Response(spaceMarines, TextFormatter.colorInfoMessage("Список кораблей по здоровью: "));
    }
}
