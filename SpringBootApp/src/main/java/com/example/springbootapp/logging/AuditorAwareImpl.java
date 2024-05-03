package com.example.springbootapp.logging;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        //LOGICA PER OTTENERE L'USERNAME DELL'UTENTE LOGGATO E RESTITUIRLO COME OPTIONAL
        return Optional.of("admin");
    }
}
