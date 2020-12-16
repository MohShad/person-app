package br.com.person.api.serviceTest;

import br.com.person.api.dto.AddressPersonRequestDTO;
import br.com.person.api.dto.AddressPersonRequestUpdateDTO;
import br.com.person.api.model.AddressPerson;
import br.com.person.api.model.Person;
import br.com.person.api.repository.AddressPersonRepository;
import br.com.person.api.service.AddressPersonService;
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
public class AddressPersonServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(AddressPersonServiceTest.class);

    @Mock
    private AddressPersonRepository addressPersonRepository;

    @Mock
    private AddressPersonService addressPersonService;

    @Mock
    private AddressPerson addressPersonMock;

    @Mock
    private AddressPerson addressPersonMock01;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(AddressPersonServiceTest.class);
    }

    @Before
    public void init() {

        List<AddressPerson> addressPersonList = new ArrayList<>();
        addressPersonMock = new AddressPerson(
                1L,
                "Test",
                "M",
                "564fdd@gmail.com",
                new Date(2010 - 12 - 12),
                "test",
                "test",
                "01557866943",
                new Date(),
                "address01"

        );

        addressPersonMock01 = new AddressPerson(
                2L,
                "Test test",
                "F",
                "123fdd@gmail.com",
                new Date(2010 - 12 - 12),
                "test",
                "test",
                "01677866945",
                new Date(),
                "address02"
        );

        addressPersonList.add(addressPersonMock);
        addressPersonList.add(addressPersonMock01);

        AddressPersonRequestUpdateDTO addressPersonRequestUpdateDTO = new AddressPersonRequestUpdateDTO();
        addressPersonRequestUpdateDTO.setNome(addressPersonMock01.getNome());
        addressPersonRequestUpdateDTO.setSexo(addressPersonMock01.getSexo());
        addressPersonRequestUpdateDTO.setEmail(addressPersonMock01.getEmail());
        addressPersonRequestUpdateDTO.setDataNascimento(addressPersonMock01.getDataNascimento());
        addressPersonRequestUpdateDTO.setNaturalidade(addressPersonMock01.getNaturalidade());
        addressPersonRequestUpdateDTO.setNacionalidade(addressPersonMock01.getNacionalidade());
        addressPersonRequestUpdateDTO.setAddress(addressPersonMock01.getAddress());

        when(addressPersonRepository.findByCpf(addressPersonMock.getCpf())).thenReturn(Optional.of(addressPersonMock));
        when(addressPersonRepository.findById(addressPersonMock.getId())).thenReturn(Optional.of(addressPersonMock));
        when(addressPersonService.getAll(0, 10)).thenReturn(addressPersonList);
        when(addressPersonService.updateById(addressPersonRequestUpdateDTO, addressPersonMock.getId())).thenReturn(addressPersonMock01);

    }

    @Test
    public void savePersonServiceTest() {

        logger.info("POST - Person-v2, savePersonServiceTest");

        AddressPerson pr = new AddressPerson();
        AddressPersonRequestDTO personRequestDTO = new AddressPersonRequestDTO(
                "Teste",
                "M",
                "teste@gmail.com",
                new Date(2010 - 12 - 10),
                "teste",
                "teste",
                "02387564824",
                "address"
        );

        pr.setNome(personRequestDTO.getNome());
        pr.setSexo(personRequestDTO.getSexo());
        pr.setEmail(personRequestDTO.getEmail());
        pr.setDataNascimento(personRequestDTO.getDataNascimento());
        pr.setNaturalidade(personRequestDTO.getNaturalidade());
        pr.setNacionalidade(personRequestDTO.getNacionalidade());
        pr.setCpf(personRequestDTO.getCpf());
        pr.setAddress(personRequestDTO.getAddress());

        assertNotNull(pr);
        assertEquals("teste@gmail.com", pr.getEmail());

    }

    @Test
    public void getByCpfServiceTest() {

        logger.info("GET - Person-v2, getByCpfServiceTest");

        Optional<AddressPerson> person = addressPersonRepository.findByCpf(addressPersonMock.getCpf());
        Person personDTO = new Person(person.get().getId(), person.get().getNome(), person.get().getSexo(), person.get().getEmail(), person.get().getDataNascimento(), person.get().getNaturalidade(), person.get().getNacionalidade(), person.get().getCpf(), person.get().getCreatedAt());

        assertNotNull(personDTO);
        assertEquals(addressPersonMock.getId(), personDTO.getId());
    }

    @Test
    public void getByIdServiceTest() {
        logger.info("GET - Person-v2, getByIdServiceTest");

        Optional<AddressPerson> person = addressPersonRepository.findById(addressPersonMock.getId());
        Person personDTO = new Person(person.get().getId(), person.get().getNome(), person.get().getSexo(), person.get().getEmail(), person.get().getDataNascimento(), person.get().getNaturalidade(), person.get().getNacionalidade(), person.get().getCpf(), person.get().getCreatedAt());

        assertNotNull(personDTO);
        assertEquals("01557866943", personDTO.getCpf());
    }

    @Test
    public void getAllServiceTest() {
        logger.info("GET - Person-v2, getAllServiceTest");

        List<AddressPerson> prList = addressPersonService.getAll(0, 10);

        assertNotSame(prList.get(0).getId(), prList.get(1).getId());

    }

    @Test
    public void updateByIdServiceTest() {
        logger.info("GET - Person-v2, updateByIdServiceTest");

        Optional<AddressPerson> person = addressPersonRepository.findById(addressPersonMock.getId());
        AddressPersonRequestUpdateDTO addressPersonRequestUpdateDTO = new AddressPersonRequestUpdateDTO();
        addressPersonRequestUpdateDTO.setNome(person.get().getNome());
        addressPersonRequestUpdateDTO.setSexo(person.get().getSexo());
        addressPersonRequestUpdateDTO.setEmail(person.get().getEmail());
        addressPersonRequestUpdateDTO.setDataNascimento(person.get().getDataNascimento());
        addressPersonRequestUpdateDTO.setNaturalidade(person.get().getNaturalidade());
        addressPersonRequestUpdateDTO.setNacionalidade(person.get().getNacionalidade());
        addressPersonRequestUpdateDTO.setAddress(person.get().getAddress());

        ResponseEntity<AddressPerson> pr = (ResponseEntity<AddressPerson>) addressPersonService.updateById(addressPersonRequestUpdateDTO, addressPersonMock.getId());

        assertEquals("Test test", pr.getBody().getNome());
    }
}
