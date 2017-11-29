package cat.udl.etrapp.server.models;

public class User {

    private long id;
    private String username;
    private String token;

    public User() {
    }

    public User(long id, String username, String token) {
        this.id = id;
        this.username = username;
        this.token = token;
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getToken() {
        return token;
    }
}
