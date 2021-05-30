package spring.com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.com.dao.UserDAO;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserDAO userDAO;


    @Autowired
    public UserDetailsServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserDetails user = userDAO.getUserByUsername(s);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("Юзера %s не существует", s));
        }
        return new User(user.getUsername(), user.getPassword(), user.getAuthorities());
    }

}
