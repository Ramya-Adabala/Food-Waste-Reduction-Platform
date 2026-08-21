

import java.util.List;

public class UserService {
    private UserDao userDao;

    public UserService() {
        // Tying the implementation to the interface
        this.userDao = new UserDaoImpl();
    }

    public boolean register(String username, String password, String email, String phone, String role, String address) {
        if (password.length() < 6) {
            System.out.println("Error: Password must be at least 6 characters.");
            return false;
        }

        // Simulating password hashing
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
