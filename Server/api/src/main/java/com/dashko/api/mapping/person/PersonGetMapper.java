package com.dashko.api.mapping.person;


import com.dashko.common.dto.person.PersonGetDTO;
import com.dashko.common.models.Person;
import org.mapstruct.Builder;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",  builder = @Builder(disableBuilder = true))
public interface PersonGetMapper {
    PersonGetDTO map(Person person);

    @InheritInverseConfiguration
    Person map(PersonGetDTO dto);

}