package br.com.person.api.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
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
    @Column(name = "sexo", nullable = false)
    private Boolean sexo;

    @Basic
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Basic
    @Column(name = "data_nascimento", nullable = false)
    private Date dataNascimento;

    @Basic
    @Column(name = "naturalidade", nullable = false)
    private String naturalidade;

    @Basic
    @Column(name = "nacionalidade", nullable = false)
    private String nacionalidade;

    @Basic
    @Column(name = "cpf", nullable = false, unique = true)
    private String cpf;

    @Basic
    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Basic
    @LastModifiedDate
    @Column(name = "updated_at", nullable = true)
    private Date updatedAt;

}
