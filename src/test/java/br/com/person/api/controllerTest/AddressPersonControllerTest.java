package br.com.person.api.controllerTest;

import br.com.person.api.controller.AddressPersonController;
import br.com.person.api.controller.PersonController;
import br.com.person.api.dto.AddressPersonRequestDTO;
import br.com.person.api.dto.ApiResponseDTO;
import br.com.person.api.dto.PersonRequestDTO;
import br.com.person.api.model.AddressPerson;
import br.com.person.api.model.Person;
import br.com.person.api.repository.AddressPersonRepository;
import br.com.person.api.repository.PersonRepository;
import br.com.person.api.service.AddressPersonService;
import br.com.person.api.service.PersonService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AddressPersonControllerTest {

    private static final Logger logger = LoggerFactory.getLogger(AddressPersonControllerTest.class);

    @InjectMocks
    private AddressPersonController addressPersonController;

    @Mock
    private AddressPersonRepository addressPersonRepository;

    @Mock
    private AddressPersonService addressPersonService;

    @Test
    public void registerAddressPersonTestController() {

        logger.info("POST - Person-v2, registerAddressPersonTestController");

        AddressPersonRequestDTO person = new AddressPersonRequestDTO(
                "MohShad",
                "M",
                "test01@gmail.com",
                new Date(2010 - 12 - 12),
                "test",
                "test",
                "01557866943",
                "address01"
        );

        when(addressPersonRepository.existsByCpf("01557866943")).thenReturn(false);
        when(addressPersonService.savePerson(person)).thenReturn(1L);
        ResponseEntity<?> responseEntity = addressPersonController.registerPerson(person);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
        assertNotNull(responseEntity.getBody());
    }

    @Test
    public void getByCpfTestController() {

        logger.info("GET - Person-v2, getByCpfTestController");

        AddressPerson person = new AddressPerson(
                1L,
                "",
                "M",
                "123fdd@gmail.com",
                new Date(2010 - 12 - 12),
                "test",
                "test",
                "01557866943",
                new Date(),
                "Test Add."

        );

        when(addressPersonRepository.existsByCpf("01557866943")).thenReturn(true);
        when(addressPersonService.getByCpf("01557866943")).thenReturn(person);
        ResponseEntity<Person> responseEntity = addressPersonController.getByCpf(person.getCpf());

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(202);
        assertNotNull(responseEntity.getBody());
    }

    @Test
    public void existCpfTestController() {

        logger.info("GET - Person-v2, existCpfTestController");

        String cpf = "01557866943";
        when(addressPersonRepository.existsByCpf(cpf)).thenReturn(true);
        ResponseEntity<ApiResponseDTO> responseEntity = addressPersonController.existCpf(cpf);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(202);
        assertNotNull(responseEntity.getBody());
        assertEquals(true, responseEntity.getBody().getSuccess());

    }

    @Test
    public void getByIdTestController() {

        logger.info("GET - Person-v2, getByIdTestController");

        AddressPerson person = new AddressPerson(
                1L,
                "Test test",
                "M",
                "123fdd@gmail.com",
                new Date(2010 - 12 - 12),
                "test",
                "test",
                "test",
                new Date(),
                "address"
        );

        when(addressPersonRepository.findById(1L)).thenReturn(java.util.Optional.of(person));
        when(addressPersonService.getById(1L)).thenReturn(person);
        ResponseEntity<AddressPerson> responseEntity = addressPersonController.getById(1L);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(202);
        assertNotNull(responseEntity.getBody());
        assertEquals("Test test", responseEntity.getBody().getNome());
    }
}
