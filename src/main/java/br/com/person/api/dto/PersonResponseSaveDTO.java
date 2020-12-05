package br.com.person.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PersonResponseSaveDTO {

    private Boolean success;
    private String message;
    private Long id;
}
