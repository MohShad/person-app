package br.com.person.api.service;

import br.com.person.api.dto.PersonRequestDTO;
import br.com.person.api.dto.PersonResponseListDTO;
import br.com.person.api.dto.PersonResponseUpdateDTO;
import br.com.person.api.model.Person;
import org.springframework.http.ResponseEntity;

public interface PersonService {

    Long savePerson(PersonRequestDTO personRequestDTO);

    Person getByCpf(String cpf);

    PersonResponseListDTO getAll(Integer page, Integer size);

    ResponseEntity<Person> updateById(PersonResponseUpdateDTO personResponseUpdateDTO, Long id);

    ResponseEntity<Object> deleteById(Long id);
}
