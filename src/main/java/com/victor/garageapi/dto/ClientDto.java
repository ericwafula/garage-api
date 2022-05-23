package com.victor.garageapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientDto {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Integer age;
    private String gender;
    private String location;
    private String address;
}
