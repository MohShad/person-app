package br.com.person.api.repository;

import br.com.person.api.model.AddressPerson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressPersonRepository extends CrudRepository<AddressPerson, Long> {

    Boolean existsByCpf(String cpf);

    Optional<AddressPerson> findById(Long id);

    Optional<AddressPerson> findByCpf(String cpf);

    Page<AddressPerson> findAll(Pageable pageable);

}
