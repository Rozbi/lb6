package server.commands;


import lib.spaceMarine.SpaceMarine;
import lib.utility.Message;
import server.managers.CollectionManager;
import server.managers.JsonManager;
import lib.managers.OutputManager;
import server.managers.ServerSendingManager;

import java.util.*;
import java.util.stream.Collectors;


public class CountingByHealth extends Command {
    private static String name;
    private static String description;
    private CollectionManager collectionManager;
    private ServerSendingManager sendingManager;
    private JsonManager jsonManager;
    public CountingByHealth(String name, String description, CollectionManager collectionManager, ServerSendingManager sendingManager) {
        super("group_counting_by_health", "сгруппировать элементы коллекции по значению поля health, вывести количество элементов в каждой группе");this.name = name;
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
             Map<Long, Long> mapa = collectionManager.getCollection().stream()
                     .collect(Collectors.groupingBy(SpaceMarine::getHealth, Collectors.counting()));

             for (Long key : mapa.keySet()) {
                 sendingManager.sendMessage(new Message(message.getName(), (key).toString() + ": " + mapa.get(key).toString(), message.getAddress()));
             }
             return true;
         } catch (Exception e) {
             return false;
         }
     }
}
