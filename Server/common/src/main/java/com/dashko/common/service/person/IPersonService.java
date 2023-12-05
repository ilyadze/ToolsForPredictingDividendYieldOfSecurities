package com.dashko.common.service.person;


import com.dashko.common.models.Person;

public interface IPersonService {

    public Person getPersonById(Long id);

    public Person getPersonByEmail(String email);

    public Person getPersonByUsername(String username);

    public Person registerUser(Person person);

    public Person activateUser(String code);

}
