package server.commands;

import lib.utility.Message;
import server.managers.CollectionManager;
import lib.managers.OutputManager;
import server.managers.ServerSendingManager;

public class PrintAscending extends Command {
    private static String name;
    private static String description;
    private CollectionManager collectionManager;
    private ServerSendingManager sendingManager;

    public PrintAscending(String name, String description, CollectionManager collectionManager, ServerSendingManager sendingManager) {
        super("print_ascending", "вывести элементы коллекции в порядке возрастания");
        this.name = name;
        this.description = description;
        this.collectionManager = collectionManager;
        this.sendingManager = sendingManager;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public boolean execute(Message message) {
        try {
            sendingManager.sendMessage(new Message(message.getName(), String.valueOf(collectionManager.getCollection()), message.getAddress()));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
