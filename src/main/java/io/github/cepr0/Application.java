package io.github.cepr0;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;

@SpringBootApplication
public class Application {
    
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
    @Bean
    public Java8TimeDialect java8TimeDialect() {
        return new Java8TimeDialect();
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
    
            repo.save(new User("Вася Пупкин", "vasya@demo.com", "123456"));
            repo.save(new User("Лева Задов", "leva@demo.com", "123456"));
            repo.save(new User("Коля Петров", "kolya@demo.com", "123456"));
            repo.save(new User("Сема Заднер", "sema@demo.com", "123456"));
            
        };
    }
}
