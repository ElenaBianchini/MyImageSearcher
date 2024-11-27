package org.lebi.Repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import org.lebi.Model.Utente;

@ApplicationScoped
public class UtenteRepository implements PanacheRepository<Utente> {
    
    public Utente findByUsername(String username) {
        return find("username", username).firstResult();
    }
}
