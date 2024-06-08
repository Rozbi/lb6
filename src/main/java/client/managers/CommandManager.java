package client.managers;

import java.util.HashMap;


/**Менеджер для запуска команд*/
public class CommandManager {
    private final HashMap<String, Boolean> commandMap = new HashMap<>();

    /**добавление команд*/
    /*
     * @return - commandMap - HashMap c командами
     */
    public HashMap<String, Boolean> addCommands() {
        commandMap.put("help", false);
        commandMap.put("add", false);
        commandMap.put("info", false);
        commandMap.put("clear", false);
        commandMap.put("group_counting_by_health", false);
        commandMap.put("add_if_min", false);
        commandMap.put("execute_script", true);
        commandMap.put("print_ascending", false);
        commandMap.put("print_descending", false);
        commandMap.put("remove_by_id", true);
        commandMap.put("remove_lower", true);
        commandMap.put("exit", false);
        commandMap.put("show", false);
        commandMap.put("update", true);
        commandMap.put("history", false);
        commandMap.put("save", false);
        return commandMap;
    }
    public HashMap<String, Boolean> getCommandMap() {
        return commandMap;
    }
}
