package server.managers;
import lib.managers.OutputManager;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Arrays;

public class ServerReceivingManager {
    private final OutputManager outputManager;
    private DatagramChannel datagramChannel;
    private final ServerConnector serverConnector;
    private byte[] buffer;
    private final int DATA_SIZE = 1024;

    public ServerReceivingManager(ServerConnector serverConnector, OutputManager outputManager) {
        this.outputManager = outputManager;
        this.serverConnector = serverConnector;
    }

    public byte[] receive(JsonManager jsonManager) throws IOException, InterruptedException {
        for(;;){
            try{
                if (serverConnector.getChannel()==null) continue;
                if (!serverConnector.getChannel().isOpen()){
                    serverConnector.connect();
                    Thread.sleep(3000);
                    continue;
                }
                ByteBuffer byteBuffer = ByteBuffer.allocate(DATA_SIZE);
                var readBytes = serverConnector.getChannel().read(byteBuffer);
                if(readBytes == 0) {
                    Thread.sleep(50);
                    continue;
                }
                if(readBytes == -1)
                    serverConnector.getChannel().close();

                ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );
                outputStream.write(buffer);
                outputStream.write(Arrays.copyOf(byteBuffer.array(), byteBuffer.array().length - 1));
                buffer = outputStream.toByteArray();
                // reached the end of the object being sent
                if (byteBuffer.array()[readBytes - 1] == 1) {
                    return buffer;
                }
                byteBuffer.clear();
            } catch(Exception e){
                Thread.sleep(3000);
                serverConnector.getChannel().close();
                serverConnector.connect();
                continue;
            }
        }
    }
}
