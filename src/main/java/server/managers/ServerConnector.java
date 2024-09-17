package server.managers;

import java.nio.channels.Selector;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.channels.SelectionKey;
import java.nio.channels.DatagramChannel;

public class ServerConnector {
    private DatagramChannel channel;
    private Selector selector;
    byte[] buffer;
    private InetSocketAddress host;

    public ServerConnector(InetSocketAddress host) throws IOException {
        this.host = host;
    }

    public void connect() throws IOException {
            selector = Selector.open();
            if (channel != null) {
                channel.close();
            }
            try {
                this.channel = DatagramChannel.open();
                channel.configureBlocking(false);
                channel.bind(host);
                channel.register(selector, SelectionKey.OP_ACCEPT);
            } catch (UnknownHostException | IllegalArgumentException e) {
            }
        }
        public InetSocketAddress getHost() {
            return this.host;
        }
        public DatagramChannel getChannel() {
            return this.channel;
        }
        public Selector getSelector(){
            return selector;
            }
    public boolean isConnected() {
        return this.channel.isConnected();
    }
}
