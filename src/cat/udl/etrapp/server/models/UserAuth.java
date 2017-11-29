package cat.udl.etrapp.server.models;

public class UserAuth {
    // This class is a user plus some extra data needed only when creating and auth.

    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
