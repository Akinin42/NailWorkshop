package org.nailservice.dto;

import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import org.nailservice.entity.Procedure;
import org.nailservice.service.validator.Phone;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class OrderDto {

    private Integer id;

    @NotBlank
    private String customerName;

    @NotBlank
    @Phone
    private String customerPhone;

    private Set<Procedure> procedures;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @NotEmpty
    private String start;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @NotEmpty
    private String end;
}
