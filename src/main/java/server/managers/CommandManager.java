package server.managers;

import java.util.HashMap;

import lib.managers.OutputManager;
import server.commands.*;
import server.utility.*;
/**Менеджер для запуска команд*/
public class CommandManager {
    private CollectionManager collectionManager;
    private ServerSendingManager serverSendingManager;
    private UserManager userManager;
    private final HashMap<String, Command> commandMap = new HashMap<>();
    private SQLManager sqlManager;

    public CommandManager(CollectionManager collectionManager, ServerSendingManager serverSendingManager, UserManager userManager, SQLManager sqlManager) {
        this.serverSendingManager = serverSendingManager;
        this.collectionManager = collectionManager;
        this.userManager = userManager;
        this.sqlManager = sqlManager;
    }

    /**
     * добавление команд
     */
    /*
     * @return - commandMap - HashMap c командами
     */
    public void addCommands(){
        commandMap.put("help", new Help("help", "вывести справку по доступным командам", commandMap, serverSendingManager, userManager));
        commandMap.put("add", new Add("add", "добавить новый элемент в коллекцию", collectionManager, serverSendingManager, userManager));
        commandMap.put("info", new Info("info", "вывести в стандартный поток вывода информацию о коллекции", collectionManager, serverSendingManager, userManager));
        commandMap.put("clear", new Clear("clear", "очистить коллекцию", collectionManager, serverSendingManager, userManager));
        commandMap.put("group_counting_by_health", new CountingByHealth("group_counting_by_health", "сгруппировать элементы коллекции по значению поля health; вывести количество элементов в каждой группе", collectionManager, serverSendingManager, userManager));
        commandMap.put("add_if_min", new AddIfMin("add_if_min", "добавить новый элемент в коллекцию, если его значение меньше чем у наименьшего элемента этой коллекции", collectionManager, serverSendingManager, userManager));
        commandMap.put("print_ascending", new PrintAscending("print_ascending", "вывести элементы коллекции в порядке возрастания", collectionManager, serverSendingManager, userManager));
        commandMap.put("print_descending", new PrintDescending("print_descending", "вывести элементы коллекции в порядке убывания", collectionManager, serverSendingManager, userManager));
        commandMap.put("remove_by_id", new RemoveById("remove_by_id {id}", "удалить элемент из коллекции по его id", collectionManager, serverSendingManager, userManager, sqlManager));
        commandMap.put("remove_lower", new RemoveLower("remove_lower {id}", "удалить из коллекции элементы меньшиe чем заданный", collectionManager, serverSendingManager, userManager));
        commandMap.put("exit", new Exit("exit", "завершить программу", serverSendingManager, collectionManager, sqlManager, userManager));
        commandMap.put("show", new Show("show", "вывести содержимое коллекции", collectionManager, serverSendingManager, userManager));
        commandMap.put("update", new Update("update {id}", "обновить значение элемента коллекции id которого равен заданному", collectionManager, serverSendingManager, userManager));
        commandMap.put("history", new History("history", "вывести последние 14 команд", collectionManager, serverSendingManager, userManager));
        commandMap.put("login", new Login("login", "Войти в систему", userManager, serverSendingManager));
        commandMap.put("register", new Register("register", "зарегестрироваться в системе", userManager, serverSendingManager));
    }
    public HashMap<String, Command> getCommandMap() {
        return commandMap;
    }
}
