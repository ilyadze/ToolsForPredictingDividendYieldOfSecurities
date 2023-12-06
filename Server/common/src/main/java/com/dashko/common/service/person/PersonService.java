package com.dashko.common.service.person;

import com.dashko.common.models.Person;
import com.dashko.common.models.enums.Role;
import com.dashko.common.repository.PersonRepository;
import com.dashko.common.service.mail.IMailService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class PersonService implements IPersonService{

    PersonRepository personRepository;

    PasswordEncoder passwordEncoder;

    IMailService mailService;

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
        Person newPerson = personRepository.save(
                person.toBuilder()
                        .password(passwordEncoder.encode(person.getPassword()))
                        .role(Collections.singleton(Role.USER))
                        .enabled(true)
                        .activationCode(UUID.randomUUID().toString())
                        .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                        .updatedAt(Timestamp.valueOf(LocalDateTime.now()))
                        .build()
        );
        String message = String.format(
                "Hello, %s! \n" +
                        "Welcome to Sweater. Please, visit next link: http://localhost:4200/activate/%s",
                newPerson.getUsername(),
                newPerson.getActivationCode()
        );
        mailService.send(newPerson.getEmail(), "Activation code", message);
        return newPerson;

    }

    public Person activateUser(String code) {
        Person person = personRepository.findByActivationCode(code).orElseThrow(EntityNotFoundException::new);
        person.setActivationCode(null);
        personRepository.save(person);
        return person;
    }
}
