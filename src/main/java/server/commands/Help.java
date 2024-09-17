package server.commands;

import client.managers.SendingManager;
import lib.managers.OutputManager;
import lib.utility.Message;
import server.exeptions.InvalidInputException;
import server.managers.ServerSendingManager;
import server.managers.UserManager;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Help extends Command {
    private final HashMap<String, Command> commands;
    private String name;
    private String description;
    private final ServerSendingManager sendingManager;
    private final UserManager userManager;

    public Help(String name, String description, HashMap<String, Command> commands, ServerSendingManager sendingManager, UserManager userManager) {
        super("help", "вывести справку по доступным командам");
        this.userManager = userManager;
        this.commands = commands;
        this.name = name;
        this.description = description;
        this.sendingManager = sendingManager;
    }

    public boolean execute(Message message) throws InvalidInputException, IOException {
        if(userManager.getUserId(message.getUser().getLogin(), userManager.hashPassword(message.getUser().getPassword()))!=0) {
            List<String> coms = new ArrayList<>();
            for (Command command : commands.values()) {
                coms.add(command.getName() + ": " + command.getDescription());

            } sendingManager.sendMessage(new Message("help", String.join("\n", coms), message.getAddress()));
            return true;
        }
        sendingManager.sendMessage(new Message("NonameUser", "Юзер не опознан.", message.getAddress()));
        return false;
    }
}
