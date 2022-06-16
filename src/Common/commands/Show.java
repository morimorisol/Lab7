package Common.commands;

import Common.entities.CollectionManager;
import Common.entities.SpaceMarine;
import Common.requestSystem.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Show extends CommandAbstract {

    public Show() {
        super("show", "вывести на экран элементы коллекции в строковом представлении", 0);
    }

    @Override
    public Response execute(CollectionManager manager) {
        List<SpaceMarine> sortedList = new ArrayList<>(manager.getDragons());
        sortedList = sortedList.stream().sorted(SpaceMarine::compareByName).collect(Collectors.toList());
        return new Response(sortedList, "List of dragons: ");
    }
}
