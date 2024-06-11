package server.commands;

import lib.spaceMarine.*;
import lib.utility.Message;
import server.managers.CollectionManager;
import lib.managers.OutputManager;
import lib.managers.InputManager;
import server.managers.ServerSendingManager;
import server.utility.SpaceMarineComparator;
import server.managers.ServerReceivingManager;

import java.time.LocalDateTime;

public class Update extends Command {
    private static String name;
    private static String description;
    private CollectionManager collectionManager;
    private ServerSendingManager serverSendingManager;
    private boolean k;
    public Update(String name, String description, CollectionManager collectionManager, ServerSendingManager serverSendingManager) {
        super("update", "обновить значение элемента коллекции, id которого равен заданному");
        this.name = name;
        this.serverSendingManager = serverSendingManager;
        this.description=description;
        this.collectionManager = collectionManager;
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
            String[] sm = message.getEntity().toString().split(" ");
            SpaceMarineComparator comparator = new SpaceMarineComparator();
            String[] components = message.getEntity().toString().split(" ");
            try {
                SpaceMarine element = new SpaceMarine(collectionManager.getCurrentId(), sm[0], new Coordinates(Long.parseLong(sm[1]), Float.parseFloat(sm[2])), LocalDateTime.now(), Long.parseLong(sm[3]), Integer.parseInt(sm[4]), sm[5].equals("null") ? null : AstartesCategory.valueOf(sm[5]), sm[6].equals("null") ? null : MeleeWeapon.valueOf(sm[6]), new Chapter(sm[7], sm[8]));
                for (var spaceMarine : collectionManager.getCollection()) {
                    if ((spaceMarine.getId()) == Long.parseLong(message.getEntity().toString())) {
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
                if (!k) {
                    serverSendingManager.sendMessage(new Message(message.getName(), "Нет элемента с таким id ", message.getAddress()));
                }
            } catch (IndexOutOfBoundsException e) {
                Long idElement = Long.parseLong(sm[0]);
                SpaceMarine element = new SpaceMarine(collectionManager.getCurrentId(), sm[1], new Coordinates(Long.parseLong(sm[2]), Float.parseFloat(sm[3])), LocalDateTime.now(), Long.parseLong(sm[4]), Integer.parseInt(sm[5]), sm[6].equals("null") ? null : AstartesCategory.valueOf(sm[6]), sm[7].equals("null") ? null : MeleeWeapon.valueOf(sm[7]), null);
                for (var spaceMarine : collectionManager.getCollection()) {
                    if ((spaceMarine.getId()) == element.getId()) {
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
    }
}
