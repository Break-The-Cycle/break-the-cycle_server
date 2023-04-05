package brave.btc.repository;

import brave.btc.domain.User;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final EntityManager em;

    public void save(User User) {
        em.persist(User);
    }

    public User findOne(Long id) {
        return em.find(User.class, id);
    }

    public List<User> findAll() {
        return em.createQuery("select u from User u", User.class)
                .getResultList();
    }

    public User findByName(String name) {
        return em.createQuery("select u from User u where u.name= :name", User.class)
                .setParameter("name", name)
                .getResultList()
                .stream()
                .findFirst()
                .orElse(null);
    }

    public Optional<User> findByloginId(String loginId) {
        User user = em.createQuery("select u from User u where u.loginId =:loginId", User.class)
            .setParameter("loginId", loginId)
            .getResultList()
            .stream()
            .findFirst()
            .orElse(null);
        return Optional.ofNullable(user);
    }
}
