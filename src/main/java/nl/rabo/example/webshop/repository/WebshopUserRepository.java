package nl.rabo.example.webshop.repository;

import nl.rabo.example.webshop.entity.WebshopUser;

import java.util.List;
import java.util.Optional;

public interface WebshopUserRepository {

    Optional<WebshopUser> findByUsernameAndPassword(String username, String password);
    List<WebshopUser> findAll();
}
