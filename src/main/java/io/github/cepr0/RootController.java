package io.github.cepr0;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static org.springframework.data.domain.Sort.Direction.ASC;

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
    public String users(Model model, @PageableDefault(size = 5, sort = "name", direction = ASC) Pageable pageable) {
        Page<User> users = repo.findAll(pageable);
        model.addAttribute("users", users);
        model.addAttribute("search", "");
    
        model.addAttribute("totalElements", users.getTotalElements());
        model.addAttribute("totalPages", users.getTotalPages());
        model.addAttribute("pageSize", pageable.getPageSize());
        model.addAttribute("current", pageable.getPageNumber());
        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next", pageable.next().getPageNumber());
    
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
    public String search(@RequestParam("search") String search, Model model) {
        List<User> users = repo.findByNameAndEmail(search);
        model.addAttribute("users", users);
        model.addAttribute("search", search);
        return "users";
    }
}
