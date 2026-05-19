package com.travelbooking.service;

import com.travelbooking.dao.UserDAO;
import com.travelbooking.model.User;
import com.travelbooking.util.PasswordUtil;
import org.bson.types.ObjectId;

import java.util.List;

public class UserService {

    private final UserDAO userDAO = new UserDAO();

    public User register(String firstName, String lastName, String email, String password, String phone) {
        if (userDAO.emailExists(email)) return null;
        String hashed = PasswordUtil.hash(password);
        User user = new User(firstName, lastName, email, hashed, phone);
        ObjectId id = userDAO.insert(user);
        user.setId(id);
        return user;
    }

    public User login(String email, String password) {
        User user = userDAO.findByEmail(email);
        if (user != null && PasswordUtil.verify(password, user.getPassword())) {
            return user;
        }
        return null;
    }

    public User findById(String id) { return userDAO.findById(id); }
    public User findByEmail(String email) { return userDAO.findByEmail(email); }
    public List<User> findAll() { return userDAO.findAll(); }
    public long count() { return userDAO.count(); }

    public boolean updateProfile(User user) { return userDAO.update(user); }

    public boolean changePassword(ObjectId userId, String oldPassword, String newPassword) {
        User user = userDAO.findById(userId);
        if (user != null && PasswordUtil.verify(oldPassword, user.getPassword())) {
            return userDAO.updatePassword(userId, PasswordUtil.hash(newPassword));
        }
        return false;
    }

    public boolean delete(String id) { return userDAO.delete(id); }
    public boolean emailExists(String email) { return userDAO.emailExists(email); }
}
