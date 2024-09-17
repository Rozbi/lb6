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

public class ServerSendingManager {
    private final ServerConnector serverConnector;

    public ServerSendingManager(ServerConnector serverConnector) {
        this.serverConnector = serverConnector;
    }
    //insert thread
      public void sendMessage(Message message) throws InvalidInputException, IOException {
          ByteBuffer buffer = ByteBuffer.wrap(SerializationUtils.serialize(message));
          serverConnector.getChannel().send(buffer, message.getAddress());
    }
}
