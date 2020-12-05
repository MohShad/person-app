package br.com.person.api.repository;

import br.com.person.api.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    Boolean existsByCpf(String cpf);

    Optional<Person> findById(Long id);

    Optional<Person> findByCpf(String cpf);

}
