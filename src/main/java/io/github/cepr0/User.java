package io.github.cepr0;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 * @author Cepro, 2017-02-22
 */
@Entity
public class User {
    
    @Id
    @GeneratedValue
    private Integer id;
    
    private String name;
    
    private String email;
    
    private String password;
    
    private Boolean enabled;
    
    private LocalDateTime registered;
    
    private Role role;
    
    public enum Role {
        ROLE_USER, ROLE_ADMIN
    }
    
    public User() {
        this(null, null, null);
    }
    
    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.enabled = true;
        this.registered = LocalDateTime.now();
        this.role = Role.ROLE_USER;
    }
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public Boolean getEnabled() {
        return enabled;
    }
    
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
    
    public LocalDateTime getRegistered() {
        return registered;
    }
    
    public void setRegistered(LocalDateTime registered) {
        this.registered = registered;
    }
    
    public Role getRole() {
        return role;
    }
    
    public void setRole(Role role) {
        this.role = role;
    }
        
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", enabled=" + enabled +
                ", registered=" + registered +
                ", role=" + role +
                '}';
    }
}
