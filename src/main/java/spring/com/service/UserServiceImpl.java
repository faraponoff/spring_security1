package spring.com.service;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.com.dao.UserDAO;
import spring.com.model.User;

import java.util.List;

@Transactional
@Service
public class UserServiceImpl implements UserService{


    private final PasswordEncoder passwordEncoder;
    private final UserDAO userDAO;

    public UserServiceImpl(PasswordEncoder passwordEncoder, UserDAO userDAO) {
        this.passwordEncoder = passwordEncoder;
        this.userDAO = userDAO;
    }

    @Override
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    @Override
    public User getUserById(Long id) {
        return userDAO.getUserById(id);
    }

    @Override
    public void deleteUser(Long id) {
        userDAO.deleteUser(id);
    }

    @Override
    public User saveUser(User user, String[] roleNames) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userDAO.saveUser(user, roleNames);
    }

    @Override
    public User updateUser(User user, String[] roleNames) {
        return userDAO.updateUser(user, roleNames);
    }

    @Override
    public User getUserByUsername(String username) {
        return userDAO.getUserByEmail(username);
    }


}
