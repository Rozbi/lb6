package server.managers;
import lib.managers.OutputManager;
import lib.utility.Message;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.SerializationUtils;
import server.utility.Runner;

public class ServerReceivingManager {
    private final ServerConnector serverConnector;
    private ServerSendingManager serverSendingManager;


    public ServerReceivingManager(ServerConnector serverConnector, ServerSendingManager serverSendingManager) {
        this.serverConnector = serverConnector;
        this.serverSendingManager = serverSendingManager;

    }

    public void receive(Runner runner) throws IOException, SocketTimeoutException, InterruptedException {
        for (; ;) {
            try {
                ByteBuffer buffer = ByteBuffer.allocate(4096);
                InetSocketAddress senderAddress = (InetSocketAddress) serverConnector.getChannel().receive(buffer);
                if (senderAddress != null) {
                    new Thread(() -> {
                    buffer.flip(); // Переключаем в режим чтения.
                    int bytesRead = buffer.remaining();

                    byte[] data = new byte[bytesRead];
                    buffer.get(data);
                    Message message = SerializationUtils.deserialize(buffer.array());
                    message.setAddress(senderAddress);
                    runner.processMessage(message);
                    }).start();
                }
            } catch (Exception e) {
                Thread.sleep(3000);
                serverConnector.getChannel().close();
                serverConnector.connect();
            }
        }
        }
}

