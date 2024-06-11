package lib.utility;

public class PortGetter {
    private final int clientPort = Integer.parseInt(System.getenv("CLIENT_PORT"));
    private final int serverPort = Integer.parseInt(System.getenv("SERVER_PORT"));

    public int getClientPort() {
        return clientPort;
    }
    public int getServerPort() {
        return serverPort;
    }

}
