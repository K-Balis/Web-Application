package barber_shop_application.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import org.hamcrest.CoreMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import barber_shop_application.entities.Employee;
import barber_shop_application.service.EmployeeServiceImplemetation;

@WebMvcTest(controllers = EmployeeController.class)
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EmployeeServiceImplemetation employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateEmployee() throws Exception{
        Employee employee = Employee.builder()
                                    .firstname("Test")
                                    .lastname("Employee1")
                                    .username("test_employee1")
                                    .password("password")
                                    .email("testemployee1@email")
                                    .phone("123456789")
                                    .role("Employee")
                                    .bio("Test Employee 1's bio..")
                                    .build();
        when(employeeService.saveEmployee(any(Employee.class))).thenReturn(employee);

        mockMvc.perform(post("/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employee)))
                        .andExpect(status().isCreated());
    }

    @Test
    public void testGetEmployeeById() throws Exception{
        Integer employeeId = 1;
        Employee employee = Employee.builder()
                                    .id(employeeId)
                                    .firstname("Test")
                                    .lastname("Employee1")
                                    .username("test_employee1")
                                    .password("password")
                                    .email("testemployee1@email")
                                    .phone("123456789")
                                    .role("Employee")
                                    .bio("Test Employee 1's bio..")
                                    .build();
        when(employeeService.getEmployeeById(employeeId)).thenReturn(employee);

        mockMvc.perform(get("/employee/" + employeeId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", CoreMatchers.is(employee.getId())));
    }

    @Test
    public void testGetAllEmployees() throws Exception{
        Employee employee1 = Employee.builder()
                                    .id(1)
                                    .firstname("Test")
                                    .lastname("Employee1")
                                    .username("test_employee1")
                                    .password("password")
                                    .email("testemployee1@email")
                                    .phone("123456789")
                                    .role("Employee")
                                    .bio("Test Employee 1's bio..")
                                    .build();
        Employee employee2 = Employee.builder()
                                    .id(2)
                                    .firstname("Test")
                                    .lastname("Employee2")
                                    .username("test_employee2")
                                    .password("password")
                                    .email("testemployee2@email")
                                    .phone("987654321")
                                    .role("Employee")
                                    .bio("Test Employee 2's bio..")
                                    .build();
        List<Employee> employees = Arrays.asList(employee1, employee2);
        when(employeeService.getAllEmployees()).thenReturn(employees);

        mockMvc.perform(get("/employee"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", CoreMatchers.is(employees.size())));
    }

    @Test
    public void testGetAvailableEmployees() throws Exception{
        LocalDate date = LocalDate.of(2025, 1, 1);
        LocalTime time = LocalTime.of(10, 0);
        Employee employee1 = Employee.builder()
                                    .id(1)
                                    .firstname("Test")
                                    .lastname("Employee1")
                                    .username("test_employee1")
                                    .password("password")
                                    .email("testemployee1@email")
                                    .phone("123456789")
                                    .role("Employee")
                                    .bio("Test Employee 1's bio..")
                                    .build();
        Employee employee2 = Employee.builder()
                                    .id(2)
                                    .firstname("Test")
                                    .lastname("Employee2")
                                    .username("test_employee2")
                                    .password("password")
                                    .email("testemployee2@email")
                                    .phone("987654321")
                                    .role("Employee")
                                    .bio("Test Employee 2's bio..")
                                    .build();
        List<Employee> employees = Arrays.asList(employee1, employee2);
        when(employeeService.getAvailableEmployees(date, time)).thenReturn(employees);

        mockMvc.perform(get("/employee/availableEmployees")
                .param("date", date.toString())
                .param("time", time.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", CoreMatchers.is(employees.size())));
    }

    @Test
    public void testUpdateEmployee() throws Exception{
        Integer employeeId = 1;
        Employee employee = Employee.builder()
                                    .id(employeeId)
                                    .firstname("Test")
                                    .lastname("Employee1")
                                    .username("test_employee1")
                                    .password("password")
                                    .email("testemployee1@email")
                                    .phone("123456789")
                                    .role("Employee")
                                    .bio("Test Employee 1's bio..")
                                    .build();
        when(employeeService.updateEmployee(eq(employeeId), any(Employee.class))).thenReturn(employee);

        mockMvc.perform(put("/employee/" + employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", CoreMatchers.is(employee.getFirstname())));
    }

    @Test
    public void testDeleteEmployee() throws Exception {
        Integer employeeId = 1;

        doNothing().when(employeeService).deleteEmployee(employeeId);

        mockMvc.perform(delete("/employee/" + employeeId))
                .andExpect(status().isOk());
    }
}
