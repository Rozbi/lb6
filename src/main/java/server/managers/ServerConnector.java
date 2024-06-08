package server.managers;

import lib.managers.InputManager;
import lib.managers.OutputManager;
import server.exeptions.InvalidInputException;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.channels.DatagramChannel;

public class ServerConnector {
    private DatagramChannel channel;
    private InputManager inputManager;
    private OutputManager outputManager;
    byte[] buffer;
    private InetSocketAddress host;

    public ServerConnector(InputManager inputManager, InetSocketAddress host, OutputManager outputManager) throws IOException {
        this.inputManager = inputManager;
        this.host = host;
        this.outputManager = outputManager;
    }

    public DatagramChannel getChannel() {
        return channel;
    }

    public boolean isConnected() {
        return channel.isConnected();
    }

    public void connect() throws IOException {
        for (; ; ) {
            if (channel != null) {
                channel.close();
            }
            try {
                this.channel = DatagramChannel.open();
                channel.bind(host);
                channel.configureBlocking(false);
                channel.connect(host);
            } catch (UnknownHostException e) {
                outputManager.printerr("Ошибка! Хост не найден!");
            }
            }
        }
}
