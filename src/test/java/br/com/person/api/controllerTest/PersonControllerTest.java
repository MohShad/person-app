package br.com.person.api.controllerTest;

import br.com.person.api.controller.PersonController;
import br.com.person.api.dto.ApiResponseDTO;
import br.com.person.api.dto.PersonRequestDTO;
import br.com.person.api.dto.PersonRequestUpdateDTO;
import br.com.person.api.model.Person;
import br.com.person.api.repository.PersonRepository;
import br.com.person.api.service.PersonService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class PersonControllerTest {

    private static final Logger logger = LoggerFactory.getLogger(PersonControllerTest.class);

    @InjectMocks
    private PersonController personController;

    @Mock
    private PersonRepository personRepository;

    @Mock
    private PersonService personService;

    @Mock
    private Person personMock;

    @Mock
    private Person personMock01;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(PersonControllerTest.class);
    }

    @Before
    public void init() {

        List<Person> personList = new ArrayList<>();
        personMock = new Person(
                1L,
                "Test",
                "M",
                "564fdd@gmail.com",
                new Date(2010 - 12 - 12),
                "test",
                "test",
                "01557866943",
                new Date()
        );

        personMock01 = new Person(
                2L,
                "Test test",
                "F",
                "123fdd@gmail.com",
                new Date(2010 - 12 - 12),
                "test",
                "test",
                "01677866945",
                new Date()
        );
        personList.add(personMock);
        personList.add(personMock01);

        PersonRequestUpdateDTO personRequestUpdateDTO = new PersonRequestUpdateDTO();
        personRequestUpdateDTO.setNome(personMock01.getNome());
        personRequestUpdateDTO.setSexo(personMock01.getSexo());
        personRequestUpdateDTO.setEmail(personMock01.getEmail());
        personRequestUpdateDTO.setDataNascimento(personMock01.getDataNascimento());
        personRequestUpdateDTO.setNaturalidade(personMock01.getNaturalidade());
        personRequestUpdateDTO.setNacionalidade(personMock01.getNacionalidade());

        when(personRepository.findById(personMock.getId())).thenReturn(Optional.of(personMock));
        when(personService.getAll(0, 10)).thenReturn(personList);
        when(personRepository.existsByCpf("01557866943")).thenReturn(false);

    }

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

        List<Person> prList = personService.getAll(0, 10);

        assertNotSame(prList.get(0).getId(), prList.get(1).getId());
        assertEquals(prList.get(0).getNacionalidade(), prList.get(1).getNacionalidade());

    }
}
