package lib.utility;

import java.io.Serializable;
import java.net.InetSocketAddress;

public class Message implements Serializable {
    String name;
    Serializable entity;
    InetSocketAddress address;
    public Message(String name, Serializable entity, InetSocketAddress address) {
        this.name = name;
        this.entity = entity;
        this.address = address;
    }
    public Message(String name, Serializable entity) {
        this.name = name;
        this.entity = entity;
    }

    @Override
    public String toString() {
        return "Message{" +
                "name='" + name + '\'' +
                ", entity=" + entity + ", address=" + address +
                '}';
    }

    public String getName() {
        return name;
    }

    public Serializable getEntity() {
        return entity;
    }
    public InetSocketAddress getAddress() {
        return address;
    }
    public void setAddress(InetSocketAddress address) {
        this.address = address;
    }
}
