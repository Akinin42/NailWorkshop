package org.nailservice.api.v1;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.nailservice.GlobalExceptionHandler;
import org.nailservice.dto.ProcedureDto;
import org.nailservice.entity.Procedure;
import org.nailservice.exception.EntityAlreadyExistException;
import org.nailservice.exception.EntityNotExistException;
import org.nailservice.service.ProcedureService;
import org.nailservice.utils.CreatorTestEntities;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
class ProcedureControllerTest {

    private MockMvc mockMvc;

    private ObjectMapper mapper;

    @Mock
    private ProcedureService procedureServiceMock;

    private ProcedureController procedureController;

    @BeforeEach
    public void setUpBeforeClass() throws Exception {
        mapper = new ObjectMapper();
        procedureController = new ProcedureController(procedureServiceMock);
        mockMvc = MockMvcBuilders.standaloneSetup(procedureController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }
    
    @Test
    void testFindAll() throws Exception {
        List<Procedure> procedures = CreatorTestEntities.createTestProcedures();
        when(procedureServiceMock.findAllProcedures()).thenReturn(procedures);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/api/v1/procedures")
                .contentType(MediaType.APPLICATION_JSON);
        ResultActions result = mockMvc.perform(request);
        result.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name", is("manicure")))
                .andExpect(jsonPath("$[1].name", is("pedicure")))
                .andExpect(jsonPath("$[0].cost", is(1000)))
                .andExpect(jsonPath("$[1].cost", is(1500)))
                .andExpect(jsonPath("$", Matchers.hasSize(2)));
    }
    
    @Test
    void testAddProcedure() throws Exception {
        ProcedureDto procedure = new ProcedureDto();
        procedure.setName("Test procedure");
        procedure.setCost(100);
        procedure.setDuration(60);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/api/v1/procedures")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(procedure));
        ResultActions result = mockMvc.perform(request);
        result.andExpect(status().isCreated());
        verify(procedureServiceMock).createProcedure(procedure);
    }
    
    @Test
    void testAddProcedureWhenInputInvalidName() throws Exception {
        ProcedureDto procedure = new ProcedureDto();
        procedure.setName("   ");
        procedure.setCost(100);
        procedure.setDuration(60);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/api/v1/procedures")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(procedure));
        ResultActions result = mockMvc.perform(request);
        result.andExpect(exception -> assertEquals(MethodArgumentNotValidException.class,
                exception.getResolvedException().getClass()))
                .andExpect(exception -> assertTrue(
                        exception.getResolvedException().getMessage().contains("blank")))
                .andExpect(exception -> assertTrue(exception.getResolvedException().getMessage().contains("name")))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    void testAddProcedureWhenInputInvalidCost() throws Exception {
        ProcedureDto procedure = new ProcedureDto();
        procedure.setName("Test procedure");
        procedure.setCost(0);
        procedure.setDuration(60);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/api/v1/procedures")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(procedure));
        ResultActions result = mockMvc.perform(request);
        result.andExpect(exception -> assertEquals(MethodArgumentNotValidException.class,
                exception.getResolvedException().getClass()))
                .andExpect(exception -> assertTrue(
                        exception.getResolvedException().getMessage().contains("must be greater than or equal to 1")))
                .andExpect(exception -> assertTrue(exception.getResolvedException().getMessage().contains("cost")))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    void testAddProcedureWhenInputInvalidDuration() throws Exception {
        ProcedureDto procedure = new ProcedureDto();
        procedure.setName("Test procedure");
        procedure.setCost(100);
        procedure.setDuration(-5);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/api/v1/procedures")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(procedure));
        ResultActions result = mockMvc.perform(request);
        result.andExpect(exception -> assertEquals(MethodArgumentNotValidException.class,
                exception.getResolvedException().getClass()))
                .andExpect(exception -> assertTrue(
                        exception.getResolvedException().getMessage().contains("must be greater than or equal to 1")))
                .andExpect(exception -> assertTrue(exception.getResolvedException().getMessage().contains("duration")))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    void testAddProcedureWhenThisProcedureExist() throws Exception {
        ProcedureDto procedure = new ProcedureDto();
        procedure.setName("Existed procedure");
        procedure.setCost(100);
        procedure.setDuration(60);
        doThrow(new EntityAlreadyExistException("procedure exists")).when(procedureServiceMock)
            .createProcedure(procedure);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/api/v1/procedures")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(procedure));
        ResultActions result = mockMvc.perform(request);
        result.andExpect(exception -> assertEquals(
                exception.getResolvedException().getMessage(), "procedure exists"))                
                .andExpect(status().isBadRequest());
    }
    
    @Test
    void testUpdateProcedure() throws Exception {
        ProcedureDto procedure = new ProcedureDto();
        procedure.setId(1);
        procedure.setName("Test procedure");
        procedure.setCost(100);
        procedure.setDuration(60);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.patch("/api/v1/procedures")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(procedure));
        ResultActions result = mockMvc.perform(request);
        result.andExpect(status().isOk());
        verify(procedureServiceMock).editProcedure(procedure);
    }
    
    @Test
    void testUpdateProcedureWhenInputInvalidName() throws Exception {
        ProcedureDto procedure = new ProcedureDto();
        procedure.setId(1);
        procedure.setName("   ");
        procedure.setCost(100);
        procedure.setDuration(60);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.patch("/api/v1/procedures")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(procedure));
        ResultActions result = mockMvc.perform(request);
        result.andExpect(exception -> assertEquals(MethodArgumentNotValidException.class,
                exception.getResolvedException().getClass()))
                .andExpect(exception -> assertTrue(
                        exception.getResolvedException().getMessage().contains("blank")))
                .andExpect(exception -> assertTrue(exception.getResolvedException().getMessage().contains("name")))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    void testUpdateProcedureWhenInputInvalidCost() throws Exception {
        ProcedureDto procedure = new ProcedureDto();
        procedure.setId(1);
        procedure.setName("Test procedure");
        procedure.setCost(0);
        procedure.setDuration(60);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.patch("/api/v1/procedures")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(procedure));
        ResultActions result = mockMvc.perform(request);
        result.andExpect(exception -> assertEquals(MethodArgumentNotValidException.class,
                exception.getResolvedException().getClass()))
                .andExpect(exception -> assertTrue(
                        exception.getResolvedException().getMessage().contains("must be greater than or equal to 1")))
                .andExpect(exception -> assertTrue(exception.getResolvedException().getMessage().contains("cost")))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    void testUpdateProcedureWhenInputInvalidDuration() throws Exception {
        ProcedureDto procedure = new ProcedureDto();
        procedure.setId(1);
        procedure.setName("Test procedure");
        procedure.setCost(100);
        procedure.setDuration(-5);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.patch("/api/v1/procedures")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(procedure));
        ResultActions result = mockMvc.perform(request);
        result.andExpect(exception -> assertEquals(MethodArgumentNotValidException.class,
                exception.getResolvedException().getClass()))
                .andExpect(exception -> assertTrue(
                        exception.getResolvedException().getMessage().contains("must be greater than or equal to 1")))
                .andExpect(exception -> assertTrue(exception.getResolvedException().getMessage().contains("duration")))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    void testUpdateProcedureWhenThisProcedureNotExist() throws Exception {
        ProcedureDto procedure = new ProcedureDto();
        procedure.setId(1);
        procedure.setName("Not existed procedure");
        procedure.setCost(100);
        procedure.setDuration(60);
        doThrow(new EntityNotExistException("procedure not exists")).when(procedureServiceMock)
            .editProcedure(procedure);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.patch("/api/v1/procedures")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(procedure));
        ResultActions result = mockMvc.perform(request);
        result.andExpect(exception -> assertEquals(
                exception.getResolvedException().getMessage(), "procedure not exists"))                
                .andExpect(status().isBadRequest());
    }
    
    @Test
    void testDeleteProcedure() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete("/api/v1/procedures/1")
                .contentType(MediaType.APPLICATION_JSON);
        ResultActions result = mockMvc.perform(request);
        result.andExpect(status().isOk());
        verify(procedureServiceMock).deleteById(1);
    }
}
