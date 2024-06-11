package server.managers;
import lib.managers.OutputManager;
import lib.utility.Message;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Arrays;

public class ServerReceivingManager {
    private final ServerConnector serverConnector;
    private ServerSendingManager serverSendingManager;

    public ServerReceivingManager(ServerConnector serverConnector, ServerSendingManager serverSendingManager) {
        this.serverConnector = serverConnector;
        this.serverSendingManager = serverSendingManager;

    }

    public Message receive() throws IOException, InterruptedException {
        for(;;){
            try{
                ByteBuffer buffer = ByteBuffer.allocate(4096);
                InetSocketAddress sender = (InetSocketAddress) serverConnector.getChannel().receive(buffer);
                    if(sender!= null){
                        buffer.flip();
                        ByteArrayInputStream bais = new ByteArrayInputStream(buffer.array(), 0, buffer.limit());
                        JsonManager jsonManager = new JsonManager();
                        Message message = jsonManager.gson.fromJson(new InputStreamReader(bais), Message.class);
                        message.setAddress(sender);
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
