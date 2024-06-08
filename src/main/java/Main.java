import client.managers.*;
import lib.managers.InputManager;
import lib.utility.Ask;
import lib.managers.OutputManager;
import server.managers.CollectionManager;
import server.managers.JsonManager;
import server.utility.Runner;
import server.managers.ServerReceivingManager;
import server.managers.ServerSendingManager;

import java.io.FileNotFoundException;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SocketException, FileNotFoundException {
        byte[] buffer = new byte[300];
        OutputManager outputManager = new OutputManager();
        DatagramSocket datagramSocket = new DatagramSocket(1234);
        JsonManager jsonManager = new JsonManager(outputManager);
        CollectionManager collectionManager = new CollectionManager(jsonManager);
        InputManager inputManager = new InputManager(outputManager, new Scanner(System.in));
        Ask ask = new Ask(inputManager, outputManager);
        ServerReceivingManager serverReceivingManager = new ServerReceivingManager(datagramSocket, outputManager);
//        CommandManager = new CommandManager(collectionManager, outputManager, ask, inputManager, serverReceivingManager);
        ServerSendingManager serverSendingManager = new ServerSendingManager(serverReceivingManager, outputManager, buffer);
        serverReceivingManager.receive();
//        Runner runner = new Runner(outputManager, inputManager, commandManager, serverReceivingManager);
//        runner.letsGo();
//        serverSendingManager.sendPack();


    }

}
