package server.commands;

import lib.utility.Message;
import server.exeptions.InvalidInputException;
import server.managers.CollectionManager;
import lib.spaceMarine.SpaceMarine;
import server.managers.ServerSendingManager;
import server.managers.UserManager;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class PrintDescending extends Command {
    private static String name;
    private static String description;
    private CollectionManager collectionManager;
    private ServerSendingManager serverSendingManager;
    private UserManager userManager;
    public PrintDescending(String name, String description, CollectionManager collectionManager, ServerSendingManager serverSendingManager, UserManager userManager) {
        super("print_descending", "вывести элементы коллекции в порядке убывания");
        this.name = name;
        this.description=description;
        this.collectionManager=collectionManager;
        this.serverSendingManager = serverSendingManager;
        this.userManager=userManager;
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
    public boolean execute(Message message) throws InvalidInputException, IOException {
         if (userManager.getUserId(message.getUser().getLogin(), message.getUser().getPassword()) != 0) {
             List<SpaceMarine> sortedList = collectionManager.getCollection().stream()
                     .sorted(Comparator.reverseOrder())
                     .collect(Collectors.toList());
             try {
                 serverSendingManager.sendMessage(new Message(message.getName(), sortedList.toString(), message.getAddress()));
                 return true;
             } catch (Exception e) {
                 return false;
             }
         }serverSendingManager.sendMessage(new Message("NonameUser", "Юзер не опознан.", message.getAddress()));
            return false;
     }
    }
