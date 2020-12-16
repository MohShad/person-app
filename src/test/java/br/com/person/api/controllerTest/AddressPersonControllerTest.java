package br.com.person.api.controllerTest;

import br.com.person.api.controller.AddressPersonController;
import br.com.person.api.dto.AddressPersonRequestDTO;
import br.com.person.api.dto.ApiResponseDTO;
import br.com.person.api.dto.PersonRequestUpdateDTO;
import br.com.person.api.model.AddressPerson;
import br.com.person.api.model.Person;
import br.com.person.api.repository.AddressPersonRepository;
import br.com.person.api.service.AddressPersonService;
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
public class AddressPersonControllerTest {

    private static final Logger logger = LoggerFactory.getLogger(AddressPersonControllerTest.class);

    @InjectMocks
    private AddressPersonController addressPersonController;

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
        MockitoAnnotations.initMocks(AddressPersonControllerTest.class);
    }

    @Before
    public void init() {

        List<AddressPerson> personList = new ArrayList<>();
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
                "addres02"
        );
        personList.add(addressPersonMock);
        personList.add(addressPersonMock01);

        PersonRequestUpdateDTO personRequestUpdateDTO = new PersonRequestUpdateDTO();
        personRequestUpdateDTO.setNome(addressPersonMock01.getNome());
        personRequestUpdateDTO.setSexo(addressPersonMock01.getSexo());
        personRequestUpdateDTO.setEmail(addressPersonMock01.getEmail());
        personRequestUpdateDTO.setDataNascimento(addressPersonMock01.getDataNascimento());
        personRequestUpdateDTO.setNaturalidade(addressPersonMock01.getNaturalidade());
        personRequestUpdateDTO.setNacionalidade(addressPersonMock01.getNacionalidade());

        when(addressPersonRepository.findById(addressPersonMock.getId())).thenReturn(Optional.of(addressPersonMock));
        when(addressPersonService.getAll(0, 10)).thenReturn(personList);
        when(addressPersonRepository.existsByCpf("01557866943")).thenReturn(false);

    }

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

    @Test
    public void getAllTestController() {

        logger.info("POST - Person-v1, getAllTestController");

        List<AddressPerson> prList = addressPersonService.getAll(0, 10);

        assertNotSame(prList.get(0).getId(), prList.get(1).getId());
        assertEquals(prList.get(0).getNacionalidade(), prList.get(1).getNacionalidade());

    }
}
