import axios from "axios";

const REST_API_BASE_URL= 'http://localhost:8080/appointments';

export const createAppointment = (appointment) => axios.post(REST_API_BASE_URL, appointment);

export const updateAppointment = (appointmentId, appointment) => axios.put(`${REST_API_BASE_URL}/${appointmentId}`, appointment);

export const getAllAppointments = () => axios.get(REST_API_BASE_URL);

export const deleteAppointment = (appointmentId) => axios.delete(`${REST_API_BASE_URL}/${appointmentId}`);

export const getAppointmentById = (appointmentId) => axios.get(`${REST_API_BASE_URL}/${appointmentId}`);

export const getAppointmentsByDate = (date) => axios.get(`${REST_API_BASE_URL}/by-date?date=${date}`);

export const getAppointmentsByDates = (startDate, endDate) => axios.get(`${REST_API_BASE_URL}/by-dates?startDate=${startDate}&endDate=${endDate}`);

export const getAppointmentsByEmployeeId = (employeeId) => axios.get(`${REST_API_BASE_URL}/employee/${employeeId}`);

export const getAppointmentsByCustomerId = (customerId) => axios.get(`${REST_API_BASE_URL}/customer/${customerId}`);