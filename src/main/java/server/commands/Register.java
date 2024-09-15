package server.commands;

import lib.utility.Message;
import server.exeptions.InvalidInputException;
import server.managers.ServerSendingManager;
import server.managers.UserManager;

import java.io.IOException;

public class Register extends Command {
    private String name;
    private String description;
    private UserManager userManager;
    private ServerSendingManager sendingManager;
 public Register(String name, String description, UserManager userManager, ServerSendingManager serverSendingManager) {
     super("register", "зарегестрироваться в системе");
     this.name = name;
     this.description = description;
     this.userManager=userManager;
     this.sendingManager=serverSendingManager;
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
     String user = message.getEntity().toString();
     String[] inputTrim = (user.trim() + " ").split(" ", 2);
     String login = inputTrim[0];
     String psswd = inputTrim[1].trim();
     String hashedPassword = userManager.hashPassword(psswd);
     if (userManager.checkUser(login, hashedPassword)){
         sendingManager.sendMessage(new Message("SuccessLogin","Вы уже зарегестрированы. Добро пожаловать!" + userManager.getUserId(login, hashedPassword), message.getAddress()));
         return true;
     }
     if (userManager.addUser(login, hashedPassword)){
         sendingManager.sendMessage(new Message("SuccessLogin", "Добро пожаловать!" + userManager.getUserId(login, hashedPassword), message.getAddress()));
         return true;
     }
    sendingManager.sendMessage(new Message("ErrorLogin", "Пользователь с таким именем уже есть", message.getAddress()));
    return false;
    }
}
