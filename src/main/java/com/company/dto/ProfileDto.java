package com.company.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileDto {
    private Integer id;

    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String middleName;
    @NotBlank
    private LocalDate birthDate;
    @NotBlank
    @Size(max = 10,min = 10)
    private String passportNumber;
    @NotBlank()
    @Size(min = 13,max = 13)
    private String phone;

    private LocalDateTime createdDate;
}
