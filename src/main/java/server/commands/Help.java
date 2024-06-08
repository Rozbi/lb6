package server.commands;

import lib.managers.OutputManager;

import java.util.HashMap;

public class Help extends Command {
    private final HashMap<String, Command> commands;
    private final OutputManager outputManager;
    private String name;
    private String description;
    public Help(String name, String description, HashMap<String, Command> commands, OutputManager outputManager){
        super("help", "вывести справку по доступным командам");
        this.outputManager = outputManager;
        this.commands = commands;
        this.name = name;
        this.description = description;
    }
    public boolean execute(String arg){
        if (!arg.isEmpty()){
            outputManager.print("Неправильное количество аргументов ");
            return false;
        }
        outputManager.println("Список доступных команд: ");
        for (Command command : commands.values()) {
            outputManager.println(command.getName() + ": " + command.getDescription());
        }
        return true;
    }
}
