package server.commands;

import lib.utility.Message;
import server.exeptions.InvalidInputException;
import server.managers.ServerSendingManager;
import server.managers.UserManager;

import java.io.IOException;

public class Login extends Command {
    private String name;
    private String description;
    private final UserManager userManager;
    private ServerSendingManager serverSendingManager;

    public Login(String name, String description, UserManager userManager, ServerSendingManager serverSendingManager) {
        super("login", "войти в систему");
        this.name = name;
        this.description = description;
        this.userManager = userManager;
        this.serverSendingManager = serverSendingManager;
    }

    @Override
    public boolean execute(Message message) throws InvalidInputException, IOException {
        String user = message.getEntity().toString();
        String[] inputTrim = (user.trim() + " ").split(" ", 2);
        String login = inputTrim[0];
        String psswd = inputTrim[1];
        if (userManager.checkUser(login, psswd, message.getAddress())) {
            serverSendingManager.sendMessage(new Message(message.getName(), "Добро пожаловать!" + userManager.getUserId(login, psswd), message.getAddress()));
        }
        return false;

    }
}
