package cat.udl.etrapp.server.models;

public class SessionToken {

    private final String plainToken;
    private final String hashedToken;

    public SessionToken(String plainToken, String hashedToken) {
        this.plainToken = plainToken;
        this.hashedToken = hashedToken;
    }

    public String getPlainToken() {
        return plainToken;
    }

    public String getHashedToken() {
        return hashedToken;
    }
}
