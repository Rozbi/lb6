package server.commands;


import lib.spaceMarine.SpaceMarine;
import lib.utility.Message;
import server.exeptions.InvalidInputException;
import server.managers.CollectionManager;
import server.managers.JsonManager;
import server.managers.ServerSendingManager;
import server.managers.UserManager;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


public class CountingByHealth extends Command {
    private static String name;
    private static String description;
    private CollectionManager collectionManager;
    private ServerSendingManager serverSendingManager;
    private UserManager userManager;

    public CountingByHealth(String name, String description, CollectionManager collectionManager, ServerSendingManager serverSendingManager, UserManager userManager) {
        super("group_counting_by_health", "сгруппировать элементы коллекции по значению поля health, вывести количество элементов в каждой группе");
        this.name = name;
        this.description = description;
        this.collectionManager = collectionManager;
        this.serverSendingManager = serverSendingManager;
        this.userManager=userManager;
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
        if (userManager.getUserId(message.getUser().getLogin(), message.getUser().getPassword()) != 0) {
            try {
                Map<Long, Long> mapa = collectionManager.getCollection().stream()
                        .collect(Collectors.groupingBy(SpaceMarine::getHealth, Collectors.counting()));
                for (Long key : mapa.keySet()) {
                    serverSendingManager.sendMessage(new Message(message.getName(), (key).toString() + ": " + mapa.get(key).toString(), message.getAddress()));
                }
                return true;
            } catch (Exception e) {
                return false;
            }
        } serverSendingManager.sendMessage(new Message("NonameUser", "Юзер не опознан.", message.getAddress()));
            return false;
    }
}
