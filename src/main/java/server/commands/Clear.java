package server.commands;

import lib.utility.Message;
import server.managers.CollectionManager;
import lib.managers.OutputManager;
import server.managers.ServerSendingManager;

public class Clear extends Command {
    private static String name;
    private static String description;
    private CollectionManager collectionManager;
    private ServerSendingManager serverSendingManager;
    public Clear(String name, String description, CollectionManager collectionManager, ServerSendingManager serverSendingManager) {
        super("clear", "очистить коллекцию");this.name = name;
        this.description=description;
        this.collectionManager = collectionManager;
        this.serverSendingManager = serverSendingManager;
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
            collectionManager.clear();
            serverSendingManager.sendMessage(new Message(message.getName(), "Коллекция очищена ", message.getAddress()));
            return true;
        } catch(Exception e){
            return false;
        }

    }
}
