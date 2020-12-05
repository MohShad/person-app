package br.com.person.api.controller;

import br.com.person.api.dto.ApiResponseDTO;
import br.com.person.api.dto.PersonRequestDTO;
import br.com.person.api.dto.PersonResponseSaveDTO;
import br.com.person.api.repository.PersonRepository;
import br.com.person.api.service.PersonService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/person")
public class PersonController {

    private static final Logger logger = LoggerFactory.getLogger(PersonController.class);

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PersonService personService;

    @ApiOperation(value = "Cadastro pessoa", produces = "application/json")
    @ApiResponses({
            @ApiResponse(code = 401, message = "Não autorizado"),
            @ApiResponse(code = 200, message = "A Pessoa foi cadastrado com sucesso.")
    })
    @PostMapping
    public ResponseEntity<?> registerPerson(
            @ApiParam(value = "Obejto person para criar pessoa em banco de dados.", required = true)
            @Valid @RequestBody PersonRequestDTO personRequestDTO) {
        try {
            if (personRepository.existsByCpf(personRequestDTO.getCpf())) {
                return new ResponseEntity(new ApiResponseDTO(false, "Existe pessoa registrado com CPF: " + personRequestDTO.getCpf()),
                        HttpStatus.BAD_REQUEST);
            }
            Long id = personService.savePerson(personRequestDTO);

            return new ResponseEntity(new PersonResponseSaveDTO(true, "Pessoa registrado com sucesso.", id),
                    HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return new ResponseEntity(new ApiResponseDTO(false, "Internal error: " + e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
