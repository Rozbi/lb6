package server.commands;

import client.managers.SendingManager;
import lib.utility.Message;
import server.exeptions.InvalidInputException;
import server.managers.ServerSendingManager;
import server.managers.UserManager;

public class Register extends Command {
    private String name;
    private String description;
    private UserManager userManager;
    private SendingManager sendingManager;
 public Register(String name, String description, UserManager userManager, ServerSendingManager serverSendingManager){
     super("register", "зарегестрироваться в системе");
     this.name = name;
     this.description = description;
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
    public boolean execute(Message message) throws InvalidInputException {
     String user = message.getEntity().toString();
     String[] inputTrim = (user.trim() + " ").split(" ", 2);
     String login = inputTrim[0];
     String psswd = inputTrim[1];
     if (userManager.checkUser(login, psswd, message.getAddress())){
         sendingManager.sendMessage(new Message("SuccessLogin","Вы уже зарегестрированы. Добро пожаловать! " + userManager.getUserId(login, psswd), message.getAddress()));
         return true;
     } sendingManager.sendMessage(new Message("SuccessLogin", "Добро пожаловать " + userManager.getUserId(login, psswd), message.getAddress()));
        return true;
    }
}
