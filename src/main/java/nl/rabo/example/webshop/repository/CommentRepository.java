package nl.rabo.example.webshop.repository;

import nl.rabo.example.webshop.entity.Comment;
import nl.rabo.example.webshop.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByItem(Item item);
}
