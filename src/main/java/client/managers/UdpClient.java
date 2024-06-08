package client.managers;

import lib.managers.InputManager;
import server.exeptions.InvalidInputException;
import lib.managers.OutputManager;

import java.io.IOException;
import java.net.*;
import java.nio.channels.DatagramChannel;

public class UdpClient {
    private DatagramChannel channel;
    private InputManager inputManager;
    private OutputManager outputManager;
    byte[] buffer;
    private InetSocketAddress host;

    public UdpClient(InputManager inputManager, InetSocketAddress host, OutputManager outputManager) throws IOException {
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

    public DatagramChannel connect() throws IOException {
        host = new InetSocketAddress("127.0.0.1", (int) (Math.random() * 10000));
        for (; ; ) {
            if (channel != null) {
                channel.close();
            }
            try {
                this.channel = DatagramChannel.open();
                channel.bind(host);
                channel.configureBlocking(false);
                channel.connect(host);
                return channel;
            } catch (UnknownHostException e) {
                outputManager.printerr("Ошибка! Хост не найден!");
            } catch (IOException e) {
                try {
//                    logger.info("TCP server: " + e.getMessage());
                    channel.close();
                    channel.connect(newIP());
                    Thread.sleep(3000);
                } catch (InvalidInputException ex) {
                    outputManager.printerr("Ошибка ввода данных");
                } catch (InterruptedException ex) {
                    outputManager.printerr("Ошибка повторного подключения");
                }
            }
        }
    }

//    public void send() {
//        while (true) {
//            try {
//                buffer = inputManager.read().getBytes();
//                outputManager.println("server says that u said: " + new String(packet.getData(), 0, packet.getLength()));
//            } catch (InvalidInputException e) {
//                outputManager.printerr("Ошибка чтения");
//            } catch (IOException e) {
//                outputManager.printerr("Ошибка отправки");
//            }
//        }
//    }

    public InetSocketAddress newIP() throws InvalidInputException {
        outputManager.println("Напишите новый хост (address:port):");
        while (true) {
            var adr = inputManager.read();
            if (adr.contains(":")) {
                try {
                    var port = Integer.parseInt(adr.split(":")[1]);
                    getChannel().close();
                    return new InetSocketAddress(adr.split(":")[0], port);
                } catch (Exception e1) {
                    outputManager.println("Попробуйте снова");
                }
            } else {
                try {
                    getChannel().close();
                    return new InetSocketAddress(adr, host.getPort());
                } catch (Exception e) {
                    outputManager.println("Неправильный ввод. Напишите новый хост (address:port):");
                }
            }
            return null;
        }
    }

    public InetSocketAddress getHost() {
        return host;
    }

    public int getPort() {
        return host.getPort();
    }
}