package server.commands;

import client.managers.SendingManager;
import lib.utility.Message;
import server.managers.CollectionManager;
import lib.managers.InputManager;
import lib.managers.OutputManager;
import lib.spaceMarine.SpaceMarine;
import client.utility.Ask;
import server.managers.ServerSendingManager;

import java.util.PriorityQueue;

public class RemoveLower extends Command {
    private static String name;
    private static String description;
    private CollectionManager collectionManager;
    private InputManager inputManager;
    private ServerSendingManager sendingManager;
    private Ask ask;

    public RemoveLower(String name, String description, CollectionManager collectionManager, ServerSendingManager sendingManager) {
        super("remove_lower", "удалить из коллекции все элементы, меньшие, чем заданный");
        this.name = name;
        this.description = description;
        this.ask = ask;
        this.collectionManager = collectionManager;
        this.sendingManager = sendingManager;
        this.inputManager = inputManager;
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
        try{
            if (collectionManager.getCollection().isEmpty()) {
                sendingManager.sendMessage(new Message(message.getName(), "коллекция пуста!", message.getAddress()));
                return false;
            } else {
                PriorityQueue<SpaceMarine> queue = new PriorityQueue<>();

                for (var spaceMarine : collectionManager.getCollection()) {
                    if (Long.parseLong(message.getEntity().toString()) <= spaceMarine.getId()) {
                        queue.add(spaceMarine);

                    }
                } if (queue.size() == collectionManager.getCollection().size()){
                    sendingManager.sendMessage(new Message(message.getName(), "У всех элементов id больше заданного", message.getAddress()));
                    return false;
                }collectionManager.setCollection(queue);
                sendingManager.sendMessage(new Message(message.getName(), "Элементы удалены", message.getAddress()));
                return true;
            }
    } catch(Exception e){
            return false;
        }
    }
}
