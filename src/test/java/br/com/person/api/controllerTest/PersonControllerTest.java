package br.com.person.api.controllerTest;

import br.com.person.api.controller.PersonController;
import br.com.person.api.dto.ApiResponseDTO;
import br.com.person.api.dto.PersonRequestDTO;
import br.com.person.api.model.Person;
import br.com.person.api.repository.PersonRepository;
import br.com.person.api.service.PersonService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PersonControllerTest {

    private static final Logger logger = LoggerFactory.getLogger(PersonControllerTest.class);

    @InjectMocks
    private PersonController personController;

    @Mock
    private PersonRepository personRepository;

    @Mock
    private PersonService personService;

    @Test
    public void registerPersonTestController() {

        logger.info("POST - Person-v1, registerPersonTestController");

        PersonRequestDTO person = new PersonRequestDTO("MohShad",
                "M",
                "test01@gmail.com",
                new Date(2010 - 12 - 12),
                "test",
                "test",
                "01557866943"
        );

        when(personRepository.existsByCpf("01557866943")).thenReturn(false);
        when(personService.savePerson(person)).thenReturn(1L);
        ResponseEntity<?> responseEntity = personController.registerPerson(person);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
        assertNotNull(responseEntity.getBody());
    }

    @Test
    public void getByCpfTestController() {

        logger.info("GET - Person-v1, getByCpfTestController");

        Person person = new Person(
                1L,
                "",
                "M",
                "123fdd@gmail.com",
                new Date(2010 - 12 - 12),
                "test",
                "test",
                "01557866943",
                new Date()
        );

        when(personRepository.existsByCpf("01557866943")).thenReturn(true);
        when(personService.getByCpf("01557866943")).thenReturn(person);
        ResponseEntity<Person> responseEntity = personController.getByCpf(person.getCpf());

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(202);
        assertNotNull(responseEntity.getBody());
    }

    @Test
    public void existCpfTestController() {

        logger.info("GET - Person-v1, existCpfTestController");

        String cpf = "01557866943";
        when(personRepository.existsByCpf(cpf)).thenReturn(true);
        ResponseEntity<ApiResponseDTO> responseEntity = personController.existCpf(cpf);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(202);
        assertNotNull(responseEntity.getBody());
        assertEquals(true, responseEntity.getBody().getSuccess());

    }

    @Test
    public void getByIdTestController() {

        logger.info("GET - Person-v1, getByIdTestController");

        Person person = new Person(
                1L,
                "Test test",
                "M",
                "123fdd@gmail.com",
                new Date(2010 - 12 - 12),
                "test",
                "test",
                "01557866943",
                new Date()
        );

        when(personRepository.findById(1L)).thenReturn(java.util.Optional.of(person));
        when(personService.getById(1L)).thenReturn(person);
        ResponseEntity<Person> responseEntity = personController.getById(1L);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(202);
        assertNotNull(responseEntity.getBody());
        assertEquals("Test test", responseEntity.getBody().getNome());
    }

    @Test
    public void getAllTestController() {

        logger.info("POST - Person-v1, getAllTestController");

        Person person01 = new Person(
                1L,
                "Test test",
                "M",
                "123fdd@gmail.com",
                new Date(2010 - 12 - 12),
                "test",
                "test",
                "01557866943",
                new Date()
        );
        Person person02 = new Person(
                1L,
                "Test test",
                "M",
                "123fdd@gmail.com",
                new Date(2010 - 12 - 12),
                "test",
                "test",
                "01557866943",
                new Date()
        );

        List<Person> personList = new ArrayList<>();
        personList.add(person01);
        personList.add(person02);
        Integer page = 0;
        Integer size = 10;
        Pageable paging = PageRequest.of(page, size);
//        when(personService.getAll(page, size).thenReturn(Arrays.asList(person01, person02)));


    }
}
