package server.commands;

import lib.utility.Message;
import server.managers.CollectionManager;
import lib.managers.OutputManager;
import lib.spaceMarine.SpaceMarine;
import server.managers.ServerSendingManager;

import java.util.*;
import java.util.stream.Collectors;

public class PrintDescending extends Command {
    private static String name;
    private static String description;
    private CollectionManager collectionManager;
    private ServerSendingManager sendingManager;
    public PrintDescending(String name, String description, CollectionManager collectionManager, ServerSendingManager sendingManager) {
        super("print_descending", "вывести элементы коллекции в порядке убывания");
        this.name = name;
        this.description=description;
        this.collectionManager=collectionManager;
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
        List<SpaceMarine> sortedList = collectionManager.getCollection().stream()
            .sorted(Comparator.reverseOrder())
            .collect(Collectors.toList());
         try {
             sendingManager.sendMessage(new Message(message.getName(), sortedList.toString(), message.getAddress()));
             return true;
         } catch (Exception e) {
             return false;
         }
     }
    }
