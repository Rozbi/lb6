package lib.utility;

import java.io.Serializable;

public class User implements Serializable {
    private long id;
    private String login;
    private String password;
    public User(long id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }
    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String getPassword() {
        return password;
    }
}
