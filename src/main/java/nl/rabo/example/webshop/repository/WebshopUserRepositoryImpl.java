package nl.rabo.example.webshop.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import nl.rabo.example.webshop.entity.WebshopUser;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class WebshopUserRepositoryImpl implements WebshopUserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<WebshopUser> findByUsernameAndPassword(String username, String password) {
        String queryString = "SELECT * FROM WebshopUser WHERE username = '" + username + "' AND password = '" + password + "'";
        Query query = entityManager.createNativeQuery(queryString, WebshopUser.class);

        return query.getResultList().stream().findFirst();
    }

    @Override
    public List<WebshopUser> findAll() {
        String queryString = "SELECT * FROM WebshopUser";
        Query query = entityManager.createNativeQuery(queryString, WebshopUser.class);
        return query.getResultList();
    }
}
