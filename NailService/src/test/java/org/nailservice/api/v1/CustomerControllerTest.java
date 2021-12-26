package org.nailservice.api.v1;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.nailservice.GlobalExceptionHandler;
import org.nailservice.entity.Customer;
import org.nailservice.service.CustomerService;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CustomerService customerServiceMock;

    private CustomerController customerController;

    @BeforeEach
    public void setUpBeforeClass() throws Exception {
        customerController = new CustomerController(customerServiceMock);
        mockMvc = MockMvcBuilders.standaloneSetup(customerController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void testFindAll() throws Exception {
        List<Customer> customers = createTestCustomers();
        when(customerServiceMock.findAllCustomers()).thenReturn(customers);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON);
        ResultActions result = mockMvc.perform(request);
        result.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name", is("First customer")))
                .andExpect(jsonPath("$[1].name", is("Second customer")))
                .andExpect(jsonPath("$[0].phone", is("phone first")))
                .andExpect(jsonPath("$[1].phone", is("phone second")))
                .andExpect(jsonPath("$", Matchers.hasSize(2)));
    }

    @Test
    void testDelete() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete("/api/v1/customers/1")
                .contentType(MediaType.APPLICATION_JSON);
        ResultActions result = mockMvc.perform(request);
        result.andExpect(status().isOk());
        verify(customerServiceMock).deleteById(1);
    }

    private List<Customer> createTestCustomers() {
        List<Customer> customers = new ArrayList<>();
        Customer customer = Customer.builder()
                .withId(1)
                .withName("First customer")
                .withPhone("phone first")
                .build();
        customers.add(customer);
        customer = Customer.builder()
                .withId(2)
                .withName("Second customer")
                .withPhone("phone second")
                .build();
        customers.add(customer);
        return customers;
    }
}
