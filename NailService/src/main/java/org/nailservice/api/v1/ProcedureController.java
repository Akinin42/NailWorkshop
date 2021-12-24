package org.nailservice.api.v1;

import java.util.List;

import org.nailservice.dto.ProcedureDto;
import org.nailservice.entity.Procedure;
import org.nailservice.service.ProcedureService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/procedures")
public class ProcedureController {

    private final ProcedureService procedureService;

    @GetMapping
    public List<Procedure> findAll() {
        return procedureService.findAllProcedures();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addProcedure(@RequestBody ProcedureDto procedure) {
        procedureService.createProcedure(procedure);
    }

    @PatchMapping
    public void updateProcedure(@RequestBody ProcedureDto procedure) {
        procedureService.editProcedure(procedure);
    }

    @DeleteMapping("{id}")
    public void deleteProcedure(@PathVariable("id") Integer id) {
        procedureService.deleteById(id);
    }
}
