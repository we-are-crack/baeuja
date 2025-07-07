package xyz.baeuja.api.helper;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import xyz.baeuja.api.user.domain.LoginType;
import xyz.baeuja.api.user.domain.User;

@Component
@Transactional
public class TestDataHelper {

    @Autowired
    EntityManager em;

    public void clearAll() {
        clearUser();
    }

    public void clearUser() {
        em.createQuery("delete from User").executeUpdate();
    }

    public User saveGuestUser(String nickname, String locale, String timezone) {
        User user = new User(nickname, locale, timezone, LoginType.GUEST);
        em.persist(user);
        return user;
    }

    public User saveGoogleUser(String email, String nickname, String locale, String timezone) {
        User user = new User(nickname, locale, timezone, LoginType.GOOGLE);
        user.convertToGoogleAccount(email);
        em.persist(user);
        return user;
    }
}
