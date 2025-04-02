package barber_shop_application.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import barber_shop_application.entities.Customer;
import barber_shop_application.service.CustomerServiceImplemetation;

@WebMvcTest(controllers = CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CustomerServiceImplemetation customerService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateCustomer() throws Exception{
        Integer customerId = 1;
        Customer customer = Customer.builder()
                                    .id(customerId)
                                    .firstname("Test")
                                    .lastname("Customer")
                                    .username("testcustomer")
                                    .password("password")
                                    .email("testcustomer@email")
                                    .phone("123456789")
                                    .role("user")
                                    .build();
        when(customerService.saveCustomer(any(Customer.class))).thenReturn(customer);

        mockMvc.perform(post("/customer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testGetCustomerById() throws Exception{
        Integer customerId = 1;
        Customer customer = Customer.builder()
                                    .id(customerId)
                                    .firstname("Test")
                                    .lastname("Customer")
                                    .username("testcustomer")
                                    .password("password")
                                    .email("testcustomer@email")
                                    .phone("123456789")
                                    .role("user")
                                    .build();
        when(customerService.getCustomerById(customerId)).thenReturn(customer);

        mockMvc.perform(get("/customer/" + customerId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", CoreMatchers.is(customer.getId())));
    }

    @Test
    public void testGetAllCustomers() throws Exception{
        Customer customer1 = Customer.builder()
                                    .id(1)
                                    .firstname("Test")
                                    .lastname("Customer1")
                                    .username("testcustomer1")
                                    .password("password")
                                    .email("testcustomer1@email")
                                    .phone("123456789")
                                    .role("user")
                                    .build();
        Customer customer2 = Customer.builder()
                                    .id(2)
                                    .firstname("Test")
                                    .lastname("Customer2")
                                    .username("testcustomer2")
                                    .password("password")
                                    .email("testcustomer2@email")
                                    .phone("987654321")
                                    .role("user")
                                    .build();
        List<Customer> customers = Arrays.asList(customer1, customer2);
        when(customerService.getAllCustomers()).thenReturn(customers);

        mockMvc.perform(get("/customer"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", CoreMatchers.is(customers.size())));
    }

    @Test
    public void testUpdateCustomer() throws Exception{
        Integer customerId = 1;
        Customer customer = Customer.builder()
                                    .id(customerId)
                                    .firstname("Test")
                                    .lastname("Customer")
                                    .username("testcustomer")
                                    .password("password")
                                    .email("testcustomer@email")
                                    .phone("123456789")
                                    .role("user")
                                    .build();
        when(customerService.updateCustomer(eq(customerId), any(Customer.class))).thenReturn(customer);

        mockMvc.perform(put("/customer/" + customerId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", CoreMatchers.is(customer.getFirstname())));
    }

    @Test
    public void testDeleteCustomer() throws Exception{
        Integer customerId = 1;
        
        doNothing().when(customerService).deleteCustomer(customerId);

        mockMvc.perform(delete("/customer/" + customerId))
                .andExpect(status().isOk());
    }
}
