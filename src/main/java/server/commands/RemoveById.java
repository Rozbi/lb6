package server.commands;

import client.managers.SendingManager;
import lib.utility.Message;
import server.managers.CollectionManager;
import lib.managers.OutputManager;
import server.managers.ServerSendingManager;

public class RemoveById extends Command {
    private static String name;
    private static String description;
    private CollectionManager collectionManager;
    private ServerSendingManager sendingManager;
    private boolean k;
    public RemoveById(String name, String description, CollectionManager collectionManager, ServerSendingManager sendingManager) {
        super("remove_by_id", "удалить элемент из коллекции по его id");
        this.name = name;
        this.description=description;
        this.collectionManager = collectionManager;
        this.sendingManager = sendingManager;
    }
    @Override
    public String getName(){
        return name;
    }
    @Override
    public String getDescription(){
        return description;
    }
     @Override
    public boolean execute(Message message) {
         try {
             for (var spaceMarine : collectionManager.getCollection()) {
                 if ((spaceMarine.getId()) == Long.parseLong(message.getEntity().toString())) {
                     collectionManager.getCollection().remove(spaceMarine);
                     sendingManager.sendMessage(new Message(message.getName(), "Элемент удален", message.getAddress()));
                     k = true;
                     return true;
                 }
             }
             if (!k) {
                 sendingManager.sendMessage(new Message(message.getName(), "Нет элемента с таким id", message.getAddress()));
                 return false;
             }
             return false;
         } catch (Exception e) {
             return false;
         }
     }
}
