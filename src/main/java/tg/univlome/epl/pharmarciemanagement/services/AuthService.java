package tg.univlome.epl.pharmarciemanagement.services;

import tg.univlome.epl.pharmarciemanagement.dao.UserDAO;
import tg.univlome.epl.pharmarciemanagement.exceptions.AuthenticationException;
import tg.univlome.epl.pharmarciemanagement.exceptions.DatabaseException;
import tg.univlome.epl.pharmarciemanagement.models.User;
import tg.univlome.epl.pharmarciemanagement.utils.PasswordHasher;

public class AuthService {

    private final UserDAO userDAO;

    public AuthService() {
        this.userDAO = new UserDAO();
    }

    public User login(String username, String password) throws AuthenticationException, DatabaseException {
        User user = userDAO.findByUsername(username);
        if (user == null) {
            throw new AuthenticationException("Nom d'utilisateur ou mot de passe incorrect.");
        }
        if (!PasswordHasher.check(password, user.getPasswordHash())) {
            throw new AuthenticationException("Nom d'utilisateur ou mot de passe incorrect.");
        }
        return user;
    }

    public void signup(String username, String password) throws AuthenticationException, DatabaseException {
        if (userDAO.usernameExists(username)) {
            throw new AuthenticationException("Ce nom d'utilisateur existe déjà.");
        }
        String hash = PasswordHasher.hash(password);
        User user = new User(username, hash);
        userDAO.insert(user);
    }
}
