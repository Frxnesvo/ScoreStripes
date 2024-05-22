package com.example.springbootapp.logging;

import com.example.springbootapp.service.impl.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("auditorProvider")
@RequiredArgsConstructor
public class AuditorAwareImpl implements AuditorAware<String> {

    private final UserDetailsServiceImpl userDetailsService;

    @Override
    public Optional<String> getCurrentAuditor() {
        try {
            return Optional.of(userDetailsService.getCurrentUser().getUsername());
        } catch (Exception e) {
            return Optional.of("system");
        }
    }
}
