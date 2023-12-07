package com.dashko.api.controller;

import com.dashko.api.dto.auth.AuthRequestDTO;
import com.dashko.api.dto.auth.AuthResponseDTO;
import com.dashko.api.dto.person.PersonCreateDTO;
import com.dashko.api.mapping.person.PersonMapper;
import com.dashko.api.security.SecurityService;
import com.dashko.common.models.Person;
import com.dashko.common.service.person.IPersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {

    IPersonService personService;
    SecurityService securityService;
    PersonMapper personMapper;


    @Operation(
            summary = "Login person",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Created",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = AuthResponseDTO.class)))
                            })
            }
    )
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestDTO dto) {
        AuthResponseDTO authResponseDTO = new AuthResponseDTO();
        Person person = personService.getPersonByEmail(dto.getEmail());
        if (person.getActivationCode() != null) {
            return ResponseEntity.badRequest().body(new AuthResponseDTO(dto.getEmail(), "Account is not activated"));
        }
        return ResponseEntity.ok(authResponseDTO.toBuilder()
                .email(dto.getEmail())
                .token(securityService.authenticate(dto.getEmail(), dto.getPassword()))
                .build());
    }


    @Operation(
            summary = "Register person",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Created",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = PersonCreateDTO.class)))
                            })
            }
    )
    @PostMapping("/register")
    public PersonCreateDTO register(@RequestBody PersonCreateDTO dto) {
        Person entity = personMapper.map(dto);
        return personMapper.map(personService.registerUser(entity));
    }

    @GetMapping("/activate/{code}")
    public ResponseEntity<String> activate(@PathVariable String code) {
        Person person = personService.activateUser(code);

        if (person.getActivationCode() == null) {
            return ResponseEntity.ok("User successfully activated");
        } else {
            return ResponseEntity.badRequest().body("Activation code is not found!");
        }
    }
}
