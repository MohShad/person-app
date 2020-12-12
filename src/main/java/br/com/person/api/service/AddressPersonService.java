package br.com.person.api.service;

import br.com.person.api.dto.AddressPersonRequestDTO;
import br.com.person.api.dto.AddressPersonRequestUpdateDTO;
import br.com.person.api.model.AddressPerson;
import br.com.person.api.model.Person;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AddressPersonService {

    Long savePerson(AddressPersonRequestDTO addressPersonRequestDTO);

    AddressPerson getByCpf(String cpf);

    AddressPerson getById(Long id);

    List<AddressPerson> getAll(Integer page, Integer size);

    Object updateById(AddressPersonRequestUpdateDTO addressPersonRequestUpdateDTO, Long id);

    ResponseEntity<Object> deleteById(Long id);

}
