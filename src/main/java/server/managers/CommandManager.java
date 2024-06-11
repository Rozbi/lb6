package server.managers;

import java.util.HashMap;

import lib.managers.OutputManager;
import server.commands.*;
import server.utility.*;
/**Менеджер для запуска команд*/
public class CommandManager {
    private CollectionManager collectionManager;
    private ServerSendingManager serverSendingManager;
    private final HashMap<String, Command> commandMap = new HashMap<>();

    public CommandManager(CollectionManager collectionManager, ServerSendingManager serverSendingManager) {
        this.serverSendingManager = serverSendingManager;
        this.collectionManager = collectionManager;
    }

    /**
     * добавление команд
     */
    /*
     * @return - commandMap - HashMap c командами
     */
    public void addCommands(){
        commandMap.put("help", new Help("help", "вывести справку по доступным командам", commandMap, serverSendingManager));
        commandMap.put("add", new Add("add", "добавить новый элемент в коллекцию", collectionManager, serverSendingManager));
        commandMap.put("info", new Info("info", "вывести в стандартный поток вывода информацию о коллекции", collectionManager, serverSendingManager));
        commandMap.put("clear", new Clear("clear", "очистить коллекцию", collectionManager, serverSendingManager));
        commandMap.put("group_counting_by_health", new CountingByHealth("group_counting_by_health", "сгруппировать элементы коллекции по значению поля health; вывести количество элементов в каждой группе", collectionManager, serverSendingManager));
        commandMap.put("add_if_min", new AddIfMin("add_if_min", "добавить новый элемент в коллекцию, если его значение меньше чем у наименьшего элемента этой коллекции", collectionManager, serverSendingManager));
        commandMap.put("print_ascending", new PrintAscending("print_ascending", "вывести элементы коллекции в порядке возрастания", collectionManager, serverSendingManager));
        commandMap.put("print_descending", new PrintDescending("print_descending", "вывести элементы коллекции в порядке убывания", collectionManager, serverSendingManager));
        commandMap.put("remove_by_id", new RemoveById("remove_by_id {id}", "удалить элемент из коллекции по его id", collectionManager, serverSendingManager));
        commandMap.put("remove_lower", new RemoveLower("remove_lower {id}", "удалить из коллекции элементы меньшиe чем заданный", collectionManager, serverSendingManager));
        commandMap.put("exit", new Exit("exit", "завершить программу", serverSendingManager, collectionManager));
        commandMap.put("show", new Show("show", "вывести содержимое коллекции", collectionManager, serverSendingManager));
        commandMap.put("update", new Update("update {id}", "обновить значение элемента коллекции id которого равен заданному", collectionManager, serverSendingManager));
        commandMap.put("history", new History("history", "вывести последние 14 команд", collectionManager, serverSendingManager));
    }
    public HashMap<String, Command> getCommandMap() {
        return commandMap;
    }
}
