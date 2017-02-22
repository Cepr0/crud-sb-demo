package io.github.cepr0;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Cepro, 2017-02-22
 */
@Controller
public class RootController {
    
    private final UserRepo repo;
    
    @Autowired
    public RootController(UserRepo repo) {
        this.repo = repo;
    }
    
    @GetMapping("/")
    public String index(Model model) {
        Iterable<User> users = repo.findAll();
        model.addAttribute("users", users);
        return "index";
    }
    
}
