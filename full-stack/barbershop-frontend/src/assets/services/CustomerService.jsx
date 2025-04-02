import axios from 'axios';

const REST_API_BASE_URL = 'http://localhost:8080/customer';

export const createCustomer = (customer) => axios.post(REST_API_BASE_URL, customer);

export const getCustomerById = (customerId) => axios.get(`${REST_API_BASE_URL}/${customerId}`);

export const getAllCustomers = () => axios.get(REST_API_BASE_URL);

export const updateCustomer = (customerId, customer) => axios.put(`${REST_API_BASE_URL}/${customerId}`, customer);

export const deleteCustomer = (customerId) => axios.delete(`${REST_API_BASE_URL}/${customerId}`); 