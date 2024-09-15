package server.commands;

import lib.spaceMarine.*;
import lib.utility.Message;
import server.exeptions.InvalidInputException;
import server.managers.CollectionManager;
import server.managers.ServerSendingManager;
import server.managers.UserManager;
import server.utility.SpaceMarineComparator;

import java.io.IOException;
import java.time.LocalDateTime;

public class Update extends Command {
    private static String name;
    private static String description;
    private CollectionManager collectionManager;
    private ServerSendingManager serverSendingManager;
    private boolean k;
    private UserManager userManager;
    public Update(String name, String description, CollectionManager collectionManager, ServerSendingManager serverSendingManager, UserManager userManager) {
        super("update", "обновить значение элемента коллекции, id которого равен заданному");
        this.name = name;
        this.serverSendingManager = serverSendingManager;
        this.description=description;
        this.collectionManager = collectionManager;
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
            try {
                boolean k = false;
                String[] sm = message.getEntity().toString().split(" ");
                SpaceMarineComparator comparator = new SpaceMarineComparator();
                Long idArgument = Long.parseLong(sm[0]);
                try {
                    SpaceMarine element = new SpaceMarine(collectionManager.getCurrentId(), sm[1], new Coordinates(Long.parseLong(sm[2]), Float.parseFloat(sm[3])), LocalDateTime.now(), Long.parseLong(sm[4]), Integer.parseInt(sm[5]), sm[6].equals("null") ? null : AstartesCategory.valueOf(sm[6]), sm[7].equals("null") ? null : MeleeWeapon.valueOf(sm[7]), new Chapter(sm[8], sm[9]));
                    for (var spaceMarine : collectionManager.getCollection()) {
                        if ((spaceMarine.getId()) == idArgument) {
                            spaceMarine.setName(element.getName());
                            spaceMarine.setCoordinates(element.getCoordinates());
                            spaceMarine.setHealth(element.getHealth());
                            spaceMarine.setHeartCount(element.getHeartCount());
                            spaceMarine.setMeleeWeapon(element.getMeleeWeapon());
                            spaceMarine.setChapter(element.getChapter());
                            spaceMarine.setCreationDate(element.getCreationDate());
                            spaceMarine.setCategory(element.getCategory());
                            serverSendingManager.sendMessage(new Message("add", "Элемент обновлен", message.getAddress()));
                            collectionManager.setLastInitTime(LocalDateTime.now());
                            k = true;
                            return true;
                        }
                    }
                } catch (IndexOutOfBoundsException e) {
                    SpaceMarine element = new SpaceMarine(collectionManager.getCurrentId(), sm[1], new Coordinates(Long.parseLong(sm[2]), Float.parseFloat(sm[3])), LocalDateTime.now(), Long.parseLong(sm[4]), Integer.parseInt(sm[5]), sm[6].equals("null") ? null : AstartesCategory.valueOf(sm[6]), sm[7].equals("null") ? null : MeleeWeapon.valueOf(sm[7]), null);
                    for (var spaceMarine : collectionManager.getCollection()) {
                        if ((spaceMarine.getId()) == idArgument) {
                            spaceMarine.setName(element.getName());
                            spaceMarine.setCoordinates(element.getCoordinates());
                            spaceMarine.setHealth(element.getHealth());
                            spaceMarine.setHeartCount(element.getHeartCount());
                            spaceMarine.setMeleeWeapon(element.getMeleeWeapon());
                            spaceMarine.setChapter(element.getChapter());
                            spaceMarine.setCreationDate(element.getCreationDate());
                            spaceMarine.setCategory(element.getCategory());
                            serverSendingManager.sendMessage(new Message(message.getName(), "Элемент обновлен", message.getAddress()));
                            collectionManager.setLastInitTime(LocalDateTime.now());
                            k = true;
                            return true;
                        }
                    }
                    if (!k) {
                        serverSendingManager.sendMessage(new Message(message.getName(), "Нет элемента с таким id ", message.getAddress()));
                        return false;
                    }
                }
            } catch (Exception e) {
                return false;
            }
            return false;
        }serverSendingManager.sendMessage(new Message("NonameUser", "Юзер не опознан.", message.getAddress()));
            return false;
    }
}
