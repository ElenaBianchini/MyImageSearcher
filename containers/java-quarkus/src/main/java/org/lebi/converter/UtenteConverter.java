package org.lebi.converter;

import org.lebi.entity.UtenteEntity;
import org.lebi.model.Utente;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UtenteConverter implements IUtenteConverter {

    @Override
    public UtenteEntity utenteBO2UtenteEntity(Utente bo) {
        if(bo == null) {
            return null;
        }

        UtenteEntity entity = new UtenteEntity();
        entity.setId(bo.getId());
        entity.setUsername(bo.getUsername());
        entity.setName(bo.getName());
        entity.setSurname(bo.getSurname());
        entity.setEmail(bo.getEmail());
        entity.setPassword(bo.getPassword());
        entity.setDescription(bo.getDescription());
        entity.setImage(bo.getImage());
        entity.setActive(bo.isActive());

        return entity;
    }

    @Override
    public Utente utenteEntity2UtenteBO(UtenteEntity entity) {
        if(entity == null) {
            return null;
        }

        Utente bo = new Utente();
        bo.setId(entity.getId());
        bo.setUsername(entity.getUsername());
        bo.setName(entity.getName());
        bo.setSurname(entity.getSurname());
        bo.setEmail(entity.getEmail());
        bo.setPassword(entity.getPassword());
        bo.setDescription(entity.getDescription());
        bo.setImage(entity.getImage());
        bo.setActive(entity.isActive());

        return bo;
    }
    
}
