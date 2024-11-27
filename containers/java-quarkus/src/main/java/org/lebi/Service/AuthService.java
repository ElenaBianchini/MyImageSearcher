package org.lebi.Service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import org.lebi.DTO.LoginRequest;
import org.lebi.DTO.SignupRequest;
import org.lebi.Model.*;
import org.lebi.Repository.UserRepository;
import org.mindrot.jbcrypt.BCrypt;


@ApplicationScoped
public class AuthService {
    @Inject
    UserRepository userRepository;

    @Transactional
    public boolean newUser(SignupRequest request) {
        if (userRepository.findByUsername(request.getUsername()) != null) {
            return false; 
        }

        // Crittografa la password
        String hashedPassword = BCrypt.hashpw(request.getPassword(), BCrypt.gensalt());

        // Crea un nuovo utente e salva nel database
        User utente = new User();
        utente.setUsername(request.getUsername());
        utente.setPassword(hashedPassword);
        utente.setEmail(request.getEmail());
        userRepository.persist(utente);

        return true;
    }

    public User login(LoginRequest request){
        User utente = userRepository.findByUsername(request.getUsername());
        return utente; 
    }
}
