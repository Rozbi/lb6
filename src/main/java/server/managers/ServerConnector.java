package server.managers;

import lib.managers.InputManager;
import lib.managers.OutputManager;
import lib.utility.Message;
import server.exeptions.InvalidInputException;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.DatagramChannel;
import java.util.Iterator;

public class ServerConnector {
    private DatagramChannel channel;
    byte[] buffer;
    private InetSocketAddress host;

    public ServerConnector(InetSocketAddress host) throws IOException {
        this.host = host;
    }

    public void connect() throws IOException {
            if (channel != null) {
                channel.close();
            }
            try {
                this.channel = DatagramChannel.open();
                channel.bind(host);
                channel.configureBlocking(false);
            } catch (UnknownHostException e) {
            }
        }
        public InetSocketAddress getHost() {
            return this.host;
        }
        public DatagramChannel getChannel() {
        return this.channel;
    }
    public boolean isConnected() {
        return this.channel.isConnected();
    }
}
