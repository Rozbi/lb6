package server.commands;

import lib.managers.OutputManager;
import lib.utility.Message;
import server.managers.CollectionManager;
import server.managers.ServerSendingManager;

public class Show extends Command {
    private CollectionManager collectionManager;
    private static String name;
    private static String description;
    private ServerSendingManager sendingManager;

    public Show(String name, String description, CollectionManager collectionManager, ServerSendingManager sendingManager) {
        super("show", "вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
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
            sendingManager.sendMessage(new Message(message.getName(), collectionManager.getCollection().toString(), message.getAddress()));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
