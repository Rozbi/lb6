package client.managers;

import lib.managers.InputManager;
import lib.managers.OutputManager;
import lib.utility.Message;
import lib.utility.User;
import server.exeptions.InvalidInputException;

public class UserManager {
    private OutputManager outputManager;
    private InputManager inputManager;
    private SendingManager sendingManager;
    public UserManager(OutputManager outputManager, InputManager inputManager, SendingManager sendingManager) {
        this.sendingManager = sendingManager;
        this.outputManager = outputManager;
        this.inputManager = inputManager;
    }

    public User register() throws InvalidInputException {
        outputManager.print("Выберите действие. Login/Register\n");
        String input = inputManager.read().toLowerCase();
        String[] command = (input.trim() + " ").split(" ", 2);
        String name = command[0];
        if (name.equals("login") || (name.equals("register"))) {
            outputManager.print("Введите логин\n");
            String loginInput = inputManager.read().toLowerCase();
            String[] inputTrim = (loginInput.trim() + " ").split(" ", 2);
            String login = inputTrim[0];
            outputManager.print("Введите пароль\n");
            String PsswdInput = inputManager.read().toLowerCase();
            String[] PsswdTrim = (PsswdInput.trim() + " ").split(" ", 2);
            String psswd = PsswdTrim[0];
            Message userMessage = new Message(name, login + " " + psswd);
            sendingManager.sendMessage(userMessage);
            return new User(login, psswd);
        }
        return null;
    }
}
