package io.github.cepr0;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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

    @GetMapping("/user/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("user", repo.findOne(id));
        return "edit";
    }
    
}
