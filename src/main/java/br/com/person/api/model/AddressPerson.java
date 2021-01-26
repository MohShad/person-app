package br.com.person.api.model;

import lombok.*;
import org.hibernate.annotations.Polymorphism;
import org.hibernate.annotations.PolymorphismType;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "person_address")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Polymorphism(type = PolymorphismType.IMPLICIT)
public class AddressPerson extends Person implements Serializable {

    private static final long serialVersionUID = 1L;

    @Basic
    @Column(name = "address", nullable = false)
    private String address;

    public AddressPerson(String nome, String sexo, String email, Date dataNascimento, String naturalidade, String nacionalidade, String cpf, Date createdAt, String address) {
        super(nome, sexo, email, dataNascimento, naturalidade, nacionalidade, cpf, createdAt);
        this.address = address;
    }

    public AddressPerson(Long id, String nome, String sexo, @Email String email, Date dataNascimento, String naturalidade, String nacionalidade, @Size(min = 11, max = 11) String cpf, Date updatedAt, String address) {
        super(id, nome, sexo, email, dataNascimento, naturalidade, nacionalidade, cpf, updatedAt);
        this.address = address;
    }

    public AddressPerson(Person pr) {
    }
}
