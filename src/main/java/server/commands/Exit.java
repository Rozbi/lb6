package server.commands;


import lib.managers.OutputManager;
import lib.utility.Message;
import server.managers.CollectionManager;
import server.managers.ServerSendingManager;

public class Exit extends Command {
    private static String name;
    private static String description;
    private ServerSendingManager sendingManager;
    private CollectionManager collectionManager;

    public Exit(String name, String description, ServerSendingManager sendingManager, CollectionManager collectionManager) {
        super("exit", "завершить программу (без сохранения в файл)");
        this.name = name;
        this.description = description;
        this.sendingManager = sendingManager;
        this.collectionManager = collectionManager;
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
            sendingManager.sendMessage(new Message("exit", "Завершение выполнения программы...", message.getAddress()));
            collectionManager.save();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}