package client.utility;
import client.managers.*;
import lib.utility.Message;
import lib.utility.User;
import server.exeptions.InvalidInputException;
import lib.managers.InputManager;
import lib.managers.OutputManager;
import lib.utility.Runnable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**класс для вызова команд*/
public class Runner implements Runnable {
    private OutputManager outputManager;
    private InputManager inputManager;
    private SendingManager sendingManager;
    private UdpClient udpClient;
    private final CommandManager commandManager;
    private ReceivingManager receivingManager;
    private UserManager userManager;

    public Runner(OutputManager outputManager, CommandManager commandManager, InputManager inputManager, UdpClient udpClient, SendingManager sendingManager, Ask ask, ReceivingManager receivingManager, UserManager userManager) {
        this.sendingManager = sendingManager;
        this.receivingManager = receivingManager;
        this.udpClient = udpClient;
        this.outputManager = outputManager;
        this.inputManager = inputManager;
        this.commandManager = commandManager;
        this.userManager = userManager;
    }
    /**
     * метод для запуска интерактивного режима
     **/
    @Override
    public void letsGo() throws IOException, InvalidInputException {
        commandManager.addCommands();
        while (true) login: {
            User userInput = userManager.register();
            if (userInput==null) break login;
            Message recievedMessage = receivingManager.receive();
            outputManager.print(recievedMessage.getEntity().toString().split("!", 2)[0] + "\n");
                switch (recievedMessage.getName()) {
                    case "SuccessLogin":
                        User user = new User(Long.parseLong(recievedMessage.getEntity().toString().split("!", 2)[1]), userInput.getLogin(), userInput.getPassword());
                        outputManager.print("Введите название команды или команду help для просмотра доступных команд\n");
                        while (true) {
                            try {
                                String letsGoInput = inputManager.read().toLowerCase();
                                String[] letsGoCommand = (letsGoInput.trim() + " ").split(" ", 2);
                                String letsGoName = letsGoCommand[0];
                                if (!commandManager.getCommandMap().containsKey(letsGoName)) {
                                    outputManager.printerr("Такой команды не существует\n");
                                    continue;
                                }
                                if ((!(commandManager.getCommandMap().get(letsGoName)) && !(letsGoCommand[1].isEmpty())) || ((commandManager.getCommandMap().get(letsGoName)) && (letsGoCommand[1].isEmpty()))) {
                                    outputManager.printerr("Неправильное количество аргументов!\n");
                                    continue;
                                }
                                switch (letsGoName) {
                                    case "execute_script": {
                                        String argument = letsGoCommand[1].trim();
                                        letsGoScript(new InputManager(new Scanner(new File(argument))), user);
                                        if (Objects.equals(receivingManager.receive().getName(), "NonameUser")){
                                            break login;
                                        }
                                    }
                                    case "add", "add_if_min", "update": {
                                        Ask ask = new Ask(inputManager, outputManager);
                                        String argument = ask.getSpaceMarineComponents();
                                        String idArgument = letsGoCommand[1];
                                        Message message = new Message(letsGoName, idArgument + argument, user);
                                        sendingManager.sendMessage(message);

                                        Message mess = receivingManager.receive();

                                        outputManager.prettyPrint(mess);
                                        if (Objects.equals(mess.getName(), "NonameUser")){
                                            break login;
                                        }
                                        break;
                                    }
                                    case "exit": {
                                        Message message = new Message(letsGoName, user);
                                        sendingManager.sendMessage(message);
                                        Message mess = receivingManager.receive();
                                        outputManager.prettyPrint(mess);
                                        if (Objects.equals(mess.getName(), "NonameUser")){
                                            break login;
                                        }
                                            System.exit(0);
                                    }
                                    default: {
                                        if (letsGoCommand[1].isEmpty()) {
                                            Message message = new Message(letsGoName, user);
                                            //udpClient.connect();
                                            sendingManager.sendMessage(message);
                                            Message mess = receivingManager.receive();
                                            outputManager.prettyPrint(mess);
                                            if (Objects.equals(mess.getName(), "NonameUser")){
                                            break login;
                                        }
                                            break;
                                        } else {
                                            String argument = letsGoCommand[1].trim();
                                            try {
                                                Integer.parseInt(argument);
                                            } catch (NumberFormatException e) {
                                                outputManager.printerr("Неправильный аргумент");
                                                break;
                                            }
                                            Message message = new Message(letsGoName, argument, user);
                                            sendingManager.sendMessage(message);
                                            Message mess = receivingManager.receive();
                                            outputManager.prettyPrint(mess);
                                            if (Objects.equals(mess.getName(), "NonameUser")){
                                            break login;
                                        }
                                            break;
                                        }


                                    }
                                }
                            } catch (SocketTimeoutException e) {
                                throw new RuntimeException(e);
                            } catch (FileNotFoundException e) {
                                throw new RuntimeException(e);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                }
            }
        }


        /**
         * метод для запуска интерактивного режима в execute_script
         */
        public void letsGoScript (InputManager inputManager, User user) {

            commandManager.addCommands();
            while (InputManager.getScanner().hasNextLine()) {
                try {
                    String input;
                    input = inputManager.read().toLowerCase();
                    String[] command = (input.trim() + " ").split(" ", 2);
                    String commandName = command[0];
                    if (!commandManager.getCommandMap().containsKey(commandName)) {
                        System.out.println("Такой команды не существует");
                    }
                    if (((commandManager.getCommandMap().get(commandName)) && (command[1].isEmpty())) || (!(commandManager.getCommandMap().get(commandName)) && !(command[1].isEmpty()))) {
                        System.out.println("Неправильное количество аргументов!");
                    }
                    if (!commandName.equals("execute_script")) {
                        if (command[1].isEmpty()) {
                            command[1] = "";
                            Message message = new Message(commandName, command[1], user);
                            sendingManager.sendMessage(message);
                            Message serverMessage = receivingManager.receive();
                            outputManager.print(serverMessage.toString());
                            break;
                        } else {
                            String argument = command[1].trim();
                            try {
                                Integer.parseInt(argument);
                                Message message = new Message(commandName, argument, user);
                                sendingManager.sendMessage(message);
                                Message serverMessage = receivingManager.receive();
                                outputManager.print(serverMessage.toString());
                                break;
                            } catch (NumberFormatException e) {
                                outputManager.println("Неправильный аргумент");
                            } catch (SocketTimeoutException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    } else {
                        outputManager.println("Скрипт не может вызываться рекурсивно");
                    }
                } catch (InvalidInputException e) {
                    outputManager.printerr("Неверный ввод данных ");
                } catch (NullPointerException e) {
                } catch (SocketTimeoutException e) {
                    outputManager.printerr("Время ожидания вышло.");
                } catch (IOException e) {
                }
            }
        }
    }


