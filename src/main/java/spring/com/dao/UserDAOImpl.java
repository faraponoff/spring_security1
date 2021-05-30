package spring.com.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import spring.com.model.User;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@Transactional
@Repository("userDAO")
public class UserDAOImpl implements UserDAO {


    private EntityManager entityManager;

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public List getAllUsers() {
        return entityManager.createQuery("SELECT u FROM User u").getResultList();
    }

    @Override
    public User getUserById(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public User getUserByUsername(String username) {
        return entityManager.createQuery("SELECT u FROM User u WHERE u.email= :email", User.class)
                .setParameter("email", username.toLowerCase())
                .setHint("javax.persistence.fetchgraph", username)
                .getSingleResult();
    }

    @Override
    public void deleteUser(Long id) {
        entityManager.remove(entityManager.find(User.class, id));
    }

    @Override
    public User saveUser(User user) {

        entityManager.persist(user);

        return user;
    }

    @Override
    public User updateUser(User user) {
        entityManager.merge(user);
        return user;
    }
}
