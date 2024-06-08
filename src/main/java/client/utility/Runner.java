package client.utility;
import lib.utility.Message;
import server.exeptions.InvalidInputException;
import client.managers.CommandManager;
import lib.managers.InputManager;
import client.managers.SendingManager;
import client.managers.UdpClient;
import lib.managers.OutputManager;
import lib.utility.Runnable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**класс для вызова команд*/
public class Runner implements Runnable {
    private OutputManager outputManager;
    private InputManager inputManager;
    private SendingManager sendingManager;
    private UdpClient udpClient;
    private CommandManager commandManager;
    public Runner(OutputManager outputManager, CommandManager commandManager, InputManager inputManager, UdpClient udpClient, SendingManager sendingManager) {
        this.sendingManager = sendingManager;
        this.udpClient = udpClient;
        this.outputManager = outputManager;
        this.inputManager = inputManager;
        this.commandManager = commandManager;
    }

    /**метод для запуска интерактивного режима**/
    @Override
    public void letsGo() {
        commandManager.addCommands();
        outputManager.print("Введите название команды или команду help для просмотра доступных команд\n");
        while (true) {
            try {
                String input = inputManager.read();
                String[] command = (input.trim() + " ").split(" ", 2);
                String name = command[0];
                while (true) {
                    if (!commandManager.getCommandMap().containsKey(name)) {
                        System.out.println("Такой команды не существует");
                    }
                    if (((commandManager.getCommandMap().get(name)) && (command[1].isEmpty())) || (!(commandManager.getCommandMap().get(name)) && !(command[1].isEmpty()))) {
                        System.out.println("Неправильное количество аргументов!");
                    }
                    break;
                }

                switch (name) {
                    case "execute_script": {
                        String argument = command[1].trim();
                        letsGoScript(argument, commandManager, inputManager, outputManager, new Scanner(new File(argument)));
                        break;
                    }
                    default: {
                        if (command[1].isEmpty()) {
                            command[1] = "";
                            Message message = new Message(name, command[1]);
                            udpClient.connect();
                            sendingManager.sendMessage(message);
                            break;
                        } else {
                            String argument = command[1].trim();
                            try {
                                Integer.parseInt(argument);
                            } catch (NumberFormatException e) {
                                outputManager.println("Неправильный аргумент");
                                break;
                            }
                            Message message = new Message(name, argument);
                            udpClient.connect();
                            sendingManager.sendMessage(message);
                            break;
                        }
                    }
                }
            } catch (FileNotFoundException e) {
                outputManager.println("Файл скрипта не найден!");
                break;
            } catch (InvalidInputException e) {
                outputManager.println("Неверный ввод данных ");
            } catch (NullPointerException e) {
                outputManager.println("Давайте не будем так делать :( \n");
                break;
            } catch (IOException e) {
                outputManager.println("Ошибка подключения");
            }
        }
    }
   /**метод для запуска интерактивного режима в execute_script*/
    @Override
    public void letsGoScript(String arg, CommandManager comman, InputManager inputManager, OutputManager outputManager, Scanner scanner){

        comman.addCommands();
        while (scanner.hasNextLine()) {
            try {
                String input;
                input = inputManager.read();
                String[] command = (input.trim() + " ").split(" ", 2);
                String commandName = command[0];
                if (commandManager.getCommandMap().containsKey(commandName)) {
                    System.out.println("Такой команды не существует");
                }
                if (((commandManager.getCommandMap().get(commandName)) && (command[1].isEmpty())) || (!(commandManager.getCommandMap().get(commandName)) && !(command[1].isEmpty()))) {
                    System.out.println("Неправильное количество аргументов!");
                }
                if (!commandName.equals("execute_script")) {
                    if (command[1].isEmpty()) {
                        command[1] = "";
                        Message message = new Message(commandName, command[1]);
                        sendingManager.sendMessage(message);
                    } else {
                        String argument = command[1].trim();
                        try {
                            Integer.parseInt(argument);
                            Message message = new Message(commandName, argument);
                            sendingManager.sendMessage(message);
                        } catch (NumberFormatException e) {
                            outputManager.println("Неправильный аргумент");
                        }
                    }
                }
                if (commandName.equals("execute_script")) {
                    outputManager.println("Скрипт не может вызываться рекурсивно");
                }
                } catch (InvalidInputException e) {
                outputManager.printerr("Неверный ввод данных ");
            }
        }
    }

}

