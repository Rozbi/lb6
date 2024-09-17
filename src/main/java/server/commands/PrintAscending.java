package server.commands;

import lib.utility.Message;
import lib.spaceMarine.SpaceMarine;
import server.exeptions.InvalidInputException;
import server.managers.CollectionManager;
import server.managers.SQLManager;
import server.managers.ServerSendingManager;
import server.managers.UserManager;
import server.utility.SpaceMarineComparator;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class PrintAscending extends Command {
    private static String name;
    private static String description;
    private ServerSendingManager serverSendingManager;
    private SQLManager sqlManager;
    private UserManager userManager;
    public PrintAscending(String name, String description, ServerSendingManager serverSendingManager, UserManager userManager, SQLManager sqlManager) {
        super("print_ascending", "вывести элементы коллекции в порядке возрастания");
        this.name = name;
        this.description = description;
        this.serverSendingManager = serverSendingManager;
        this.userManager=userManager;
        this.sqlManager = sqlManager;
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
        if (userManager.getUserId(message.getUser().getLogin(), userManager.hashPassword(message.getUser().getPassword())) != 0) {
            try {
                List<SpaceMarine> sortedSpaceMarines = sqlManager.select().stream()
    .           sorted(new SpaceMarineComparator())
    .           collect(Collectors.toList());
                serverSendingManager.sendMessage(new Message(message.getName(), sortedSpaceMarines.toString(), message.getAddress()));
                return true;
            } catch (Exception e) {
                return false;
            }
        }serverSendingManager.sendMessage(new Message("NonameUser", "Юзер не опознан.", message.getAddress()));
            return false;
    }
}
