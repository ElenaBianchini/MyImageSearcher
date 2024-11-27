package org.lebi.Repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import org.lebi.Model.User;

@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {
    
    public User findByUsername(String username) {
        return find("username", username).firstResult();
    }
}
