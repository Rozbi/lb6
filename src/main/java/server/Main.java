package server;

import lib.spaceMarine.SpaceMarine;
import server.exeptions.InvalidInputException;
import server.utility.Runner;
import lib.managers.InputManager;
import lib.managers.OutputManager;
import lib.utility.PortGetter;
import server.managers.*;
import server.managers.JsonManager;
import server.utility.SpaceMarineComparator;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException, InvalidInputException {
        byte[] buffer = new byte[300];
        PortGetter portGetter = new PortGetter();
        OutputManager outputManager = new OutputManager();
        InputManager inputManager = new InputManager(new Scanner(System.in));
        InetSocketAddress inetSocketAddress = new InetSocketAddress("localhost", portGetter.getServerPort());
        ServerConnector serverConnector = new ServerConnector(inetSocketAddress);
        ServerSendingManager serverSendingManager = new ServerSendingManager(serverConnector);
        JsonManager jsonManager = new JsonManager();
        CollectionManager collectionManager = new CollectionManager(jsonManager);
        SpaceMarineComparator comparator = new SpaceMarineComparator();
        PriorityQueue<SpaceMarine> priora = new PriorityQueue(comparator);
        List<SpaceMarine> list = new ArrayList<>();
        list.addAll(jsonManager.readCollection());
        Collections.sort(list, comparator);
        priora.addAll(list);
        collectionManager.setCollection(priora);
        SQLconnector sqlconnector = new SQLconnector();
        UserManager userManager = new UserManager(sqlconnector, serverSendingManager);
        ServerReceivingManager serverReceivingManager = new ServerReceivingManager(serverConnector, serverSendingManager);
        CommandManager commandManager = new CommandManager(collectionManager, serverSendingManager, userManager);
        Runner runner = new Runner(collectionManager, commandManager, serverReceivingManager, serverSendingManager, serverConnector, inputManager, outputManager);
        runner.letsGo();
    }

}
