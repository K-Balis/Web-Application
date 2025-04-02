package barber_shop_application.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

import barber_shop_application.entities.Employee;
import barber_shop_application.entities.EmployeeSchedule;
import barber_shop_application.service.EmployeeScheduleServiceImplementation;

@WebMvcTest(controllers = EmployeeScheduleController.class)
public class EmployeeScheduleControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EmployeeScheduleServiceImplementation employeeScheduleService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testSaveEmployeeSchedule() throws Exception{
        Integer employeeId = 1;
        LocalDate date = LocalDate.of(2025, 1, 1);
        LocalTime startTime = LocalTime.of(9, 0);
        LocalTime endTime = LocalTime.of(13, 0);
        
        EmployeeScheduleRequestBody employeeScheduleRequestBody = EmployeeScheduleRequestBody.builder()
                                                                                                .employeeId(employeeId)
                                                                                                .date(date)
                                                                                                .startTime(startTime)
                                                                                                .endTime(endTime)
                                                                                                .build();
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
        EmployeeSchedule employeeSchedule = EmployeeSchedule.builder()
                                                            .employee(employee)
                                                            .date(date)
                                                            .scheduleStartTime(startTime)
                                                            .scheduleEndTime(endTime)
                                                            .build();
        when(employeeScheduleService.saveEmployeeSchedule(employeeScheduleRequestBody.getEmployeeId(), 
                                                            employeeScheduleRequestBody.getDate(), 
                                                            employeeScheduleRequestBody.getStartTime(), 
                                                            employeeScheduleRequestBody.getEndTime()))
                                                            .thenReturn(employeeSchedule);
        mockMvc.perform(post("/schedule")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employeeScheduleRequestBody)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testGetAllEmployeeSchedules() throws Exception{
        LocalDate date = LocalDate.of(2025, 1, 1);
        LocalTime startTime = LocalTime.of(9, 0);
        LocalTime endTime = LocalTime.of(13, 0);
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
        EmployeeSchedule employeeSchedule1 = EmployeeSchedule.builder()
                                                            .employee(employee)
                                                            .date(date)
                                                            .scheduleStartTime(startTime)
                                                            .scheduleEndTime(endTime)
                                                            .build();
        EmployeeSchedule employeeSchedule2 = EmployeeSchedule.builder()
                                                            .employee(employee)
                                                            .date(date.plusDays(1))
                                                            .scheduleStartTime(startTime.plusHours(1))
                                                            .scheduleEndTime(endTime.plusHours(1))
                                                            .build();
        List<EmployeeSchedule> allSchedules = Arrays.asList(employeeSchedule1, employeeSchedule2);
        when(employeeScheduleService.getAllEmployeeSchedules()).thenReturn(allSchedules);

        mockMvc.perform(get("/schedule"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", CoreMatchers.is(allSchedules.size())));
    }

    @Test
    public void testGetScheduleIdForAppointment() throws Exception{
        Integer scheduleId = 1;
        Integer employeeId = 1;
        LocalDate date = LocalDate.of(2025, 1, 1);
        LocalTime time = LocalTime.of(10, 0);

        when(employeeScheduleService.scheduleIdForAppointment(employeeId, date, time)).thenReturn(scheduleId);

        mockMvc.perform(get("/schedule/scheduleForAppointment")
                        .param("employeeId", employeeId.toString())
                        .param("date", date.toString())
                        .param("time", time.toString()))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$", CoreMatchers.is(scheduleId)));
    }

    @Test
    public void testGetSchedulesByDate() throws Exception{
        LocalDate date = LocalDate.of(2025, 1, 1);
        LocalTime startTime = LocalTime.of(9, 0);
        LocalTime endTime = LocalTime.of(13, 0);
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
        EmployeeSchedule employeeSchedule1 = EmployeeSchedule.builder()
                                                            .employee(employee)
                                                            .date(date)
                                                            .scheduleStartTime(startTime)
                                                            .scheduleEndTime(endTime)
                                                            .build();
        List<EmployeeSchedule> schedulesByDate = Arrays.asList(employeeSchedule1);
        when(employeeScheduleService.getEmployeeScheduleByDate(date)).thenReturn(schedulesByDate);

        mockMvc.perform(get("/schedule/by-date")
                        .param("date", date.toString()))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.size()", CoreMatchers.is(schedulesByDate.size())))
                        .andExpect(jsonPath("$[0].date", CoreMatchers.is(date.toString())));
    }

    @Test
    public void testSchedulesByEmployeeId() throws Exception{
        LocalDate date = LocalDate.of(2025, 1, 1);
        LocalTime startTime = LocalTime.of(9, 0);
        LocalTime endTime = LocalTime.of(13, 0);
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
                                                            .employee(employee)
                                                            .date(date)
                                                            .scheduleStartTime(startTime)
                                                            .scheduleEndTime(endTime)
                                                            .build();
        List<EmployeeSchedule> schedulesByEmployeeId = Arrays.asList(employeeSchedule);
        when(employeeScheduleService.getEmployeeScheduleByEmployeeId(employeeId)).thenReturn(schedulesByEmployeeId);

        mockMvc.perform(get("/schedule/employee/" + employeeId))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.size()", CoreMatchers.is(schedulesByEmployeeId.size())))
                        .andExpect(jsonPath("$[0].employee.id", CoreMatchers.is(employeeId)));

    }

    @Test
    public void testGetSchedulesByDates() throws Exception{
        LocalDate date = LocalDate.of(2025, 1, 1);
        LocalDate startFilterDate = date.minusDays(1);
        LocalDate endFilterDate = date.plusDays(1);
        LocalTime startTime = LocalTime.of(9, 0);
        LocalTime endTime = LocalTime.of(13, 0);
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
        EmployeeSchedule employeeSchedule = EmployeeSchedule.builder()
                                                            .employee(employee)
                                                            .date(date)
                                                            .scheduleStartTime(startTime)
                                                            .scheduleEndTime(endTime)
                                                            .build();
        List<EmployeeSchedule> schedulesByDates = Arrays.asList(employeeSchedule);
        when(employeeScheduleService.getEmployeeScheduleByDates(startFilterDate, endFilterDate)).thenReturn(schedulesByDates);

        mockMvc.perform(get("/schedule/by-dates")
                        .param("startDate", startFilterDate.toString())
                        .param("endDate", endFilterDate.toString()))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.size()", CoreMatchers.is(schedulesByDates.size())));
    }

    @Test
    public void testDeleteEmployeeSchedule() throws Exception{
        Integer scheduleId = 1;

        doNothing().when(employeeScheduleService).deleteEmployeeSchedule(scheduleId);

        mockMvc.perform(delete("/schedule/" + scheduleId))
                        .andExpect(status().isOk());
    }

}
