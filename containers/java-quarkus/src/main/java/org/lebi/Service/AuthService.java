package org.lebi.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.core.Response;

import java.util.HashSet;
import java.util.Set;

import org.lebi.converter.UtenteConverter;
import org.lebi.dto.LoginRequest;
import org.lebi.dto.SignupRequest;
import org.lebi.entity.*;
import org.lebi.model.Utente;
import org.lebi.repository.UtenteRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.smallrye.jwt.build.Jwt;

@ApplicationScoped
public class AuthService {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Inject
    UtenteRepository utenteRepository;

    @Inject
    UtenteConverter utenteConverter;

    @Transactional
    public boolean newUser(SignupRequest request) {
        if (utenteRepository.findByUsername(request.getUsername()) != null) {
            return false;
        }

        // Crittografa la password
        String hashedPassword = BCrypt.hashpw(request.getPassword(), BCrypt.gensalt());

        // Crea un nuovo utente e salva nel database
        UtenteEntity utente = new UtenteEntity();
        utente.setUsername(request.getUsername());
        utente.setPassword(hashedPassword);
        utente.setEmail(request.getEmail());
        utenteRepository.persist(utente);

        return true;
    }

    public String login(LoginRequest request) throws NotAuthorizedException {
        logger.info("REQUEST: {}", request);

        // 1. Cerca l'utente nel database
        Utente utente = utenteConverter.utenteEntity2UtenteBO(utenteRepository.findByUsername(request.getUsername()));

        // 2. Verifica password
        if (utente == null || !BCrypt.checkpw(request.getPassword(), utente.getPassword())) {
            throw new NotAuthorizedException("Credenziali non valide!");
        }

        // 3. Genera token JWT
        Set<String> roles = new HashSet<>();
        roles.add("USER");
        String token = Jwt.issuer("lellibibbi")
                            .subject(utente.getUsername())
                            .groups(roles)
                            .sign();
        return token;
    }
}
