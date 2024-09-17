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
import server.utility.ExchangeChannel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.*;

public class Main {
    static ExchangeChannel exchangeChannel;
    public static void main(String[] args) throws IOException, InterruptedException, InvalidInputException {
        byte[] buffer = new byte[300];
        PortGetter portGetter = new PortGetter();
        OutputManager outputManager = new OutputManager();
        InputManager inputManager = new InputManager(new Scanner(System.in));
        InetSocketAddress inetSocketAddress = new InetSocketAddress("localhost", portGetter.getServerPort());
        ServerConnector serverConnector = new ServerConnector(inetSocketAddress);
        exchangeChannel = new ExchangeChannel(serverConnector);
        ServerSendingManager serverSendingManager = new ServerSendingManager(serverConnector);
        JsonManager jsonManager = new JsonManager();
        SpaceMarineComparator comparator = new SpaceMarineComparator();
        PriorityQueue<SpaceMarine> priora = new PriorityQueue(comparator);
        SQLconnector sqlconnector = new SQLconnector();
        SQLManager sqlManager = new SQLManager(sqlconnector);
        CollectionManager collectionManager = new CollectionManager(sqlManager);
        List<SpaceMarine> list = sqlManager.select();
        Collections.sort(list, comparator);
        priora.addAll(list);
        collectionManager.setCollection(priora);
        UserManager userManager = new UserManager(sqlconnector, serverSendingManager);
        ServerReceivingManager serverReceivingManager = new ServerReceivingManager(serverConnector, serverSendingManager);
        CommandManager commandManager = new CommandManager(collectionManager, serverSendingManager, userManager, sqlManager, comparator);
        Runner runner = new Runner(collectionManager, commandManager, serverReceivingManager, serverSendingManager, serverConnector, inputManager, outputManager);
        exchangeChannel.setCommandManager(commandManager); // Передача CommandManager в ExchangeChannel
        Thread serverThread = new Thread(exchangeChannel);
        serverThread.run();
        runner.letsGo();
    }
}
