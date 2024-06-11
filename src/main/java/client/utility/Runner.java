package client.utility;
import client.managers.ReceivingManager;
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
import java.net.SocketException;
import java.util.Scanner;

/**класс для вызова команд*/
public class Runner implements Runnable {
    private OutputManager outputManager;
    private InputManager inputManager;
    private SendingManager sendingManager;
    private UdpClient udpClient;
    private final CommandManager commandManager;
    private ReceivingManager receivingManager;

    public Runner(OutputManager outputManager, CommandManager commandManager, InputManager inputManager, UdpClient udpClient, SendingManager sendingManager, Ask ask, ReceivingManager receivingManager) {
        this.sendingManager = sendingManager;
        this.receivingManager = receivingManager;
        this.udpClient = udpClient;
        this.outputManager = outputManager;
        this.inputManager = inputManager;
        this.commandManager = commandManager;
    }

    /**
     * метод для запуска интерактивного режима
     **/
    @Override
    public void letsGo() throws SocketException {
        commandManager.addCommands();
        outputManager.print("Введите название команды или команду help для просмотра доступных команд\n");
        while (true) {
            try {
                String input = inputManager.read();
                String[] command = (input.trim() + " ").split(" ", 2);
                String name = command[0];
                if (!commandManager.getCommandMap().containsKey(name)) {
                        outputManager.printerr("Такой команды не существует");
                        continue;
                }
                if ((!(commandManager.getCommandMap().get(name)) && !(command[1].isEmpty())) || ((commandManager.getCommandMap().get(name)) && (command[1].isEmpty()))) {
                        outputManager.printerr("Неправильное количество аргументов!");
                        continue;
                }
                switch (name) {
                    case "execute_script": {
                        String argument = command[1].trim();
                        letsGoScript(new InputManager(new Scanner(new File(argument))));
                        break;
                    }
                    case "add", "add_if_min", "update" :{
                        Ask ask = new Ask(inputManager, outputManager);
                        String argument = ask.getSpaceMarineComponents();
                        String idArgument = command[1].trim();
                        Message message = new Message(name, idArgument + argument);
                        sendingManager.sendMessage(message);
                        outputManager.prettyPrint(receivingManager.receive());
                        break;
                    }
                    case "exit": {
                        Message message = new Message(name, "");
                        sendingManager.sendMessage(message);
                        outputManager.prettyPrint(receivingManager.receive());
                        System.exit(0);
                    }
                    default: {
                        if (command[1].isEmpty()) {
                            command[1] = "";
                            Message message = new Message(name, command[1]);
                            udpClient.connect();
                            sendingManager.sendMessage(message);
                            outputManager.prettyPrint(receivingManager.receive());
                            break;
                        } else {
                            String argument = command[1].trim();
                            try {
                                Integer.parseInt(argument);
                            } catch (NumberFormatException e) {
                                outputManager.printerr("Неправильный аргумент");
                                break;
                            }
                            Message message = new Message(name, argument);
                            sendingManager.sendMessage(message);
                            outputManager.prettyPrint(receivingManager.receive());
                            break;
                        }
                    }
                }
            } catch (FileNotFoundException e) {
                outputManager.printerr("Файл скрипта не найден!");
                break;
            } catch (InvalidInputException e) {
                outputManager.printerr("Неверный ввод данных ");
            } catch (NullPointerException e) {
                outputManager.printerr("Давайте не будем так делать :(");
                break;
            } catch (IOException e) {
                outputManager.printerr("Ошибка выполнения");
            }
        }
    }

    /**
     * метод для запуска интерактивного режима в execute_script
     */
    public void letsGoScript(InputManager inputManager) {

        commandManager.addCommands();
        while (InputManager.getScanner().hasNextLine()) {
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
                        Message serverMessage = receivingManager.receive();
                        outputManager.print(serverMessage.toString());
                        break;
                    } else {
                        String argument = command[1].trim();
                        try {
                            Integer.parseInt(argument);
                            Message message = new Message(commandName, argument);
                            sendingManager.sendMessage(message);
                            Message serverMessage = receivingManager.receive();
                            outputManager.print(serverMessage.toString());
                            break;
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
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

