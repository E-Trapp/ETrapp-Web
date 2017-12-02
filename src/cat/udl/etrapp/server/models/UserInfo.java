package cat.udl.etrapp.server.models;

public class UserInfo extends BaseUser {

    public static UserInfo fromUser(User user) {
        if (user == null) return null;
        UserInfo userInfo = new UserInfo();
        userInfo.setId(user.getId());
        userInfo.setUsername(user.getUsername());
        userInfo.setAvatarUrl(user.getAvatarUrl());
        userInfo.setEmail(user.getEmail());
        userInfo.setFirstName(user.getFirstName());
        userInfo.setLastName(user.getLastName());
        return userInfo;
    }

}
