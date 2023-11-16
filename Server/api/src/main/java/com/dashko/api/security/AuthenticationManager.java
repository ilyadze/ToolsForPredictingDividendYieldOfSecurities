package com.dashko.api.security;

import com.dashko.common.exception.UnauthorizedException;
import com.dashko.common.models.Person;
import com.dashko.common.service.person.PersonService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class AuthenticationManager implements org.springframework.security.authentication.AuthenticationManager {

    PersonService personService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();
        return Optional.of(personService.getPersonById(principal.getId()))
                .filter(Person::isEnabled)
                .map(user -> authentication)
                .orElseThrow(() -> new UnauthorizedException("User disabled"));
    }
}