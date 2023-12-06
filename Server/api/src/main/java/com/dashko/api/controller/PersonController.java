package com.dashko.api.controller;

import com.dashko.api.dto.person.PersonGetDTO;
import com.dashko.api.mapping.PersonGetMapper;
import com.dashko.common.service.person.PersonService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/person")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class PersonController {

    PersonService personService;
    PersonGetMapper personGetMapper;

    @GetMapping("/{email}")
    public ResponseEntity<PersonGetDTO> getPerson(@PathVariable String email) {
        System.out.println(email);
        System.out.println(personGetMapper.map(personService.getPersonByEmail(email)));
        return ResponseEntity.ok(personGetMapper.map(personService.getPersonByEmail(email)));
    }
}
