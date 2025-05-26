package nl.rabo.example.webshop.controller;

import jakarta.servlet.http.HttpSession;
import nl.rabo.example.webshop.entity.WebshopUser;
import nl.rabo.example.webshop.repository.ItemRepository;
import nl.rabo.example.webshop.repository.WebshopUserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class LoginController {

    public static final String ADMIN_USER_TYPE = "admin";
    public static final String USER_TYPE_ATTRIBUTE = "usertype";
    public static final String USERNAME_ATTRIBUTE = "username";
    public static final String USERS_ATTRIBUTE = "users";
    public static final String ITEMS_ATTRIBUTE = "items";

    private final WebshopUserRepository webshopUserRepository;
    private final ItemRepository itemRepository;

    public LoginController(WebshopUserRepository webshopUserRepository, ItemRepository itemRepository) {
        this.webshopUserRepository = webshopUserRepository;
        this.itemRepository = itemRepository;
    }

    @GetMapping("/login")
    public String showLoginPage(HttpSession session) {
        if (session.getAttribute(USER_TYPE_ATTRIBUTE) != null) {
            String userType = (String) session.getAttribute(USER_TYPE_ATTRIBUTE);
            if (ADMIN_USER_TYPE.equals(userType)) {
                return "redirect:/admin";
            }
            return "redirect:/items";
        }
        return "login";
    }

    @PostMapping("/login")
    public String handleLogin(@RequestParam String username, @RequestParam String password, HttpSession session, Model model) {
        Optional<WebshopUser> user = webshopUserRepository.findByUsernameAndPassword(username, password);

        if (user.isPresent()) {
            session.setAttribute(USERNAME_ATTRIBUTE, username);
            session.setAttribute(USER_TYPE_ATTRIBUTE, user.get().getRole());
            if (ADMIN_USER_TYPE.equals(username)) {
                return "redirect:/admin";
            }
            return "redirect:/items";
        }

        model.addAttribute("error", "Invalid username or password");
        return "login";
    }

    @GetMapping("/admin")
    public String showAdminPage(HttpSession session, Model model) {
        if (!isLoggedIn(session)) {
            return "redirect:/login";
        }

        String username = (String) session.getAttribute(USERNAME_ATTRIBUTE);
        if (!ADMIN_USER_TYPE.equals(username)) {
            return "redirect:/login";
        }

        model.addAttribute(USERS_ATTRIBUTE, webshopUserRepository.findAll());
        return "admin";
    }

    @GetMapping("/items")
    public String showItemsPage(HttpSession session, Model model) {
        if (!isLoggedIn(session)) {
            return "redirect:/login";
        }

        String username = (String) session.getAttribute(USERNAME_ATTRIBUTE);
        if (ADMIN_USER_TYPE.equals(username)) {
            return "redirect:/admin";
        }

        model.addAttribute(ITEMS_ATTRIBUTE, itemRepository.findAll());
        return "items";
    }

    private boolean isLoggedIn(HttpSession session) {
        return session.getAttribute(USERNAME_ATTRIBUTE) != null;
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
