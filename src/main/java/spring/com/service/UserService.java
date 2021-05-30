package spring.com.service;

import spring.com.model.User;

import java.util.List;

public interface UserService {


    List<User> getAllUsers();

    User getUserById(Long id);

    void deleteUser(Long id);

    User saveUser(User user);

    User updateUser(User user);

    User getUserByUsername(String username);


}
