package Common.commands;


import Common.entities.CollectionManager;
import Common.entities.SpaceMarine;
import Common.handlers.TextFormatter;
import Common.requestSystem.Response;

public class Add extends CommandAbstract {

    private SpaceMarine dragon;

    public Add(SpaceMarine dragon) {
        super("add", "Добавить элемент в коллекцию", SpaceMarine.COUNT_OF_PRIMITIVE_ARGS);
        this.dragon = dragon;
    }

    @Override
    public Response execute(CollectionManager manager) {
        manager.addDragon(dragon);
        return new Response(TextFormatter.colorInfoMessage("Dragon successfully added"));
    }

    public void setDragon(SpaceMarine dragon) {
        this.dragon = dragon;
    }
}
