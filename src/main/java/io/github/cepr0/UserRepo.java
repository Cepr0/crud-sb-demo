package io.github.cepr0;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author Cepro, 2017-02-22
 */
public interface UserRepo extends CrudRepository<User, Integer> {
    List<User> findByNameContainingIgnoreCase(String name);
    
    @Query("select u from User u where lower(concat(u.name, u.email)) like concat('%', lower(?1), '%')")
    List<User> findByNameAndEmail(String search);
}
