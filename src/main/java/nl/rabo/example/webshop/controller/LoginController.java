package nl.rabo.example.webshop.controller;

import jakarta.servlet.http.HttpSession;
import nl.rabo.example.webshop.entity.Item;
import nl.rabo.example.webshop.entity.ShoppingCart;
import nl.rabo.example.webshop.entity.WebshopUser;
import nl.rabo.example.webshop.repository.ItemRepository;
import nl.rabo.example.webshop.repository.ShoppingCartRepository;
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
    private final ShoppingCartRepository shoppingCartRepository;

    public LoginController(WebshopUserRepository webshopUserRepository, ItemRepository itemRepository, ShoppingCartRepository shoppingCartRepository) {
        this.webshopUserRepository = webshopUserRepository;
        this.itemRepository = itemRepository;
        this.shoppingCartRepository = shoppingCartRepository;
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

        String userType = (String) session.getAttribute(USERNAME_ATTRIBUTE);
        if (!ADMIN_USER_TYPE.equals(userType)) {
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

    @GetMapping("/cart")
    public String showCartPage(HttpSession session, Model model) {
        if (!isLoggedIn(session)) {
            return "redirect:/login";
        }

        String username = (String) session.getAttribute(USERNAME_ATTRIBUTE);
        ShoppingCart cart = shoppingCartRepository.findByUserUsername(username);

        if (cart == null || cart.getItems().isEmpty()) {
            model.addAttribute("emptyCartMessage", "Your cart is empty.");
        } else {
            model.addAttribute("cart", cart);
            double total = cart.getItems().stream()
                    .mapToDouble(item -> item.getPrice())
                    .sum();
            model.addAttribute("total", total);
        }

        return "cart";
    }

    @PostMapping("/cart/add")
    public String addToCart(@RequestParam Long itemId, HttpSession session) {
        if (!isLoggedIn(session)) {
            return "redirect:/login";
        }

        String username = (String) session.getAttribute(USERNAME_ATTRIBUTE);
        ShoppingCart cart = shoppingCartRepository.findByUserUsername(username);
        if (cart == null) {
            cart = new ShoppingCart();
            cart.setUser(webshopUserRepository.findByUsername(username).orElseThrow());
        }

        Item item = itemRepository.findById(itemId).orElseThrow();
        cart.getItems().add(item);
        shoppingCartRepository.save(cart);

        return "redirect:/items";
    }

    @GetMapping("/checkout")
    public String showCheckoutPage(HttpSession session, Model model) {
        if (!isLoggedIn(session)) {
            return "redirect:/login";
        }

        String username = (String) session.getAttribute(USERNAME_ATTRIBUTE);
        ShoppingCart cart = shoppingCartRepository.findByUserUsername(username);
        WebshopUser user = webshopUserRepository.findByUsername(username).orElseThrow();

        model.addAttribute("cart", cart);
        model.addAttribute("address", user.getAddress());

        cart.getItems().clear();
        shoppingCartRepository.save(cart);

        return "checkout";
    }
}
