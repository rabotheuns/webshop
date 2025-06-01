package nl.rabo.example.webshop.repository;

import nl.rabo.example.webshop.entity.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    ShoppingCart findByUserUsername(String username);
}
