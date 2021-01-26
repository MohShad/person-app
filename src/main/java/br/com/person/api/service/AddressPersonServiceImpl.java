package br.com.person.api.service;

import br.com.person.api.dto.AddressPersonRequestDTO;
import br.com.person.api.dto.AddressPersonRequestUpdateDTO;
import br.com.person.api.model.AddressPerson;
import br.com.person.api.model.Person;
import br.com.person.api.repository.AddressPersonRepository;
import br.com.person.api.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AddressPersonServiceImpl implements AddressPersonService {

    private static final Logger logger = LoggerFactory.getLogger(AddressPersonServiceImpl.class);

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private AddressPersonRepository addressPersonRepository;

    @Override
    @Transactional
    public Long savePerson(AddressPersonRequestDTO addressPersonRequestDTO) {
        logger.info("AddressPerson, savePerson");
        try {

            AddressPerson addressPerson = new AddressPerson(
                    addressPersonRequestDTO.getNome(),
                    addressPersonRequestDTO.getSexo(),
                    addressPersonRequestDTO.getEmail(),
                    addressPersonRequestDTO.getDataNascimento(),
                    addressPersonRequestDTO.getNaturalidade(),
                    addressPersonRequestDTO.getNacionalidade(),
                    addressPersonRequestDTO.getCpf(),
                    new Date(),
                    addressPersonRequestDTO.getAddress()
            );

            addressPersonRepository.save(addressPerson);
            return addressPerson.getId();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return Long.valueOf(e.getMessage());
        }
    }

    @Override
    public AddressPerson getByCpf(String cpf) {
        logger.info("Person, getByCpf");
        Optional<AddressPerson> person = addressPersonRepository.findByCpf(cpf);
        return person.get();
    }

    @Override
    public AddressPerson getById(Long id) {
        logger.info("Person, getById");
        Optional<AddressPerson> person = addressPersonRepository.findById(id);
        return person.get();
    }

    @Override
    public List<AddressPerson> getAll(Integer page, Integer size) {
        logger.info("Person, getAll");

        List<AddressPerson> personList = new ArrayList<>();
        Pageable paging = PageRequest.of(page, size);

        Page<AddressPerson> prsList = addressPersonRepository.findAll(paging);
        for (AddressPerson person : prsList)
            if (person != null)
                personList.add(person);

        return personList;
    }

    @Override
    @Transactional
    public Object updateById(AddressPersonRequestUpdateDTO addressPersonRequestUpdateDTO, Long id) {
        logger.info("AddressPerson, updateById");
        try {
            Optional<Person> person = personRepository.findById(id);
            Optional<AddressPerson> addressperson = addressPersonRepository.findById(id);

            Person pr = person.get();
            pr.setId(person.get().getId());
            pr.setNome(addressPersonRequestUpdateDTO.getNome());
            pr.setSexo(addressPersonRequestUpdateDTO.getSexo());
            pr.setEmail(addressPersonRequestUpdateDTO.getEmail());
            pr.setDataNascimento(addressPersonRequestUpdateDTO.getDataNascimento());
            pr.setNaturalidade(addressPersonRequestUpdateDTO.getNaturalidade());
            pr.setNacionalidade(addressPersonRequestUpdateDTO.getNacionalidade());
            pr.setCpf(person.get().getCpf());
            pr.setUpdatedAt(new Date());
            personRepository.save(pr);

            addressperson.get().setAddress(addressPersonRequestUpdateDTO.getAddress());
            AddressPerson person1 = addressPersonRepository.save(addressperson.get());

            return new ResponseEntity<AddressPerson>(person1, HttpStatus.OK).getBody();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<Object> deleteById(Long id) {
        logger.info("Person, deleteById");
        return addressPersonRepository.findById(id)
                .map(record -> {
                    addressPersonRepository.deleteById(id);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }
}
