package client.managers;

import lib.managers.InputManager;
import lib.managers.OutputManager;
import lib.utility.Message;
import server.exeptions.InvalidInputException;

public class UserManager {
    private OutputManager outputManager;
    private InputManager inputManager;

    public UserManager(OutputManager outputManager, InputManager inputManager) {
        this.outputManager = outputManager;
        this.inputManager = inputManager;
    }

    public Message register() {
        outputManager.print("Выберите команду. Login/Register\n");
        while (true) {
            try {
                String input = inputManager.read().toLowerCase();
                String[] command = (input.trim() + " ").split(" ", 2);
                if (command[1] != null) {
                    outputManager.printerr("Неправильное количество аргументов");
                }
            } catch (InvalidInputException e) {
                outputManager.printerr("Ошибка чтения");
            }
        }
    }
}
