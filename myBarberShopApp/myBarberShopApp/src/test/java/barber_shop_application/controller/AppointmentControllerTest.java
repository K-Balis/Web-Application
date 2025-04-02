package barber_shop_application.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.LocalTime;
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

import barber_shop_application.entities.Appointment;
import barber_shop_application.entities.Customer;
import barber_shop_application.entities.Employee;
import barber_shop_application.entities.EmployeeSchedule;
import barber_shop_application.service.AppointmentServiceImplementation;

@WebMvcTest(controllers = AppointmentController.class)
public class AppointmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AppointmentServiceImplementation appointmentService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testSaveAppointment() throws Exception{
        Integer employeeId = 1;
        Integer customerId = 1;
        Integer scheduleId = 1;
        LocalDate appointmentDate = LocalDate.of(2025, 1, 1);
        LocalTime appointmentStartTime = LocalTime.of(10, 0);

        AppointmentRequestBody appointmentRequestBody = AppointmentRequestBody.builder()
                                                                                .employeeId(employeeId)
                                                                                .customerId(customerId)
                                                                                .scheduleId(scheduleId)
                                                                                .date(appointmentDate)
                                                                                .startTime(appointmentStartTime)
                                                                                .build();
        Employee employee = Employee.builder()
                                    .id(appointmentRequestBody.getEmployeeId())
                                    .firstname("Test")
                                    .lastname("Employee1")
                                    .username("test_employee1")
                                    .password("password")
                                    .email("testemployee1@email")
                                    .phone("123456789")
                                    .role("Employee")
                                    .bio("Test Employee 1's bio..")
                                    .build();
        EmployeeSchedule employeeSchedule = EmployeeSchedule.builder()
                                                            .id(appointmentRequestBody.getScheduleId())
                                                            .employee(employee)
                                                            .date(appointmentDate)
                                                            .scheduleStartTime(LocalTime.of(8, 0))
                                                            .scheduleEndTime(LocalTime.of(13, 0))
                                                            .build();
        Customer customer = Customer.builder()
                                    .id(appointmentRequestBody.getCustomerId())
                                    .firstname("Test")
                                    .lastname("Customer")
                                    .username("testcustomer")
                                    .password("password")
                                    .email("testcustomer@email")
                                    .phone("123456789")
                                    .role("user")
                                    .build();

