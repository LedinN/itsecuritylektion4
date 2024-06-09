package dev.nick.itsecuritylektion4;

import dev.nick.itsecuritylektion4.persistence.MyUser;
import dev.nick.itsecuritylektion4.persistence.UserRepository;
import dev.nick.itsecuritylektion4.twofactorauth.QRCode;
import jakarta.validation.Valid;
import org.jboss.aerogear.security.otp.api.Base32;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Arrays;
import java.util.List;

@org.springframework.stereotype.Controller
public class MyController {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final QRCode qrCode;

    public MyController(PasswordEncoder passwordEncoder, UserRepository userRepository, QRCode qrCode) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.qrCode = qrCode;
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new UserDTO());

        List<String> listProfession = Arrays.asList("Developer", "Tester", "Architect");
        model.addAttribute("listProfession", listProfession);
        return "register_form";
    }

    @PostMapping("/register")
    public String submitForm(@Valid @ModelAttribute("user") UserDTO userDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            System.out.println("ERRORS");
            return "register_form";
        }
        MyUser user = new MyUser();
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRole("USER");
        user.setSecret(Base32.random());
        userRepository.save(user);
        model.addAttribute("qrcode", qrCode.dataUrl(user));

        return "qrcode";

    }

    @GetMapping("/admin")
    public String admin() {
        return "Hello from Admin";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

}
