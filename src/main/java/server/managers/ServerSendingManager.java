package server.managers;

import lib.managers.OutputManager;
import lib.utility.Message;
import lib.utility.PortGetter;
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
      public void sendMessage(Message message) throws InvalidInputException, IOException {
          try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
               ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
            serverConnector.connect();
            objectOutputStream.writeObject(message);
            if (message == null){
                return;
            }
            serverConnector.getChannel().send(ByteBuffer.wrap(byteArrayOutputStream.toByteArray()), message.getAddress());
        } catch (IOException e) {
            throw new RuntimeException("Serialization error", e);
        }
      }
}
