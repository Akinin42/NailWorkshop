package org.nailservice.api.v1;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.nailservice.GlobalExceptionHandler;
import org.nailservice.entity.Order;
import org.nailservice.entity.Shedule;
import org.nailservice.service.SheduleService;
import org.nailservice.utils.CreatorTestEntities;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


@ExtendWith(MockitoExtension.class)
class SheduleControllerTest {

    private MockMvc mockMvc;

    @Mock
    private SheduleService sheduleServiceMock;

    private SheduleController sheduleController;

    @BeforeEach
    public void setUpBeforeClass() throws Exception {
        sheduleController = new SheduleController(sheduleServiceMock);
        mockMvc = MockMvcBuilders.standaloneSetup(sheduleController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }
    
    @Test
    void testGetShedule() throws Exception {        
        List<Order> orders = CreatorTestEntities.createOrders();        
        Shedule shedule = new Shedule(LocalDate.now(), orders);
        when(sheduleServiceMock.createShedule(LocalDate.now())).thenReturn(shedule);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/api/v1/shedules")
                .contentType(MediaType.APPLICATION_JSON);
        ResultActions result = mockMvc.perform(request);
        result.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.orders[0].id", is(1)))
                .andExpect(jsonPath("$.orders[1].id", is(2)))
                .andExpect(jsonPath("$.orders", Matchers.hasSize(2)));
    }
    
    @Test
    void testGetSheduleWhenInputDay() throws Exception {        
        List<Order> orders = CreatorTestEntities.createOrders();        
        Shedule shedule = new Shedule(LocalDate.parse("2021-12-27"), orders);
        when(sheduleServiceMock.createShedule(LocalDate.of(2021, Month.DECEMBER, 27))).thenReturn(shedule);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/api/v1/shedules/date?date=2021-12-27")
                .contentType(MediaType.APPLICATION_JSON);
        ResultActions result = mockMvc.perform(request);
        result.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.orders[0].id", is(1)))
                .andExpect(jsonPath("$.orders[1].id", is(2)))
                .andExpect(jsonPath("$.orders", Matchers.hasSize(2)));
    }
    
    @Test
    void testGetWeekShedule() throws Exception {        
        List<Order> orders = CreatorTestEntities.createOrders();        
        Shedule shedule = new Shedule(LocalDate.now(), orders);
        List<Shedule> weekShedules = new ArrayList<>();
        weekShedules.add(shedule);
        when(sheduleServiceMock.createWeekShedule(LocalDate.now())).thenReturn(weekShedules);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/api/v1/shedules/week")
                .contentType(MediaType.APPLICATION_JSON);
        ResultActions result = mockMvc.perform(request);
        result.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$", Matchers.hasSize(1)));
    }
    
    @Test
    void testGetMonthShedule() throws Exception {        
        List<Order> orders = CreatorTestEntities.createOrders();        
        Shedule shedule = new Shedule(LocalDate.now(), orders);
        List<Shedule> monthShedules = new ArrayList<>();
        monthShedules.add(shedule);
        when(sheduleServiceMock.createMonthShedule(LocalDate.now())).thenReturn(monthShedules);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/api/v1/shedules/month")
                .contentType(MediaType.APPLICATION_JSON);
        ResultActions result = mockMvc.perform(request);
        result.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$", Matchers.hasSize(1)));
    }
}
