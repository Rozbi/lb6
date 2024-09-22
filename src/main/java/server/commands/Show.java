package server.commands;

import lib.spaceMarine.SpaceMarine;
import lib.utility.Message;
import server.exeptions.InvalidInputException;
import server.managers.CollectionManager;
import server.managers.SQLManager;
import server.managers.ServerSendingManager;
import server.managers.UserManager;
import server.utility.SpaceMarineComparator;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Show extends Command {
    private static String name;
    private static String description;
    private ServerSendingManager serverSendingManager;
    private UserManager userManager;
    private SQLManager sqlManager;
    private SpaceMarineComparator comparator;
    private CollectionManager collectionManager;

    public Show(String name, String description, ServerSendingManager serverSendingManager, UserManager userManager, CollectionManager collectionManager, SpaceMarineComparator comparator) {
        super("show", "вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
        this.name = name;
        this.description = description;
        this.comparator = comparator;
        this.serverSendingManager = serverSendingManager;
        this.userManager=userManager;
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
    public boolean execute(Message message) throws InvalidInputException, IOException {
        if(userManager.getUserId(message.getUser().getLogin(), userManager.hashPassword(message.getUser().getPassword()))!=0) {
            try {
                List<SpaceMarine> sortedSpaceMarines = collectionManager.getCollection().stream()
    .   sorted(new SpaceMarineComparator())
    .   collect(Collectors.toList());
                serverSendingManager.sendMessage(new Message(message.getName(), sortedSpaceMarines.toString(), message.getAddress()));
                return true;
            } catch (Exception e) {
                return false;
            }
        }serverSendingManager.sendMessage(new Message("NonameUser", "Юзер не опознан.", message.getAddress()));
            return false;
    }
}
