package cat.udl.etrapp.server.models;

import java.util.*;

public abstract class BaseUser {

    public static final Set<String> updatable = new HashSet<>(Arrays.asList(
            "password",
            "email",
            "firstName",
            "lastName",
            "avatarUrl")
    );

    private static final Map<String, String> keyMap;
    static {
        keyMap = new HashMap<>();
        keyMap.put("password_hashed", "password_hashed");
        keyMap.put("firstName", "first_name");
        keyMap.put("lastName", "last_name");
        keyMap.put("avatarUrl", "avatar_url");
    }

    public static String map_keys(String key) {
        return keyMap.getOrDefault(key, key);
    }

    private long id;
    private String username;
    private String token;
    private String firstName;
    private String lastName;
    private String email;
    private String avatarUrl;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
