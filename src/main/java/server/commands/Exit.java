package server.commands;

import lib.managers.OutputManager;

public class Exit extends Command {
    private OutputManager outputManager;
    private static String name;
    private static String description;
    public Exit(String name, String description, OutputManager outputManager) {
        super("exit", "завершить программу (без сохранения в файл)");this.name = name;
        this.description=description;
        this.outputManager = outputManager;
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
    public boolean execute(String arg){
        if (!arg.isEmpty()){
            outputManager.println("Неправильное количество аргументов ");
        return false;
        }
        outputManager.print("Завершение выполнения программы...");
        System.exit(0);
        return false;    }
}
