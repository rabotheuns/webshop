package nl.rabo.example.webshop.controller;

import nl.rabo.example.webshop.entity.Address;
import nl.rabo.example.webshop.entity.WebshopUser;
import nl.rabo.example.webshop.repository.WebshopUserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private final WebshopUserRepository webshopUserRepository;

    public RegisterController(WebshopUserRepository webshopUserRepository) {
        this.webshopUserRepository = webshopUserRepository;
    }

    @GetMapping
    public String showRegisterPage() {
        return "register";
    }

    @PostMapping
    public String registerUser(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String street,
            @RequestParam String houseNumber,
            @RequestParam String postalCode) {
        WebshopUser user = new WebshopUser();
        user.setUsername(username);
        user.setPassword(password);

        Address address = new Address();
        address.setStreet(street);
        address.setHouseNumber(houseNumber);
        address.setPostalCode(postalCode);

        user.setAddress(address);
        webshopUserRepository.save(user);
        return "redirect:/login";
    }
}
