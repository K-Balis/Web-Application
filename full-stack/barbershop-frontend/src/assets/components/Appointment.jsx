import React, { useEffect, useState } from "react";
import Header from "./Header";
import NavigationBar from "./NavigationBar";
import Footer from "./Footer";
import { useNavigate } from "react-router-dom";
import { deleteAppointment, getAllAppointments, getAppointmentsByCustomerId, getAppointmentsByDate, getAppointmentsByDates, getAppointmentsByEmployeeId } from "../services/AppointmentService"
import { getAllEmployees } from "../services/EmployeeService";
import { getAllCustomers } from "../services/CustomerService";
import '../styles/Appointment.css'

function Appointments(){

    const [employees, setEmployees] = useState([]);
    const [customers, setCustomers] = useState([]);
    const [selectedOption, setSelectedOption] = useState('');
    const [specificDate, setSpecificDate] = useState('');
    const [dateRangeStart, setDateRangeStart] = useState('');
    const [dateRangeEnd, setDateRangeEnd] = useState('');
    const [employeeId, setEmployeeId]= useState('');
    const [customerId, setCustomerId] = useState('');
    const [filterResult, setFilterResult] = useState([]);

    const navigate = useNavigate();

    useEffect(() => {
        document.title = "Appointments";
    }, []);

    useEffect(() => {
        getAllEmployees().then((response) => setEmployees(response.data));
        getAllCustomers().then((response) => setCustomers(response.data));
    }, []);

    useEffect(() => {
        setSpecificDate('');
        setDateRangeStart('');
        setDateRangeEnd('');
        setEmployeeId('');
        setCustomerId('');
    } , [selectedOption]);

    const handleSelectOption = (e) => {
        setSelectedOption(e.target.value);
    }

    const handleSpecificDateChange = (e) => {
        setSpecificDate(e.target.value);
    }

    const handleDateRangeStartChange = (e) => {
        setDateRangeStart(e.target.value);
    }

    const handleDateRangeEndChange = (e) => {
        setDateRangeEnd(e.target.value);
    }

    const handleEmployeeIdChange = (e) => {
        setEmployeeId(e.target.value);
    }

    const handleCustomerIdChange = (e) => {
        setCustomerId(e.target.value);
    }

    const filterAppointments = () => {
        if(selectedOption === "All") {
            getAllAppointments().then((response) => setFilterResult(response.data));
        }
        else if (selectedOption === "Date" && specificDate) {
            getAppointmentsByDate(specificDate).then((response) => setFilterResult(response.data));
        }
        else if (selectedOption === "Date Range" && dateRangeStart && dateRangeEnd) {
            getAppointmentsByDates(dateRangeStart, dateRangeEnd).then((response) => setFilterResult(response.data));
        }
        else if (selectedOption === "Employee" && employeeId) {
            getAppointmentsByEmployeeId(employeeId).then((response) => setFilterResult(response.data));
        }
        else if (selectedOption === "Customer" && customerId) {
            getAppointmentsByCustomerId(customerId).then((response) => setFilterResult(response.data));
        }
    }

    useEffect(() => {
        filterAppointments();
    }, [selectedOption, specificDate, dateRangeStart, dateRangeEnd, employeeId, customerId]);

    const handleDeleteAppointment = (appointmentId) => {
        deleteAppointment(appointmentId).then(() => {
            filterAppointments();
        })
    }

    return(
        <div>

            <Header/>

            <NavigationBar/>

            <div className="appointment-container">

                <h1 className="appointment-header">Appointments</h1>
                <hr className="appointment-hor-line"/>

                <div>
                    <select className="appointment-select-option" onChange={handleSelectOption} defaultValue="">
                        <option value="" disabled>Filter Appointments</option>
                        <option>All</option>
                        <option>Date</option>
                        <option>Date Range</option>
                        <option>Employee</option>
                        <option>Customer</option>
                    </select>

                    {selectedOption === "Date" &&
                        <input
                            className="appointment-date-select-option"
                            type="date"
                            name="specificDate"
                            value={specificDate}
                            onChange={handleSpecificDateChange}    
                        />
                    }
                    {selectedOption === "Date Range" &&
                        <>
                            <input
                                className="appointment-dates-start-select-option"
                                type="date"
                                name="dateRangeStart"
                                value={dateRangeStart}
                                onChange={handleDateRangeStartChange}
                            />
                            <input
                                className="appointment-dates-end-select-option"
                                type="date"
                                name="dateRangeEnd"
                                value={dateRangeEnd}
                                onChange={handleDateRangeEndChange}
                            />
                        </>
                    }
                    {selectedOption === "Employee" &&
                        <select
                            className="appointment-employee-select-option"
                            name="employeeId"
                            value={employeeId}
                            onChange={handleEmployeeIdChange}
                        >
                            <option value="" disabled>Select Employee</option>
                            {employees.map(employee =>(
                                <option key={employee.id} value={employee.id}>
                                    {employee.firstname} {employee.lastname}, {employee.username}
                                </option>
                            ))}
                        </select>
                    }
                    {selectedOption === "Customer" &&
                        <select
                            className="appointment-customer-select-option"
                            name="customerId"
                            value={customerId}
                            onChange={handleCustomerIdChange}
                        >
                            <option value="" disabled>Select Customer</option>
                            {customers.map(customer =>(
                                <option key={customer.id} value={customer.id}>
                                    {customer.firstname} {customer.lastname}, {customer.username}
                                </option>
                            ))}
                        </select>
                    }
                    <button className="appointment-add-button" onClick={() => navigate("/add-appointment")}>Book an Appointment</button>
                </div>
                
                {selectedOption != "" &&
                    <table className="appointment-table">
                        <thead>
                            <tr>
                                <th className="appointment-table-head">Employee</th>
                                <th className="appointment-table-head">Customer</th>
                                <th className="appointment-table-head">Date</th>
                                <th className="appointment-table-head">Time</th>
                                <th className="appointment-table-head">Manage</th>
                            </tr>
                        </thead>
                        <tbody>
                            {filterResult.map(appointment => (
                                <tr key={appointment.id}>
                                    <td className="appointment-table-data">{appointment.employee.firstname} {appointment.employee.lastname}</td>
                                    <td className="appointment-table-data">{appointment.customer.firstname} {appointment.customer.lastname}</td>
                                    <td className="appointment-table-data">{new Date(appointment.date).toLocaleDateString("en-GB")}</td>
                                    <td className="appointment-table-data">{appointment.appointmentStart.substring(0, 5)}</td>
                                    <td className="appointment-table-data">
                                        <button className="appointment-edit-button" onClick={() => navigate(`/edit-appointment/${appointment.id}`)}>Reschedule</button>
                                        <button className="appointment-delete-button" onClick={() => handleDeleteAppointment(appointment.id)}>Delete</button>
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                }

            </div>

            <Footer/>

        </div>
    );
}

export default Appointments