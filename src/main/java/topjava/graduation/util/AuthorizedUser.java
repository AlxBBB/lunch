package topjava.graduation.util;

import topjava.graduation.model.User;
import topjava.graduation.to.UserTo;

import java.util.Collections;

public class AuthorizedUser extends org.springframework.security.core.userdetails.User {
    private static final long serialVersionUID = 1L;

    private UserTo userTo;

    public AuthorizedUser(User user) {
        super(user.getEmail(), user.getPassword(), true, true, true, true,
                Collections.singletonList(user.getRole()));
        this.userTo = new UserTo(user.getId(), user.getName(), user.getEmail(), user.getPassword());
    }


    public int getId() {
        return userTo.getId();
    }

    @Override
    public String toString() {
        return userTo.toString();
    }
}