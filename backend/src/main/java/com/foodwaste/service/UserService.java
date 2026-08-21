package com.foodwaste.service;

import com.foodwaste.dao.UserDao;
import com.foodwaste.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    
    @Autowired
    private UserDao userDao;

    public boolean register(String username, String password, String email, String phone, String role, String address) {
        if (password.length() < 6) {
            return false;
        }
        String hashedPassword = "hash_" + password;
        User user = new User(username, hashedPassword, email, phone, role, address);
        return userDao.registerUser(user);
    }

    public User login(String email, String password) {
        String hashedPassword = "hash_" + password;
        return userDao.loginUser(email, hashedPassword);
    }

    public boolean updateProfile(int userId, String username, String phone, String address) {
        User user = userDao.getUserById(userId);
        if (user != null) {
            user.setUsername(username);
            user.setPhone(phone);
            user.setAddress(address);
            return userDao.updateProfile(user);
        }
        return false;
    }

    public boolean deleteUser(int userId) {
        return userDao.deleteUser(userId);
    }

    public User getUserDetails(int userId) {
        return userDao.getUserById(userId);
    }

    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }
}
