package br.com.person.api.service;

import br.com.person.api.dto.PersonRequestDTO;
import br.com.person.api.dto.PersonResponseUpdateDTO;
import br.com.person.api.model.Person;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PersonService {

    Long savePerson(PersonRequestDTO personRequestDTO);

    Person getByCpf(String cpf);

    Person getById(Long id);

    List<Person> getAll(Integer page, Integer size);

    ResponseEntity<Person> updateById(PersonResponseUpdateDTO personResponseUpdateDTO, Long id);

    ResponseEntity<Object> deleteById(Long id);
}
