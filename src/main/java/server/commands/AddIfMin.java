package server.commands;

import lib.spaceMarine.*;
import lib.utility.Message;
import server.exeptions.InvalidInputException;
import server.managers.CollectionManager;
import server.managers.SQLManager;
import server.managers.ServerSendingManager;
import server.managers.UserManager;

import java.io.IOException;
import java.time.LocalDateTime;

public class AddIfMin extends Command {
    private static String name;
    private static String description;
    private CollectionManager collectionManager;
    private ServerSendingManager serverSendingManager;
    private UserManager userManager;
    private SQLManager sqlManager;


    public AddIfMin(String name, String description, CollectionManager collectionManager, ServerSendingManager serverSendingManager, UserManager userManager, SQLManager sqlManager) {
        super("add_if_min", "добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции");
        this.description = description;
        this.name = name;
        this.collectionManager = collectionManager;
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
            String[] sm = message.getEntity().toString().split(" ");
            try {
                Long minElement = 1000L;
                if (collectionManager.getCollection().isEmpty()) {
                    serverSendingManager.sendMessage(new Message(message.getName(), "Коллекция пуста. Элемент не добавлен в коллекцию ", message.getAddress()));
                    return false;
                }
                for (var spaceMarine : collectionManager.getCollection()) {
                    if (minElement > spaceMarine.getHealth()) {
                        minElement = spaceMarine.getHealth();
                    }
                }
                try {
                    SpaceMarine element = new SpaceMarine(sm[0], sm[1], new Coordinates(Long.parseLong(sm[1]), Float.parseFloat(sm[2])), LocalDateTime.now(), Long.parseLong(sm[3]), Integer.parseInt(sm[4]), sm[5].equals("null") ? null : AstartesCategory.valueOf(sm[5]), sm[6].equals("null") ? null : MeleeWeapon.valueOf(sm[6]), new Chapter(sm[7], sm[8]));
                    if (minElement > element.getHealth()) {
                        collectionManager.add(element);
                        sqlManager.createSpaceMarine(collectionManager.getCollection(), message.getUser());
                        serverSendingManager.sendMessage(new Message(message.getName(), "Элемент добавлен в коллекцию", message.getAddress()));
                        return true;
                    } else {
                        serverSendingManager.sendMessage(new Message(message.getName(), "Здоровье не минимальное. Элемент не добавлен ", message.getAddress()));
                        return false;
                    }
                } catch (IndexOutOfBoundsException e) {
                    SpaceMarine element = new SpaceMarine(sm[0], sm[1], new Coordinates(Long.parseLong(sm[1]), Float.parseFloat(sm[2])), LocalDateTime.now(), Long.parseLong(sm[3]), Integer.parseInt(sm[4]), sm[5].equals("null") ? null : AstartesCategory.valueOf(sm[5]), sm[6].equals("null") ? null : MeleeWeapon.valueOf(sm[6]), null);
                    if (minElement > element.getHealth()) {
                        collectionManager.add(element);
                        sqlManager.createSpaceMarine(collectionManager.getCollection(), message.getUser());
                        serverSendingManager.sendMessage(new Message(message.getName(), "Элемент добавлен в коллекцию", message.getAddress()));
                        return true;
                    } else {
                        serverSendingManager.sendMessage(new Message(message.getName(), "Здоровье не минимальное. Элемент не добавлен ", message.getAddress()));
                        return false;
                    }
                }
            } catch (Exception e) {
                return false;
            }
        }serverSendingManager.sendMessage(new Message("NonameUser", "Юзер не опознан.", message.getAddress()));
            return false;
    }
}