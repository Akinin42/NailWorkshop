package org.nailservice.dto;

import javax.validation.constraints.NotBlank;

import org.nailservice.service.validator.Phone;

import lombok.Data;

@Data
public class CustomerDto {

    private Integer id;

    @NotBlank
    private String name;

    @NotBlank
    @Phone
    private String phone;
}
