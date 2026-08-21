package com.foodwaste.dao;

import com.foodwaste.model.User;
import java.util.List;

public interface UserDao {
    boolean registerUser(User user);
    User loginUser(String email, String passwordHash);
    User getUserById(int userId);
    boolean updateProfile(User user);
    boolean deleteUser(int userId);
    List<User> getAllUsers();
}
