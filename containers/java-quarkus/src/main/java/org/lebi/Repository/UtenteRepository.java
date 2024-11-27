package org.lebi.repository;

import org.lebi.entity.UtenteEntity;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UtenteRepository implements PanacheRepository<UtenteEntity> {
    
    public UtenteEntity findByUsername(String username) {
        return find("username", username).firstResult();
    }
}
