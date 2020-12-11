package br.com.person.api.service;

import br.com.person.api.dto.PersonRequestDTO;
import br.com.person.api.dto.PersonResponseUpdateDTO;
import br.com.person.api.model.Person;
import br.com.person.api.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PersonServiceImpl implements PersonService {

    private static final Logger logger = LoggerFactory.getLogger(PersonServiceImpl.class);

    @Autowired
    private PersonRepository personRepository;

    @Override
    @Transactional
    public Long savePerson(PersonRequestDTO personRequestDTO) {
        logger.info("Person, savePerson");
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

    @Override
    public Person getByCpf(String cpf) {
        logger.info("Person, getByCpf");
        Optional<Person> person = personRepository.findByCpf(cpf);
        return person.get();
    }

    @Override
    public Person getById(Long id) {
        logger.info("Person, getById");
        Optional<Person> person = personRepository.findById(id);
        return person.get();
    }

    @Override
    public List<Person> getAll(Integer page, Integer size) {
        logger.info("Person, getAll");

        List<Person> personList = new ArrayList<>();
        Pageable paging = PageRequest.of(page, size);

        Page<Person> prsList = personRepository.findAll(paging);
        for (Person person : prsList)
            if (person != null)
                personList.add(person);

        return personList;
    }

    @Override
    @Transactional
    public ResponseEntity<Person> updateById(PersonResponseUpdateDTO person, Long id) {
        logger.info("Person, updateById");
        return personRepository.findById(id)
                .map(record -> {
                    record.setNome(person.getNome());
                    record.setSexo(person.getSexo());
                    record.setEmail(person.getEmail());
                    record.setDataNascimento(person.getDataNascimento());
                    record.setNaturalidade(person.getNaturalidade());
                    record.setNacionalidade(person.getNacionalidade());
                    record.setCreatedAt(record.getCreatedAt());
                    record.setUpdatedAt(new Date());
                    Person updated = personRepository.save(record);

                    return ResponseEntity.ok().body(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    @Transactional
    public ResponseEntity<Object> deleteById(Long id) {
        logger.info("Person, deleteById");
        return personRepository.findById(id)
                .map(record -> {
                    personRepository.deleteById(id);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }
}
