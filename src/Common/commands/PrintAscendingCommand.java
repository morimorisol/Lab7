package Common.commands;


import Common.entities.CollectionManager;
import Common.entities.Dragon;
import Common.handlers.TextFormatter;
import Common.requestSystem.Response;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PrintAscendingCommand extends CommandAbstract {

    public PrintAscendingCommand() {
        super("print_ascending", "Вывести драконов коллекции от младшего к старшему", 0);
    }

    @Override
    public Response execute(CollectionManager manager) {
        List<Dragon> dragons = new ArrayList<>(manager.getDragons());
        Collections.sort(dragons);
        return new Response(dragons, TextFormatter.colorInfoMessage("List of dragons compared of age: "));
    }
}
