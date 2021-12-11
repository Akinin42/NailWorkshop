package org.nailservice.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class NailOperationDto {

    private Integer id;

    @NotBlank
    private String name;

    @Min(1)
    private Integer cost;

    @Min(1)
    private Integer duration;
}
