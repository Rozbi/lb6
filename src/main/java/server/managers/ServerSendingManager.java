package server.managers;

import lib.managers.OutputManager;
import lib.utility.Message;
import lib.utility.PortGetter;
import org.apache.commons.lang3.SerializationUtils;
import server.exeptions.InvalidInputException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ServerSendingManager {
    private final ServerConnector serverConnector;
    private PortGetter portGetter;
    private final ThreadPoolExecutor sendThreadPool = (ThreadPoolExecutor) Executors.newCachedThreadPool();


    public ServerSendingManager(ServerConnector serverConnector, PortGetter portGetter) {
        this.serverConnector = serverConnector;
        this.portGetter = portGetter;

    }

    public void sendMessage(Message message) throws InvalidInputException, IOException {
        sendThreadPool.submit(() -> {
            ByteBuffer buffer = ByteBuffer.wrap(SerializationUtils.serialize(message));
            try {
                serverConnector.getChannel().send(buffer, message.getAddress());

            } catch (IOException e) {
            }
        });
    }
}
