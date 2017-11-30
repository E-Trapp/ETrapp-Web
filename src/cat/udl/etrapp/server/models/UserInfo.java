package cat.udl.etrapp.server.models;

public class UserInfo {

    private long id;
    private String username;

    public static UserInfo fromUser(User user) {
        if (user == null) return null;
        UserInfo userInfo = new UserInfo();
        userInfo.setId(user.getId());
        userInfo.setUsername(user.getUsername());
        return userInfo;
    }

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

}
