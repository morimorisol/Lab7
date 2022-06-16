package Common.commands;

import Common.entities.CollectionManager;
import Common.entities.SpaceMarine;
import Common.handlers.TextFormatter;
import Common.requestSystem.Response;

public class Add extends CommandAbstract {

    private SpaceMarine sm;

    public Add(SpaceMarine sm) {
        super("add", "Добавить элемент в коллекцию", SpaceMarine.COUNT_OF_PRIMITIVE_ARGS);
        this.sm = sm;
    }

    @Override
    public Response execute(CollectionManager manager) {
        manager.addSpaceMarines(sm);
        return new Response(TextFormatter.colorInfoMessage("Корабль успешно добавлен"));
    }

    public void setDragon(SpaceMarine sm) {
        this.sm = sm;
    }
}
