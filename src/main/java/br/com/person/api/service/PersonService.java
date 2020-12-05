package br.com.person.api.service;

import br.com.person.api.dto.PersonRequestDTO;

public interface PersonService {

    Long savePerson (PersonRequestDTO personRequestDTO);
}
