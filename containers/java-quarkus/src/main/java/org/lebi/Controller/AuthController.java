package org.lebi.controller;

import javax.validation.Valid;

import org.lebi.dto.*;
import org.lebi.service.AuthService;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthController {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Inject
    AuthService authService;
    
    @POST
    @Path("/signup")
    public Response signup(@Valid SignupRequest signupRequest) {
        boolean ok = authService.newUser(signupRequest);
        if (ok) {
            return Response.status(Response.Status.CREATED).build();
        } else {
            return Response.status(Response.Status.CONFLICT).entity("Username già in uso").build();
        }
    }

    @POST
    @Path("/login")
    public Response login(@Valid LoginRequest loginRequest) {
        try {
            String token = authService.login(loginRequest);
            // return Response.ok().header("Authorization", "Bearer " + token).build();
            return Response.ok(token).build();
        } catch (Exception e) {
            logger.error("ECCEZIONE: {}", e.toString());
            return Response.status(Response.Status.UNAUTHORIZED).entity("Credenziali non valide").build();
        }
    }

}
