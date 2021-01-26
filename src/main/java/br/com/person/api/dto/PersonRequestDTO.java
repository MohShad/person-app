package br.com.person.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonRequestDTO {


    @NotBlank(message = "Nome é obrigatório.")
    @Size(min = 3, max = 100)
    private String nome;

    private String sexo;

    @Email
    private String email;

    @NotBlank(message = "Data de nascimento é obrigatório.")
    private Date dataNascimento;

    private String naturalidade;

    private String nacionalidade;

    @NotBlank(message = "CPF é obrigatório.")
    @Size(min = 11, max = 11)
    private String cpf;

}
