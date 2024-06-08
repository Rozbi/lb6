package server.managers;
import lib.managers.OutputManager;
import lib.utility.Message;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Arrays;

public class ServerReceivingManager {
    private final OutputManager outputManager;
    private DatagramChannel datagramChannel;
    private final ServerConnector serverConnector;
    private InetSocketAddress host;
    private byte[] buffer;
    private final int DATA_SIZE = 1024;

    public ServerReceivingManager(ServerConnector serverConnector, OutputManager outputManager) {
        this.outputManager = outputManager;
        this.serverConnector = serverConnector;
    }

    public Message receive(JsonManager jsonManager) throws IOException, InterruptedException {
        for(;;){
            try{
                if (serverConnector.getChannel()==null) continue;
                if (!serverConnector.getChannel().isOpen()){
                    serverConnector.connect();
                    Thread.sleep(3000);
                    return null;
                }
                ByteBuffer buffer = ByteBuffer.allocate(4096);
                SocketAddress sender = serverConnector.getChannel().receive(buffer);
                    if(sender!= null){
                        buffer.flip();
                        ByteArrayInputStream bais = new ByteArrayInputStream(buffer.array(), 0, buffer.limit());
                        ObjectInputStream ois = new ObjectInputStream(bais);
                        Message message = (Message) ois.readObject();
                        outputManager.println(String.valueOf(message));
                        return message;
                    }
            } catch(Exception e){
                Thread.sleep(3000);
                serverConnector.getChannel().close();
                serverConnector.connect();
            }
        }
    }
}
