package Common.commands;

import Common.entities.CollectionManager;
import Common.entities.SpaceMarine;
import Common.handlers.TextFormatter;
import Common.requestSystem.Response;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PrintDescending extends CommandAbstract {

    public PrintDescending() {
        super("print_descending", "Вывести всех драконов от более здорового к менее", 0);
    }

    @Override
    public Response execute(CollectionManager manager) {
        List<SpaceMarine> spaceMarines = new ArrayList<>(manager.getSpaceMarines());
        spaceMarines.sort(Collections.reverseOrder());
        return new Response(spaceMarines, TextFormatter.colorInfoMessage("Список кораблей по здоровью (в обратном порядке): "));
    }
}
