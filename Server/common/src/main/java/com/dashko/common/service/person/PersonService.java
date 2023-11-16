package com.dashko.common.service.person;

import com.dashko.common.models.Person;
import com.dashko.common.models.enums.Role;
import com.dashko.common.repository.PersonRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class PersonService implements IPersonService{

    PersonRepository personRepository;

    PasswordEncoder passwordEncoder;

    @Override
    public Person getPersonById(Long id) {
        return personRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Person getPersonByEmail(String email) {
        return personRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Person getPersonByUsername(String username) {
        return personRepository.findByUsername(username).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Person registerUser(Person person) {
        return personRepository.save(
                person.toBuilder()
                        .password(passwordEncoder.encode(person.getPassword()))
                        .role(Collections.singleton(Role.USER))
                        .enabled(true)
                        .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                        .updatedAt(Timestamp.valueOf(LocalDateTime.now()))
                        .build()
        );

    }
}