        Appointment appointment = Appointment.builder()
                                                .employee(employee)
                                                .customer(customer)
                                                .employeeSchedule(employeeSchedule)
                                                .date(appointmentRequestBody.getDate())
                                                .appointmentStart(appointmentRequestBody.getStartTime())
                                                .appointmentEnd(appointmentRequestBody.getStartTime().plusMinutes(30))
                                                .build();
        when(appointmentService.saveAppointment(appointmentRequestBody.getEmployeeId(), 
                                                    appointmentRequestBody.getCustomerId(), 
                                                    appointmentRequestBody.getScheduleId(), 
                                                    appointmentRequestBody.getDate(), 
                                                    appointmentRequestBody.getStartTime()))
                                                    .thenReturn(appointment);
        mockMvc.perform(post("/appointments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(appointmentRequestBody)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testUpdateAppointment() throws Exception{
        Integer employeeId = 1;
        Integer customerId = 1;
        Integer scheduleId = 1;
        Integer appointmentId = 1;
        LocalDate appointmentDate = LocalDate.of(2025, 1, 1);
        LocalTime appointmentStartTime = LocalTime.of(10, 0);

        AppointmentRequestBody appointmentRequestBody = AppointmentRequestBody.builder()
                                                                                .employeeId(employeeId)
                                                                                .customerId(customerId)
                                                                                .scheduleId(scheduleId)
                                                                                .date(appointmentDate)
                                                                                .startTime(appointmentStartTime)
                                                                                .build();
        Employee employee = Employee.builder()
                                    .id(appointmentRequestBody.getEmployeeId())
                                    .firstname("Test")
                                    .lastname("Employee1")
                                    .username("test_employee1")
                                    .password("password")
                                    .email("testemployee1@email")
                                    .phone("123456789")
                                    .role("Employee")
                                    .bio("Test Employee 1's bio..")
                                    .build();
        EmployeeSchedule employeeSchedule = EmployeeSchedule.builder()
                                                            .id(appointmentRequestBody.getScheduleId())
                                                            .employee(employee)
                                                            .date(appointmentDate)
                                                            .scheduleStartTime(LocalTime.of(8, 0))
                                                            .scheduleEndTime(LocalTime.of(13, 0))
                                                            .build();
        Customer customer = Customer.builder()
                                    .id(appointmentRequestBody.getCustomerId())
                                    .firstname("Test")
                                    .lastname("Customer")
                                    .username("testcustomer")
                                    .password("password")
                                    .email("testcustomer@email")
                                    .phone("123456789")
                                    .role("user")
                                    .build();

        Appointment appointment = Appointment.builder()
                                                .id(appointmentId)
                                                .employee(employee)
                                                .customer(customer)
                                                .employeeSchedule(employeeSchedule)
                                                .date(appointmentRequestBody.getDate())
                                                .appointmentStart(appointmentRequestBody.getStartTime())
                                                .appointmentEnd(appointmentRequestBody.getStartTime().plusMinutes(30))
                                                .build();
        when(appointmentService.updateAppointment(appointmentId,  
                                                    appointmentRequestBody.getEmployeeId(), 
                                                    appointmentRequestBody.getCustomerId(), 
                                                    appointmentRequestBody.getScheduleId(), 
                                                    appointmentRequestBody.getDate(), 
                                                    appointmentRequestBody.getStartTime()))
                                                    .thenReturn(appointment);
        mockMvc.perform(put("/appointments/" + appointmentId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(appointmentRequestBody)))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAllAppointments() throws Exception{
        LocalDate appointmentDate = LocalDate.of(2025, 1, 1);
        Employee employee = Employee.builder()
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
        EmployeeSchedule employeeSchedule = EmployeeSchedule.builder()
                                                            .id(1)
                                                            .employee(employee)
                                                            .date(appointmentDate)
                                                            .scheduleStartTime(LocalTime.of(8, 0))
                                                            .scheduleEndTime(LocalTime.of(13, 0))
                                                            .build();
        Customer customer = Customer.builder()
                                    .id(1)
                                    .firstname("Test")
                                    .lastname("Customer")
                                    .username("testcustomer")
                                    .password("password")
                                    .email("testcustomer@email")
                                    .phone("123456789")
                                    .role("user")
                                    .build();

        Appointment appointment1 = Appointment.builder()
                                                .employee(employee)
                                                .customer(customer)
                                                .employeeSchedule(employeeSchedule)
                                                .date(appointmentDate)
                                                .appointmentStart(LocalTime.of(9, 0))
                                                .appointmentEnd(LocalTime.of(9, 30))
                                                .build();
        Appointment appointment2 = Appointment.builder()
                                                .employee(employee)
                                                .customer(customer)
                                                .employeeSchedule(employeeSchedule)
                                                .date(appointmentDate)
                                                .appointmentStart(LocalTime.of(9, 0))
                                                .appointmentEnd(LocalTime.of(9, 30))
                                                .build();
        List<Appointment> allAppointments = Arrays.asList(appointment1, appointment2);
        when(appointmentService.getAllAppointments()).thenReturn(allAppointments);

        mockMvc.perform(get("/appointments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", CoreMatchers.is(allAppointments.size())));

    }

    @Test
    public void testGetAppointmentById() throws Exception{
        Integer appointmentId = 1;
        Employee employee = Employee.builder()
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
        EmployeeSchedule employeeSchedule = EmployeeSchedule.builder()
                                                            .id(1)
                                                            .employee(employee)
                                                            .date(LocalDate.of(2025, 1, 1))
                                                            .scheduleStartTime(LocalTime.of(8, 0))
                                                            .scheduleEndTime(LocalTime.of(13, 0))
                                                            .build();
        Customer customer = Customer.builder()
                                    .id(1)
                                    .firstname("Test")
                                    .lastname("Customer")
                                    .username("testcustomer")
                                    .password("password")
                                    .email("testcustomer@email")
                                    .phone("123456789")
                                    .role("user")
                                    .build();

        Appointment appointment = Appointment.builder()
                                                .id(appointmentId)
                                                .employee(employee)
                                                .customer(customer)
                                                .employeeSchedule(employeeSchedule)
                                                .date(LocalDate.of(2025, 1, 1))
                                                .appointmentStart(LocalTime.of(9, 0))
                                                .appointmentEnd(LocalTime.of(9, 30))
                                                .build();
        when(appointmentService.getAppointmentById(appointmentId)).thenReturn(appointment);

        mockMvc.perform(get("/appointments/"+appointmentId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", CoreMatchers.is(appointment.getId())));

    }

    @Test
    public void testDeleteAppointment() throws Exception{
        Integer appointmentId = 1;

        doNothing().when(appointmentService).deleteAppointment(appointmentId);

        mockMvc.perform(delete("/appointments/"+appointmentId))
                .andExpect(status().isOk());
    }
    
    @Test
    public void testGetAppointmentsByDate() throws Exception{
        LocalDate appointmentDate = LocalDate.of(2025, 1, 1);
        Employee employee = Employee.builder()
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
        EmployeeSchedule employeeSchedule = EmployeeSchedule.builder()
                                                            .id(1)
                                                            .employee(employee)
                                                            .date(appointmentDate)
                                                            .scheduleStartTime(LocalTime.of(8, 0))
                                                            .scheduleEndTime(LocalTime.of(13, 0))
                                                            .build();
        Customer customer = Customer.builder()
                                    .id(1)
                                    .firstname("Test")
                                    .lastname("Customer")
                                    .username("testcustomer")
                                    .password("password")
                                    .email("testcustomer@email")
                                    .phone("123456789")
                                    .role("user")
                                    .build();

        Appointment appointment = Appointment.builder()
                                                .employee(employee)
                                                .customer(customer)
                                                .employeeSchedule(employeeSchedule)
                                                .date(appointmentDate)
                                                .appointmentStart(LocalTime.of(9, 0))
                                                .appointmentEnd(LocalTime.of(9, 30))
                                                .build();
        List<Appointment> allAppointments = Arrays.asList(appointment);
        when(appointmentService.getAppointmentsByDate(appointmentDate)).thenReturn(allAppointments);

        mockMvc.perform(get("/appointments/by-date")
                        .param("date", appointmentDate.toString()))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.size()", CoreMatchers.is(allAppointments.size())))
                        .andExpect(jsonPath("$[0].date", CoreMatchers.is(appointmentDate.toString())));
    }
    
    @Test
    public void testGetAppointmentsByDates() throws Exception{
        LocalDate appointmentDate = LocalDate.of(2025, 1, 1);
        LocalDate startFilterDate = appointmentDate.minusDays(1);
        LocalDate endFilterDate = appointmentDate.plusDays(1);
        Employee employee = Employee.builder()
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
        EmployeeSchedule employeeSchedule = EmployeeSchedule.builder()
                                                            .id(1)
                                                            .employee(employee)
                                                            .date(appointmentDate)
                                                            .scheduleStartTime(LocalTime.of(8, 0))
                                                            .scheduleEndTime(LocalTime.of(13, 0))
                                                            .build();
        Customer customer = Customer.builder()
                                    .id(1)
                                    .firstname("Test")
                                    .lastname("Customer")
                                    .username("testcustomer")
                                    .password("password")
                                    .email("testcustomer@email")
                                    .phone("123456789")
                                    .role("user")
                                    .build();

        Appointment appointment = Appointment.builder()
                                                .employee(employee)
                                                .customer(customer)
                                                .employeeSchedule(employeeSchedule)
                                                .date(appointmentDate)
                                                .appointmentStart(LocalTime.of(9, 0))
                                                .appointmentEnd(LocalTime.of(9, 30))
                                                .build();
        List<Appointment> allAppointments = Arrays.asList(appointment);
        when(appointmentService.getAppointmentsByDates(startFilterDate, endFilterDate)).thenReturn(allAppointments);

        mockMvc.perform(get("/appointments/by-dates")
                        .param("startDate", startFilterDate.toString())
                        .param("endDate", endFilterDate.toString()))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.size()", CoreMatchers.is(allAppointments.size())));
    }
    
    @Test
    public void testGetAppointmentsByEmployeeId() throws Exception{
        LocalDate appointmentDate = LocalDate.of(2025, 1, 1);
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
        EmployeeSchedule employeeSchedule = EmployeeSchedule.builder()
                                                            .id(1)
                                                            .employee(employee)
                                                            .date(appointmentDate)
                                                            .scheduleStartTime(LocalTime.of(8, 0))
                                                            .scheduleEndTime(LocalTime.of(13, 0))
                                                            .build();
        Customer customer = Customer.builder()
                                    .id(1)
                                    .firstname("Test")
                                    .lastname("Customer")
                                    .username("testcustomer")
                                    .password("password")
                                    .email("testcustomer@email")
                                    .phone("123456789")
                                    .role("user")
                                    .build();

        Appointment appointment = Appointment.builder()
                                                .employee(employee)
                                                .customer(customer)
                                                .employeeSchedule(employeeSchedule)
                                                .date(appointmentDate)
                                                .appointmentStart(LocalTime.of(9, 0))
                                                .appointmentEnd(LocalTime.of(9, 30))
                                                .build();
        List<Appointment> allAppointments = Arrays.asList(appointment);
        when(appointmentService.getAppointmentsByEmployeeId(employeeId)).thenReturn(allAppointments);

        mockMvc.perform(get("/appointments/employee/"+employeeId))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.size()", CoreMatchers.is(allAppointments.size())))
                        .andExpect(jsonPath("$[0].employee.id", CoreMatchers.is(employee.getId())));
    }
    
    @Test
    public void testGetAppointmentsByCustomerId() throws Exception{
        LocalDate appointmentDate = LocalDate.of(2025, 1, 1);
        Integer customerId = 1;
        Employee employee = Employee.builder()
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
        EmployeeSchedule employeeSchedule = EmployeeSchedule.builder()
                                                            .id(1)
                                                            .employee(employee)
                                                            .date(appointmentDate)
                                                            .scheduleStartTime(LocalTime.of(8, 0))
                                                            .scheduleEndTime(LocalTime.of(13, 0))
                                                            .build();
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

        Appointment appointment = Appointment.builder()
                                                .employee(employee)
                                                .customer(customer)
                                                .employeeSchedule(employeeSchedule)
                                                .date(appointmentDate)
                                                .appointmentStart(LocalTime.of(9, 0))
                                                .appointmentEnd(LocalTime.of(9, 30))
                                                .build();
        List<Appointment> allAppointments = Arrays.asList(appointment);
        when(appointmentService.getAppointmentsByCustomerId(customerId)).thenReturn(allAppointments);

        mockMvc.perform(get("/appointments/customer/"+customerId))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.size()", CoreMatchers.is(allAppointments.size())))
                        .andExpect(jsonPath("$[0].customer.id", CoreMatchers.is(customer.getId())));

    }

}