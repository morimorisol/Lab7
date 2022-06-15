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
        super("print_descending", "Вывести всех драконов от старшего к младшему", 0);
    }

    @Override
    public Response execute(CollectionManager manager) {
        List<SpaceMarine> dragons = new ArrayList<>(manager.getDragons());
        dragons.sort(Collections.reverseOrder());
        return new Response(dragons, TextFormatter.colorInfoMessage("List of dragons compared of age (reverse): "));
    }
}
