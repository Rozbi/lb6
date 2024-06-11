package server.commands;

import client.managers.SendingManager;
import lib.managers.OutputManager;
import lib.utility.Message;
import server.exeptions.InvalidInputException;
import server.managers.ServerSendingManager;

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
    public Help(String name, String description, HashMap<String, Command> commands, ServerSendingManager sendingManager){
        super("help", "вывести справку по доступным командам");
        this.commands = commands;
        this.name = name;
        this.description = description;
        this.sendingManager = sendingManager;
    }
    public boolean execute(Message message) throws InvalidInputException, IOException {
        List<String> coms = new ArrayList<>();
        for(Command command : commands.values()){
            coms.add(command.getName() + ": " + command.getDescription());
        }
        sendingManager.sendMessage(new Message("help", coms.toString(), message.getAddress()));
        return true;
    }
}
