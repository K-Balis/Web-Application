import axios from "axios"

const REST_API_BASE_URL = 'http://localhost:8080/schedule';

export const saveEmployeeSchedule = (saveEmployeeSchedule) => axios.post(REST_API_BASE_URL, saveEmployeeSchedule);

export const getAllEmployeeSchedules = () => axios.get(REST_API_BASE_URL);

export const getScheduleByDate = (date) => axios.get(`${REST_API_BASE_URL}/by-date?date=${date}`);

export const getSchedulesByEmployeeId = (employeeId) => axios.get(`${REST_API_BASE_URL}/employee/${employeeId}`);

export const getSchedulesByDates = (startDate, endDate) => axios.get(`${REST_API_BASE_URL}/by-dates?startDate=${startDate}&endDate=${endDate}`);

export const deleteEmployeeSchedule = (scheduleId) => axios.delete(`${REST_API_BASE_URL}/${scheduleId}`);

export const getScheduleIdForAppointment = (employeeId, date, time) => axios.get(`${REST_API_BASE_URL}/scheduleForAppointment?employeeId=${employeeId}&date=${date}&time=${time}`);