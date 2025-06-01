package nl.rabo.example.webshop.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import nl.rabo.example.webshop.entity.WebshopUser;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class WebshopUserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public Optional<WebshopUser> findByUsernameAndPassword(String username, String password) {
        String queryString = "SELECT * FROM Webshop_User WHERE username = '" + username + "' AND password = '" + password + "'";
        Query query = entityManager.createNativeQuery(queryString, WebshopUser.class);

        Optional result = query.getResultList().stream().findFirst();

        if (result.isPresent()) {
            log.info("User found: {}", result.get());
            return Optional.of((WebshopUser) result.get());
        } else {
            log.warn("No user found with username: {}", username);
            return Optional.empty();
        }
    }

    public List<WebshopUser> findAll() {
        String queryString = "SELECT * FROM Webshop_User";
        Query query = entityManager.createNativeQuery(queryString, WebshopUser.class);
        return query.getResultList();
    }

    @Transactional
    public WebshopUser save(WebshopUser user) {

        String insertAddressQuery = "INSERT INTO Address (street, house_number, postal_code) VALUES (:street, :houseNumber, :postalCode)";
        Query addressQuery = entityManager.createNativeQuery(insertAddressQuery);
        addressQuery.setParameter("street", user.getAddress().getStreet());
        addressQuery.setParameter("houseNumber", user.getAddress().getHouseNumber());
        addressQuery.setParameter("postalCode", user.getAddress().getPostalCode());
        addressQuery.executeUpdate();

        String getAddressIdQuery = "SELECT MAX(id) FROM Address";
        Long addressId = ((Number) entityManager.createNativeQuery(getAddressIdQuery).getSingleResult()).longValue();


        String insertQuery = "INSERT INTO Webshop_User (username, password, role, address_id) VALUES (:username, :password, :role, :addressId)";
        Query query = entityManager.createNativeQuery(insertQuery);
        query.setParameter("username", user.getUsername());
        query.setParameter("password", user.getPassword());
        query.setParameter("role", user.getRole());
        query.setParameter("addressId", addressId);
        query.executeUpdate();
        return user;
    }

    public Optional<WebshopUser> findByUsername(String username) {
        String queryString = "SELECT * FROM Webshop_User WHERE username = '" + username + "'";
        Query query = entityManager.createNativeQuery(queryString, WebshopUser.class);

        return query.getResultList().stream().findFirst();
    }
}
