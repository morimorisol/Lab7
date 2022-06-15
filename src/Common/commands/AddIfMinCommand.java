package Common.commands;

import Common.entities.CollectionManager;
import Common.entities.Dragon;
import Common.handlers.TextFormatter;
import Common.requestSystem.Response;

public class AddIfMinCommand extends CommandAbstract {

    private final Dragon dragon;

    public AddIfMinCommand(Dragon dragon) {
        super("add_if_min", "Добавить дракона в коллекцию, если его возраст меньше, чем у самого младшего в коллекции", Dragon.COUNT_OF_PRIMITIVE_ARGS);
        this.dragon = dragon;
    }

    @Override
    public Response execute(CollectionManager manager) {
        int minAge = manager.getMin().getAge();
        if (dragon.getAge() < minAge) {
            manager.addDragon(dragon);
            return new Response(TextFormatter.colorInfoMessage("Dragon successfully added"));
        } else {
            return new Response(TextFormatter.colorInfoMessage("В коллекции есть дракон помладше!"));
        }
    }
}
