package server.commands;

import client.utility.LocalDateTimeAdapter;
import lib.spaceMarine.*;
import lib.utility.Message;
import server.managers.CollectionManager;
import lib.managers.InputManager;
import lib.managers.OutputManager;
import client.utility.Ask;
import server.managers.ServerReceivingManager;
import server.managers.ServerSendingManager;

import java.time.LocalDateTime;

public class Add extends Command {
    private static String name;
    private static String description;
    private CollectionManager collectionManager;
    private Ask ask;
    private ServerSendingManager serverSendingManager;
    public Add(String name, String description, CollectionManager collectionManager, ServerSendingManager serverSendingManager){
        super("add", "добавить новый элемент в коллекцию");
        this.name = name;
        this.description=description;
        this.collectionManager=collectionManager;
        this.serverSendingManager=serverSendingManager;
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
    public boolean execute(Message message){
        try{
            String [] sm = message.getEntity().toString().split(" ");
            try{
                SpaceMarine spaceMarine = new SpaceMarine(collectionManager.getCurrentId(), sm[0], new Coordinates(Long.parseLong(sm[1]), Float.parseFloat(sm[2])), LocalDateTime.now(), Long.parseLong(sm[3]), Integer.parseInt(sm[4]), sm[5].equals("null") ? null : AstartesCategory.valueOf(sm[5]), sm[6].equals("null") ? null : MeleeWeapon.valueOf(sm[6]), new Chapter(sm[7], sm[8]));
                collectionManager.add(spaceMarine);
            } catch (IndexOutOfBoundsException e){
                SpaceMarine spaceMarine = new SpaceMarine(collectionManager.getCurrentId(), sm[0], new Coordinates(Long.parseLong(sm[1]), Float.parseFloat(sm[2])), LocalDateTime.now(), Long.parseLong(sm[3]), Integer.parseInt(sm[4]), sm[5].equals("null") ? null : AstartesCategory.valueOf(sm[5]), sm[6].equals("null") ? null : MeleeWeapon.valueOf(sm[6]), null);
                collectionManager.add(spaceMarine);
            }
            serverSendingManager.sendMessage(new Message("add", "Элемент успешно добавлен в коллекцию", message.getAddress()));
                return true;
    }catch (Exception e){
            return false;
        }
    }
}
