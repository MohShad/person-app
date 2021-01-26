package br.com.person.api.serviceTest;

import br.com.person.api.dto.PersonRequestDTO;
import br.com.person.api.dto.PersonRequestUpdateDTO;
import br.com.person.api.model.AddressPerson;
import br.com.person.api.model.Person;
import br.com.person.api.repository.PersonRepository;
import br.com.person.api.service.PersonService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class PersonServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(PersonServiceTest.class);

    @Mock
    private PersonRepository personRepository;

    @Mock
    private PersonService personService;

    @Mock
    private Person personMock;

    @Mock
    private Person personMock01;

    @Mock
    private PersonRequestUpdateDTO personRequestUpdateDTO;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(PersonServiceTest.class);
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

        personRequestUpdateDTO.setNome("Test test");
        personRequestUpdateDTO.setSexo(personMock01.getSexo());
        personRequestUpdateDTO.setEmail(personMock01.getEmail());
        personRequestUpdateDTO.setDataNascimento(personMock01.getDataNascimento());
        personRequestUpdateDTO.setNaturalidade(personMock01.getNaturalidade());
        personRequestUpdateDTO.setNacionalidade(personMock01.getNacionalidade());

        when(personRepository.findByCpf(personMock.getCpf())).thenReturn(Optional.of(personMock));
        when(personRepository.findById(personMock.getId())).thenReturn(Optional.of(personMock));
        when(personService.getAll(0, 10)).thenReturn(personList);
        personMock.setNome("Teste 0899");
        when(personRepository.save(personMock)).thenReturn(personMock);

    }

    @Test
    public void savePersonServiceTest() {

        logger.info("POST - Person-v1, savePersonServiceTest");

        Person pr = new Person();
        PersonRequestDTO personRequestDTO = new PersonRequestDTO(
                "Teste",
                "M",
                "teste@gmail.com",
                new Date(2010 - 12 - 10),
                "teste",
                "teste",
                "02387564824"
        );

        pr.setNome(personRequestDTO.getNome());
        pr.setSexo(personRequestDTO.getSexo());
        pr.setEmail(personRequestDTO.getEmail());
        pr.setDataNascimento(personRequestDTO.getDataNascimento());
        pr.setNaturalidade(personRequestDTO.getNaturalidade());
        pr.setNacionalidade(personRequestDTO.getNacionalidade());
        pr.setCpf(personRequestDTO.getCpf());

        assertNotNull(pr);
        assertEquals("teste@gmail.com", pr.getEmail());

    }

    @Test
    public void getByCpfServiceTest() {

        logger.info("GET - Person-v1, getByCpfServiceTest");

        Optional<Person> person = personRepository.findByCpf(personMock.getCpf());
        Person personDTO = new Person(person.get().getId(), person.get().getNome(), person.get().getSexo(), person.get().getEmail(), person.get().getDataNascimento(), person.get().getNaturalidade(), person.get().getNacionalidade(), person.get().getCpf(), person.get().getCreatedAt());

        assertNotNull(personDTO);
        assertEquals(personMock.getId(), personDTO.getId());
    }

    @Test
    public void getByIdServiceTest() {
        logger.info("GET - Person-v1, getByIdServiceTest");

        Optional<Person> person = personRepository.findById(personMock.getId());
        Person personDTO = new Person(person.get().getId(), person.get().getNome(), person.get().getSexo(), person.get().getEmail(), person.get().getDataNascimento(), person.get().getNaturalidade(), person.get().getNacionalidade(), person.get().getCpf(), person.get().getCreatedAt());

        assertNotNull(personDTO);
        assertEquals("01557866943", personDTO.getCpf());
    }

    @Test
    public void getAllServiceTest() {
        logger.info("GET - Person-v1, getAllServiceTest");

        List<Person> prList = personService.getAll(0, 10);

        assertNotSame(prList.get(0).getId(), prList.get(1).getId());

    }

    @Test
    public void updateByIdServiceTest() {
        logger.info("GET - Person-v1, updateByIdServiceTest");

        Optional<Person> person = personRepository.findById(personMock.getId());

        person.get().setNome(personRequestUpdateDTO.getNome());
        person.get().setSexo(personRequestUpdateDTO.getSexo());
        person.get().setEmail(personRequestUpdateDTO.getEmail());
        person.get().setDataNascimento(personRequestUpdateDTO.getDataNascimento());
        person.get().setNaturalidade(personRequestUpdateDTO.getNaturalidade());
        person.get().setNacionalidade(personRequestUpdateDTO.getNacionalidade());
        person.get().setUpdatedAt(new Date());

        Person pr = personRepository.save(person.get());

        assertEquals("01557866943", pr.getCpf());
    }

}
