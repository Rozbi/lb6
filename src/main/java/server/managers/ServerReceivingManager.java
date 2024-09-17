package server.managers;
import lib.managers.OutputManager;
import lib.utility.Message;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.SerializationUtils;

public class ServerReceivingManager {
    private final ServerConnector serverConnector;
    private ServerSendingManager serverSendingManager;

    public ServerReceivingManager(ServerConnector serverConnector, ServerSendingManager serverSendingManager) {
        this.serverConnector = serverConnector;
        this.serverSendingManager = serverSendingManager;

    }

    public Message receive() throws IOException, SocketTimeoutException {
    ByteBuffer buffer = ByteBuffer.allocate(4096);
    InetSocketAddress senderAddress = null;

    while (senderAddress == null) {
        try {
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        senderAddress = (InetSocketAddress) serverConnector.getChannel().receive(buffer);
    }

    buffer.flip(); // Переключаем в режим чтения.

    int bytesRead = buffer.remaining();
    if (bytesRead <= 0) {
        throw new IOException("No data received");
    }

    byte[] data = new byte[bytesRead];
    buffer.get(data);
    Message message = SerializationUtils.deserialize(buffer.array());

    return message;
}
}
