package Common.commands;

import Common.entities.CollectionManager;
import Common.entities.Dragon;
import Common.handlers.TextFormatter;
import Common.requestSystem.Response;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PrintDescendingCommand extends CommandAbstract {

    public PrintDescendingCommand() {
        super("print_descending", "Вывести всех драконов от старшего к младшему", 0);
    }

    @Override
    public Response execute(CollectionManager manager) {
        List<Dragon> dragons = new ArrayList<>(manager.getDragons());
        dragons.sort(Collections.reverseOrder());
        return new Response(dragons, TextFormatter.colorInfoMessage("List of dragons compared of age (reverse): "));
    }
}
