package server.commands;

import client.managers.SendingManager;
import lib.utility.Message;
import server.exeptions.InvalidInputException;
import server.managers.CollectionManager;
import lib.managers.InputManager;
import lib.managers.OutputManager;
import lib.spaceMarine.SpaceMarine;
import client.utility.Ask;
import server.managers.ServerSendingManager;

import java.io.IOException;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

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
    public boolean execute(Message message) throws InvalidInputException, IOException {
        try{
            if (collectionManager.getCollection().isEmpty()) {
                sendingManager.sendMessage(new Message(message.getName(), "коллекция пуста!", message.getAddress()));
                return false;
            } else {
                PriorityQueue<SpaceMarine> queue = collectionManager.getCollection().stream()
                        .filter(spaceMarine -> spaceMarine.getId()>Long.parseLong(message.getEntity().toString()))
                        .collect(Collectors.toCollection(PriorityQueue::new));
                collectionManager.setCollection(queue);
                sendingManager.sendMessage(new Message(message.getName(), "Элементы удалены", message.getAddress()));
                return true;
            }
    } catch(Exception e){
            return false;
        }
    }
}
