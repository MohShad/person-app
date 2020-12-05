package br.com.person.api.service;

import br.com.person.api.dto.PersonRequestDTO;
import br.com.person.api.model.Person;
import br.com.person.api.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service
public class PersonServiceImpl implements PersonService {

    private static final Logger logger = LoggerFactory.getLogger(PersonService.class);

    @Autowired
    private PersonRepository personRepository;

    @Override
    @Transactional
    public Long savePerson(PersonRequestDTO personRequestDTO) {

        try {

            Person person = new Person(
                    personRequestDTO.getNome(),
                    personRequestDTO.getSexo(),
                    personRequestDTO.getEmail(),
                    personRequestDTO.getDataNascimento(),
                    personRequestDTO.getNaturalidade(),
                    personRequestDTO.getNacionalidade(),
                    personRequestDTO.getCpf(),
                    new Date()
            );

            personRepository.save(person);
            return person.getId();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return null;
        }
    }
}
