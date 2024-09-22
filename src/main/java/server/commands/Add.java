package server.commands;

import lib.spaceMarine.*;
import lib.utility.Message;
import server.exeptions.InvalidInputException;
import server.managers.CollectionManager;
import client.utility.Ask;
import server.managers.SQLManager;
import server.managers.ServerSendingManager;
import server.managers.UserManager;

import java.io.IOException;
import java.time.LocalDateTime;

public class Add extends Command {
    private static String name;
    private static String description;
    private CollectionManager collectionManager;
    private Ask ask;
    private ServerSendingManager serverSendingManager;
    private UserManager userManager;
    private SQLManager sqlManager;
    public Add(String name, String description, CollectionManager collectionManager, ServerSendingManager serverSendingManager, UserManager userManager, SQLManager sqlManager){
        super("add", "добавить новый элемент в коллекцию");
        this.name = name;
        this.description=description;
        this.collectionManager=collectionManager;
        this.serverSendingManager=serverSendingManager;
        this.userManager=userManager;
        this.sqlManager = sqlManager;
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
        if(userManager.getUserId(message.getUser().getLogin(), userManager.hashPassword(message.getUser().getPassword()))!=0) {
            try {
                String[] sm = message.getEntity().toString().split(" ");
                long id = 0;
                try {
                    SpaceMarine spaceMarine = new SpaceMarine(message.getUser().getLogin(), sm[0], new Coordinates(Long.parseLong(sm[1]), Float.parseFloat(sm[2])), LocalDateTime.now(), Long.parseLong(sm[3]), Integer.parseInt(sm[4]), sm[5].equals("null") ? null : AstartesCategory.valueOf(sm[5]), sm[6].equals("null") ? null : MeleeWeapon.valueOf(sm[6]), new Chapter(sm[7], sm[8]));
                    sqlManager.addSpaceMarine(spaceMarine, message.getUser());
                    spaceMarine.setId(sqlManager.getSpaceMarineId(spaceMarine));
                    collectionManager.add(spaceMarine);


                } catch (IndexOutOfBoundsException e) {
                    SpaceMarine spaceMarine = new SpaceMarine(message.getUser().getLogin(), sm[0], new Coordinates(Long.parseLong(sm[1]), Float.parseFloat(sm[2])), LocalDateTime.now(), Long.parseLong(sm[3]), Integer.parseInt(sm[4]), sm[5].equals("null") ? null : AstartesCategory.valueOf(sm[5]), sm[6].equals("null") ? null : MeleeWeapon.valueOf(sm[6]), null);
                    sqlManager.addSpaceMarine(spaceMarine, message.getUser());
                    spaceMarine.setId(sqlManager.getSpaceMarineId(spaceMarine));
                    collectionManager.add(spaceMarine);
                }
                serverSendingManager.sendMessage(new Message("add", "Элемент успешно добавлен в коллекцию", message.getAddress()));
                return true;
            } catch (Exception e) {
                return false;
            }
        } serverSendingManager.sendMessage(new Message("NonameUser", "Юзер не опознан.", message.getAddress()));
                return false;
    }
}
