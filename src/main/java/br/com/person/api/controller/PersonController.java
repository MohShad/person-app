package br.com.person.api.controller;

import br.com.person.api.dto.*;
import br.com.person.api.model.Person;
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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

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
        logger.info("POST - Person, registerPerson");
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

    @ApiOperation(value = "Busca de pessoa por Cpf.", produces = "application/json")
    @ApiResponses({
            @ApiResponse(code = 401, message = "Não autorizado"),
            @ApiResponse(code = 200, message = "Consultar Pessoa por Cpf.")
    })
    @GetMapping("/{cpf}")
    public ResponseEntity<Person> getById(
            @ApiParam(value = "Cpf da pessoa.", required = true)
            @PathVariable("cpf") String cpf) {
        logger.info("GET - Person, getById");
        try {
            if (!personRepository.existsByCpf(cpf)) {
                return new ResponseEntity(new ApiResponseDTO(false, "Não existe pessoa registrado com CPF: " + cpf),
                        HttpStatus.BAD_REQUEST);
            }
            Person person = personService.getByCpf(cpf);
            return new ResponseEntity<Person>(person, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return new ResponseEntity(new ApiResponseDTO(false, "Internal error: " + e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Busca Todas as pessoas.", produces = "application/json")
    @ApiResponses({
            @ApiResponse(code = 401, message = "Não autorizado"),
            @ApiResponse(code = 200, message = "Consultar Pessoas.")
    })
    @GetMapping()
    public ResponseEntity<PersonResponseListDTO> getAll(
            @ApiParam(value = "N/A.", required = false)
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "1") Integer size) {
        logger.info("GET - Person, getAll");

        try {
            PersonResponseListDTO personResponseListDTO = personService.getAll(page, size);
            return new ResponseEntity<PersonResponseListDTO>(personResponseListDTO, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return new ResponseEntity(new ApiResponseDTO(false, "Internal error: " + e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Atualizar registro da pessoa.", produces = "application/json")
    @ApiResponses({
            @ApiResponse(code = 401, message = "Não autorizado"),
            @ApiResponse(code = 200, message = "Atualizar pessoa.")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Person> updateById(
            @PathVariable("id") Long id,
            @Valid @RequestBody PersonResponseUpdateDTO personResponseUpdateDTO
    ) {
        logger.info("PUT - Person, updateById");
        try {

            Optional<Person> person = personRepository.findById(id);
            if (!person.isPresent())
                return new ResponseEntity(new ApiResponseDTO(false, "Não existe pessoa registrado com id: " + id),
                        HttpStatus.BAD_REQUEST);
            ResponseEntity<Person> pr = personService.updateById(personResponseUpdateDTO, id);
            return new ResponseEntity(new PersonResponseSaveDTO(true, "A pessoa foi atualizado com sucesso,", id), HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return new ResponseEntity(new ApiResponseDTO(false, "Internal error: " + e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Excluir registro da pessoa.", produces = "application/json")
    @ApiResponses({
            @ApiResponse(code = 401, message = "Não autorizado"),
            @ApiResponse(code = 200, message = "Atualizar pessoa.")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Person> deleteById(
            @PathVariable("id") Long id) {
        logger.info("DELETE - Person, deleteById");
        try {

            Optional<Person> person = personRepository.findById(id);
            if (!person.isPresent())
                return new ResponseEntity(new ApiResponseDTO(false, "Não existe pessoa registrado com id: " + id),
                        HttpStatus.BAD_REQUEST);
            ResponseEntity<Object> pr = personService.deleteById(id);
            return new ResponseEntity(new PersonResponseSaveDTO(true, "A pessoa foi excluida com sucesso,", id), HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return new ResponseEntity(new ApiResponseDTO(false, "Internal error: " + e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
