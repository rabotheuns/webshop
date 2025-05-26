package nl.rabo.example.webshop.controller;

import jakarta.servlet.http.HttpSession;
import nl.rabo.example.webshop.entity.Comment;
import nl.rabo.example.webshop.entity.Item;
import nl.rabo.example.webshop.repository.CommentRepository;
import nl.rabo.example.webshop.repository.ItemRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/comments")
public class CommentController {

    private final CommentRepository commentRepository;
    private final ItemRepository itemRepository;

    public CommentController(CommentRepository commentRepository, ItemRepository itemRepository) {
        this.commentRepository = commentRepository;
        this.itemRepository = itemRepository;
    }

    @GetMapping("/{itemId}")
    public String showCommentsPage(@PathVariable Long itemId, Model model, HttpSession session) {
        if (session.getAttribute("username") == null) {
            return "redirect:/login";
        }

        Optional<Item> item = itemRepository.findById(itemId);
        if (item.isEmpty()) {
            return "redirect:/items";
        }

        List<Comment> comments = commentRepository.findByItem(item.get());
        model.addAttribute("item", item.get());
        model.addAttribute("comments", comments);
        return "comments";
    }

    @PostMapping("/{itemId}")
    public String addComment(@PathVariable Long itemId, @RequestParam String content, HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/login";
        }

        Optional<Item> item = itemRepository.findById(itemId);
        if (item.isPresent()) {
            Comment comment = new Comment();
            comment.setItem(item.get());
            comment.setUsername(username);
            comment.setContent(content);
            commentRepository.save(comment);
        }
        return "redirect:/comments/" + itemId;
    }
}
