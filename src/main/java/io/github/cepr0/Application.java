package io.github.cepr0;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {
    
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
    @Bean
    public CommandLineRunner demo(UserRepo repo) {
        return args -> {
    
            Iterable<User> users = repo.findAll();
            if (users.iterator().hasNext()) {
                
                for (User user : users) {
                    System.out.println(user.toString());
                }
                
                return;
            }
    
            User u1 = repo.save(new User("Вася Пупкин", "vasya@demo.com", "123456"));
            u1.setRole(User.Role.ROLE_ADMIN);
            repo.save(u1);
            repo.save(new User("Лева Задов", "leva@demo.com", "123456"));
            User u2 = repo.save(new User("Коля Петров", "kolya@demo.com", "123456"));
            u2.setEnabled(false);
            u2.setRole(User.Role.ROLE_ADMIN);
            repo.save(u2);
            repo.save(new User("Сема Заднер", "sema@demo.com", "123456"));
            
        };
    }
}
