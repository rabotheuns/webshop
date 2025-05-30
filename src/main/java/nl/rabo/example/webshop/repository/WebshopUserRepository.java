package nl.rabo.example.webshop.repository;

import nl.rabo.example.webshop.entity.WebshopUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WebshopUserRepository extends JpaRepository<WebshopUser, Long> {

    Optional<WebshopUser> findByUsernameAndPassword(String username, String password);
    List<WebshopUser> findAll();
    WebshopUser save(WebshopUser user);
}
