package br.com.person.api.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "person")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Basic
    @Column(name = "nome", nullable = false)
    private String nome;

    @Basic
    @Column(name = "sexo", nullable = true)
    private String sexo;

    @Basic
    @Column(name = "email", nullable = true)
    @Email
    private String email;

    @Basic
    @Column(name = "data_nascimento", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date dataNascimento;

    @Basic
    @Column(name = "naturalidade", nullable = true)
    private String naturalidade;

    @Basic
    @Column(name = "nacionalidade", nullable = true)
    private String nacionalidade;

    @Basic
    @Column(name = "cpf", nullable = false, unique = true)
    @Size(min = 11, max = 11)
    private String cpf;

    @Basic
    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Basic
    @LastModifiedDate
    @Column(name = "updated_at", nullable = true)
    private Date updatedAt;

    public Person(String nome, String sexo, String email, Date dataNascimento, String naturalidade, String nacionalidade, String cpf, Date createdAt) {
        this.nome = nome;
        this.sexo = sexo;
        this.email = email;
        this.dataNascimento = dataNascimento;
        this.naturalidade = naturalidade;
        this.nacionalidade = nacionalidade;
        this.cpf = cpf;
        this.createdAt = createdAt;
    }
}
