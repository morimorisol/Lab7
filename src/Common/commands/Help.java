package Common.commands;

import Common.entities.CollectionManager;
import Common.requestSystem.Response;

import java.util.HashMap;
import java.util.Map;

public class Help extends CommandAbstract {

    private static final Map<String, String> COMMANDS = new HashMap<>();

    public Help() {
        super("help", "Вывести список доступных команд", 0);
    }

    @Override
    public Response execute(CollectionManager manager) {
        fillCommandsForBuildList();
        return new Response(getCommands());
    }

    private static void fillCommandsForBuildList() {
        COMMANDS.put("add [name, health, achievements]", "add new marine");
        COMMANDS.put("add_if_max [name, health, achievements]", "add new marine if him age is max");
        COMMANDS.put("add_if_min [name, health, achievements]", "add new marine if him age is min");
        COMMANDS.put("clear", "clear collection");
        COMMANDS.put("help", "get list of available commands");
        COMMANDS.put("history", "get 11 last commands");
        COMMANDS.put("info", "get info about collection");
        COMMANDS.put("max_by_chapter", "get marine with the best chapter");
        COMMANDS.put("print_ascending", "show marines from the healthiest to the less healthy");
        COMMANDS.put("print_descending", "show marines from less healthy to the healthiest");
        COMMANDS.put("remove_by_id [id]", "remove marine with given ID");
        COMMANDS.put("show", "show all marines in collection");
        COMMANDS.put("update_by_id [id]", "update info about marine with given ID");
    }

    private static String getCommands() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> pair : COMMANDS.entrySet()) {
            String key = pair.getKey();
            String value = pair.getValue();
            sb.append(key).append(" - ").append(value).append("\n");
        }
        return sb.toString();
    }
}
