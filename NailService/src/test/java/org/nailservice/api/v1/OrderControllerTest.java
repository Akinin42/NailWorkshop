package org.nailservice.api.v1;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.nailservice.GlobalExceptionHandler;
import org.nailservice.dto.OrderDto;
import org.nailservice.exception.InvalidOrderTimeException;
import org.nailservice.service.OrderService;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    private MockMvc mockMvc;

    private ObjectMapper mapper;

    @Mock
    private OrderService orderServiceMock;

    private OrderController orderController;

    @BeforeEach
    public void setUpBeforeClass() throws Exception {
        mapper = new ObjectMapper();
        orderController = new OrderController(orderServiceMock);
        mockMvc = MockMvcBuilders.standaloneSetup(orderController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }
    
    @Test
    void testAddOrder() throws Exception {
        OrderDto order = new OrderDto();
        order.setCustomerName("New customer");
        order.setCustomerPhone("856425632455");
        order.setStart("2010-10-10T10:00");
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/api/v1/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(order));
        ResultActions result = mockMvc.perform(request);
        result.andExpect(status().isCreated());
        verify(orderServiceMock).createOrder(order);
    }
    
    @Test
    void testAddOrderWhenInputWithInvalidName() throws Exception {
        OrderDto order = new OrderDto();
        order.setCustomerName("   ");
        order.setCustomerPhone("856425632455");
        order.setStart("2010-10-10T10:00");
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/api/v1/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(order));
        ResultActions result = mockMvc.perform(request);
        result.andExpect(exception -> assertEquals(MethodArgumentNotValidException.class,
                exception.getResolvedException().getClass()))
                .andExpect(exception -> assertTrue(
                        exception.getResolvedException().getMessage().contains("blank")))
                .andExpect(exception -> assertTrue(exception.getResolvedException().getMessage().contains("customerName")))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    void testAddOrderWhenInputWithInvalidPhone() throws Exception {
        OrderDto order = new OrderDto();
        order.setCustomerName("New customer");
        order.setCustomerPhone("invalid phone");
        order.setStart("2010-10-10T10:00");
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/api/v1/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(order));
        ResultActions result = mockMvc.perform(request);
        result.andExpect(exception -> assertEquals(MethodArgumentNotValidException.class,
                exception.getResolvedException().getClass()))
                .andExpect(exception -> assertTrue(
                        exception.getResolvedException().getMessage().contains("Input phone is invalid")))
                .andExpect(exception -> assertTrue(exception.getResolvedException().getMessage().contains("customerPhone")))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    void testAddOrderWhenInputStartEmpty() throws Exception {
        OrderDto order = new OrderDto();
        order.setCustomerName("New customer");
        order.setCustomerPhone("856425632455");
        order.setStart("");
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/api/v1/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(order));
        ResultActions result = mockMvc.perform(request);
        result.andExpect(exception -> assertEquals(MethodArgumentNotValidException.class,
                exception.getResolvedException().getClass()))
                .andExpect(exception -> assertTrue(
                        exception.getResolvedException().getMessage().contains("empty")))
                .andExpect(exception -> assertTrue(exception.getResolvedException().getMessage().contains("start")))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    void testAddOrderWhenInputStartInvalid() throws Exception {
        OrderDto order = new OrderDto();
        order.setCustomerName("New customer");
        order.setCustomerPhone("856425632455");
        order.setStart("2010-10-10T10:00");
        doThrow(new InvalidOrderTimeException("Message about invalid time!")).when(orderServiceMock)
            .createOrder(order);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/api/v1/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(order));
        ResultActions result = mockMvc.perform(request);
        result.andExpect(exception -> assertEquals(
                exception.getResolvedException().getMessage(), "400 BAD_REQUEST \"Message about invalid time!\""))                
                .andExpect(status().isBadRequest());
    }
    
    @Test
    void testDeleteOrder() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete("/api/v1/orders/1")
                .contentType(MediaType.APPLICATION_JSON);
        ResultActions result = mockMvc.perform(request);
        result.andExpect(status().isOk());
        verify(orderServiceMock).deleteById(1);
    }
}
