package br.com.person.api.repository;

import br.com.person.api.model.Person;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Primary
public interface PersonRepository extends CrudRepository<Person, Long> {

    Boolean existsByCpf(String cpf);

    Optional<Person> findById(Long id);

    Optional<Person> findByCpf(String cpf);

    Page<Person> findAll(Pageable pageable);

}
