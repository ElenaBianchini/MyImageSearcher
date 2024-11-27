package org.lebi.converter;

import org.lebi.entity.UtenteEntity;
import org.lebi.model.Utente;

public interface IUtenteConverter {
    UtenteEntity utenteBO2UtenteEntity(Utente bo);

    Utente utenteEntity2UtenteBO(UtenteEntity entity);
}
