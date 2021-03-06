package br.com.person.api.integrationTest;

import br.com.person.api.RestApiPersonApplication;
import br.com.person.api.dto.AddressPersonRequestUpdateDTO;
import br.com.person.api.dto.PersonRequestUpdateDTO;
import br.com.person.api.dto.PersonResponseSaveDTO;
import br.com.person.api.model.AddressPerson;
import br.com.person.api.model.Person;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.Date;

import static org.junit.Assert.assertEquals;

@SpringBootTest(classes = RestApiPersonApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AddressPersonIntegrationTest {

    private static final Logger logger = LoggerFactory.getLogger(AddressPersonIntegrationTest.class);

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private Environment environment;

    HttpHeaders headers = new HttpHeaders();

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

    @Test
    public void registerPersonTest() {
        logger.info("POST - Person-v2, registerPersonTest");

        AddressPerson person = new AddressPerson(
                "MohShad",
                "M",
                "test01@gmail.com",
                new Date(2010 - 12 - 12),
                "test",
                "test",
                "01557866943",
                new Date(),
                "address"
        );

        HttpEntity<Person> entity = new HttpEntity<Person>(person, headers);
        ResponseEntity<String> response = restTemplate.withBasicAuth(environment.getProperty("security.username"), environment.getProperty("security.password")).exchange(
                createURLWithPort("/api/v1/person"),
                HttpMethod.POST, entity, String.class);

        assertEquals(201, response.getStatusCodeValue());
    }

    @Test
    public void getAllTest() {
        logger.info("GET - Person-v2, getAllTest");

        AddressPerson person = new AddressPerson(
                1L,
                "MohShad",
                "M",
                "test01@gmail.com",
                new Date(2010 - 12 - 12),
                "test",
                "test",
                "01557866943",
                new Date(),
                "address02"
        );

        HttpEntity<AddressPerson> entity = new HttpEntity<AddressPerson>(person, headers);

        ResponseEntity<String> response = restTemplate.withBasicAuth(environment.getProperty("security.username"), environment.getProperty("security.password")).exchange(
                createURLWithPort("/api/v2/person"),
                HttpMethod.GET, entity, String.class);

        assertEquals(202, response.getStatusCodeValue());
        assertEquals("[]", response.getBody());
    }

    @Test
    public void updatePersonTest() {
        logger.info("PUT - Person-v2, updatePersonTest");

        Person person = new Person(
                1L,
                "MohShad",
                "M",
                "test01@gmail.com",
                new Date(2010 - 12 - 12),
                "test",
                "test",
                "01557866943",
                new Date(),
                null
        );

        AddressPersonRequestUpdateDTO personUpdated = new AddressPersonRequestUpdateDTO(
                "MohShad",
                "M",
                "test01@gmail.com",
                new Date(2010 - 12 - 12),
                "test",
                "test",
                "Address01"
        );

        HttpEntity<AddressPersonRequestUpdateDTO> entity = new HttpEntity<>(personUpdated, headers);
        ResponseEntity<PersonResponseSaveDTO> response = restTemplate.withBasicAuth(environment.getProperty("security.username"), environment.getProperty("security.password")).exchange(
                createURLWithPort("/api/v2/person/" + person.getId()),
                HttpMethod.PUT, entity, PersonResponseSaveDTO.class);

        assertEquals(409, response.getStatusCodeValue());
        assertEquals(null, response.getBody().getId());
    }

    @Test
    public void deletePersonTest() {
        logger.info("DELETE - Person-v2, deletePersonTest");

        AddressPerson person = new AddressPerson(
                1L,
                "MohShad",
                "M",
                "test01@gmail.com",
                new Date(2010 - 12 - 12),
                "test",
                "test",
                "01557866943",
                new Date(),
                "address01"
        );

        HttpEntity<Person> entity = new HttpEntity<Person>(person, new HttpHeaders());
        ResponseEntity<PersonResponseSaveDTO> response = restTemplate.withBasicAuth(environment.getProperty("security.username"), environment.getProperty("security.password")).exchange(
                createURLWithPort("/api/v2/person/" + person.getId()),
                HttpMethod.DELETE, entity, PersonResponseSaveDTO.class);

        assertEquals(409, response.getStatusCodeValue());
        assertEquals(false, response.getBody().getSuccess());
    }

}
