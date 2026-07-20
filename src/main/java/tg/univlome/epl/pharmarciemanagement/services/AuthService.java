package tg.univlome.epl.pharmarciemanagement.services;

import tg.univlome.epl.pharmarciemanagement.dao.UserDAO;
import tg.univlome.epl.pharmarciemanagement.models.User;
import tg.univlome.epl.pharmarciemanagement.utils.PasswordHasher;

public class AuthService {

    private final UserDAO userDAO;

    public AuthService() {
        this.userDAO = new UserDAO();
    }

    public User login(String username, String password) {
        User user = userDAO.findByUsername(username);
        if (user == null) {
            return null;
        }
        if (!PasswordHasher.check(password, user.getPasswordHash())) {
            return null;
        }
        return user;
    }

    public boolean signup(String username, String password) {
        if (userDAO.usernameExists(username)) {
            return false;
        }
        String hash = PasswordHasher.hash(password);
        User user = new User(username, hash);
        userDAO.insert(user);
        return true;
    }
}
