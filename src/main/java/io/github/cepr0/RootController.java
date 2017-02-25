package io.github.cepr0;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
    public String users(Model model) {
        Iterable<User> users = repo.findAll();
        model.addAttribute("users", users);
        model.addAttribute("search", "");
        return "users";
    }

    @GetMapping("/user/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("user", repo.findOne(id));
        return "edit";
    }
    
    @PostMapping("/user")
    public String save(User user) {
        repo.save(user);
        return "redirect:/";
    }
    
    @GetMapping("/user/delete/{id}")
    public String delete(@PathVariable Integer id) {
        repo.delete(id);
        return "redirect:/";
    }
    
    @GetMapping("/user")
    public String create(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "edit";
    }
    
    @PostMapping("/filter")
    public String search(HttpServletRequest req, Model model) {
        String search = req.getParameter("search");
        List<User> users = repo.findByNameAndEmail(search);
        model.addAttribute("users", users);
        model.addAttribute("search", search);
        return "users";
    }
}
