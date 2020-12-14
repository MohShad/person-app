package br.com.person.api.controller;

import br.com.person.api.dto.AddressPersonRequestDTO;
import br.com.person.api.dto.AddressPersonRequestUpdateDTO;
import br.com.person.api.dto.ApiResponseDTO;
import br.com.person.api.dto.PersonResponseSaveDTO;
import br.com.person.api.model.AddressPerson;
import br.com.person.api.model.Person;
import br.com.person.api.repository.AddressPersonRepository;
import br.com.person.api.service.AddressPersonService;
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
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v2/person")
@CrossOrigin(origins = "https://frontend-person.azurewebsites.net")
public class AddressPersonController {

    private static final Logger logger = LoggerFactory.getLogger(AddressPersonController.class);

    @Autowired
    private AddressPersonRepository addressPersonRepository;

    @Autowired
    private AddressPersonService addressPersonService;

    @ApiOperation(value = "Cadastro pessoa", produces = "application/json")
    @ApiResponses({
            @ApiResponse(code = 401, message = "Não autorizado"),
            @ApiResponse(code = 200, message = "A Pessoa foi cadastrado com sucesso.")
    })
    @PostMapping
    public ResponseEntity<?> registerPerson(
            @ApiParam(value = "Obejto person para criar pessoa em banco de dados.", required = true)
            @Valid @RequestBody AddressPersonRequestDTO addressPersonRequestDTO) {
        logger.info("POST - AddressPerson-V2, registerPerson");
        try {
            if (addressPersonRepository.existsByCpf(addressPersonRequestDTO.getCpf())) {
                return new ResponseEntity(new ApiResponseDTO(false, "Existe pessoa registrado com CPF: " + addressPersonRequestDTO.getCpf()),
                        HttpStatus.BAD_REQUEST);
            }
            Long id = addressPersonService.savePerson(addressPersonRequestDTO);

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
    @GetMapping("/getByCpf/{cpf}")
    public ResponseEntity<Person> getByCpf(
            @ApiParam(value = "Cpf da pessoa.", required = true)
            @PathVariable("cpf") String cpf) {
        logger.info("GET - AddressPerson-v2, getByCpf");
        try {
            if (!addressPersonRepository.existsByCpf(cpf)) {
                return new ResponseEntity(new ApiResponseDTO(false, "Não existe pessoa registrado com CPF: " + cpf),
                        HttpStatus.BAD_REQUEST);
            }
            AddressPerson person = addressPersonService.getByCpf(cpf);
            return new ResponseEntity<Person>(person, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return new ResponseEntity(new ApiResponseDTO(false, "Internal error: " + e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Verificar a existencia do Cpf.", produces = "application/json")
    @ApiResponses({
            @ApiResponse(code = 401, message = "Não autorizado"),
            @ApiResponse(code = 200, message = "Verificar a existencia do Cpf.")
    })
    @GetMapping("/existCpf/{cpf}")
    public ResponseEntity<ApiResponseDTO> existCpf(
            @ApiParam(value = "Cpf da pessoa.", required = true)
            @PathVariable("cpf") String cpf) {
        logger.info("GET - AddressPerson-v2, existCpf");
        try {
            if (addressPersonRepository.existsByCpf(cpf)) {
                return new ResponseEntity(new ApiResponseDTO(true, "O cpf informado existe no banco de dados."),
                        HttpStatus.OK);
            } else {
                return new ResponseEntity(new ApiResponseDTO(false, "O cpf informado não existe no banco de dados."),
                        HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return new ResponseEntity(new ApiResponseDTO(false, "Internal error: " + e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Busca de pessoa por id.", produces = "application/json")
    @ApiResponses({
            @ApiResponse(code = 401, message = "Não autorizado"),
            @ApiResponse(code = 200, message = "Consultar Pessoa por id.")
    })
    @GetMapping("/getById/{id}")
    public ResponseEntity<AddressPerson> getById(
            @ApiParam(value = "id da pessoa.", required = true)
            @PathVariable("id") Long id) {
        logger.info("GET - AddressPerson-v2, getById");
        try {
            Optional<AddressPerson> person = addressPersonRepository.findById(id);
            if (!person.isPresent()) {
                return new ResponseEntity(new ApiResponseDTO(false, "A pessoa com ID: " + id + " não foi encontrado"),
                        HttpStatus.BAD_REQUEST);
            }
            AddressPerson pr = addressPersonService.getById(id);
            return new ResponseEntity<AddressPerson>(pr, HttpStatus.OK);
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
    public ResponseEntity<List<AddressPerson>> getAll(
            @ApiParam(value = "N/A.", required = false)
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "1") Integer size) {
        logger.info("GET - AddressPerson-v2, getAll");

        try {
            List<AddressPerson> personList = addressPersonService.getAll(page, size);
            return new ResponseEntity<List<AddressPerson>>(personList, HttpStatus.OK);
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
            @Valid @RequestBody AddressPersonRequestUpdateDTO addressPersonRequestUpdateDTO
    ) {
        logger.info("PUT - AddressPerson-v2, updateById");
        try {

            Optional<AddressPerson> person = addressPersonRepository.findById(id);
            if (!person.isPresent())
                return new ResponseEntity(new ApiResponseDTO(false, "Não existe pessoa registrado com id: " + id),
                        HttpStatus.BAD_REQUEST);
            Object pr = addressPersonService.updateById(addressPersonRequestUpdateDTO, id);
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
    public ResponseEntity<AddressPerson> deleteById(
            @PathVariable("id") Long id) {
        logger.info("DELETE - AddressPerson-v2, deleteById");
        try {

            Optional<AddressPerson> person = addressPersonRepository.findById(id);
            if (!person.isPresent())
                return new ResponseEntity(new ApiResponseDTO(false, "Não existe pessoa registrado com id: " + id),
                        HttpStatus.BAD_REQUEST);
            ResponseEntity<Object> pr = addressPersonService.deleteById(id);
            return new ResponseEntity(new PersonResponseSaveDTO(true, "A pessoa foi excluida com sucesso,", id), HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return new ResponseEntity(new ApiResponseDTO(false, "Internal error: " + e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
