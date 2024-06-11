package server.commands;

import lib.utility.Message;
import server.managers.CollectionManager;
import lib.managers.OutputManager;
import server.managers.ServerSendingManager;

public class Info extends Command {
    private CollectionManager collectionManager;
    private static String name;
    private static String description;
    private ServerSendingManager sendingManager;
    public Info(String name, String description, CollectionManager collectionManager, ServerSendingManager sendingManager) {
        super("info", "вывести в стандартный поток вывода информацию о коллекции");this.name = name;
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
    public boolean execute(Message message){
        try{
        sendingManager.sendMessage(new Message("info", collectionManager.getCollection().getClass().toString() + " " + collectionManager.getCollection().size() + collectionManager.getLastInitTime() + " " + collectionManager.getLastSaveTime(), message.getAddress()));
        return true;
    } catch (Exception e) {
        return false;
    }
    }
}
