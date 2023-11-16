package com.dashko.api.security;

import com.dashko.api.security.jwt.TokenUtils;
import com.dashko.common.exception.AuthException;
import com.dashko.common.service.person.IPersonService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Component
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SecurityService {

    TokenUtils tokenUtils;

    IPersonService personService;

    PasswordEncoder passwordEncoder;

    public String authenticate(String email, String password) {
        return Optional.of(personService.getPersonByEmail(email))
                .map(user -> {
                    if (!user.isEnabled()) {
                        return new AuthException("Account disabled", "PROSELYTE_USER_ACCOUNT_DISABLED");
                    }

                    if (!passwordEncoder.matches(password, user.getPassword())) {
                        return new AuthException("Invalid password", "PROSELYTE_INVALID_PASSWORD");
                    }

                    return tokenUtils.generateToken(user);
                })
                .orElseThrow(() -> new AuthException("Invalid username", "PROSELYTE_INVALID_USERNAME")).toString();
    }
}
