import React, { useEffect, useState } from "react";
import Header from "./Header";
import NavigationBar from "./NavigationBar";
import Footer from "./Footer";
import { useNavigate } from "react-router-dom";
import { getAllCustomers } from "../services/CustomerService"
import { getAllEmployees, getAvailableEmployees } from "../services/EmployeeService";
import { createAppointment } from "../services/AppointmentService";
import { getScheduleIdForAppointment } from "../services/EmployeeScheduleService";
import '../styles/AddAppointment.css'

function AddAppointment(){

    const [customers, setCustomers] = useState([]);
    const [customerId, setCustomerId] = useState("");
    const [availableEmployees, setAvailableEmployees] = useState([]);
    const [employeeId, setEmployeeId] = useState("");
    const [date, setDate] = useState("");
    const [startTime, setStartTime] = useState("");
    const [scheduleId, setScheduleId] = useState("something");
    
    const navigate = useNavigate();

    useEffect(() => {
            document.title = "Book an Appointment";
        }, []);

    useEffect(() => {
        getAllCustomers().then((response) => setCustomers(response.data));
    }, []);

    useEffect(() => {
        if(date && startTime){
            getAvailableEmployees(date, startTime).then((response) => setAvailableEmployees(response.data));
        }
        else{
            setAvailableEmployees([]);
        }
    }, [date, startTime]);

    useEffect(() => {
        if (employeeId && date && startTime) {
            getScheduleIdForAppointment(employeeId, date, startTime).then((response) => setScheduleId(response.data));
            console.log(scheduleId);
        }
        else {
            console.log("empty");
        }
    }, [employeeId, date, startTime]);

    const handleNewAppointment = (e) => {
        e.preventDefault();

        const newAppointment = {
            employeeId,
            customerId,
            scheduleId,
            date,
            startTime
        };
        
        createAppointment(newAppointment).then((response) => navigate("/appointments"));
    }

    return(
        <div>

            <Header/>

            <NavigationBar/>

            <div className="add-appointment-container">
                <div className="card">
                    <h2 className="add-appointment-card-title">Book an Appointment</h2>
                    <div className="add-appointment-card-body">
                        <form onSubmit={handleNewAppointment}>
                            <div className="add-appointment-form">
                                <label>Select Customer</label>
                                <select name="customerId" 
                                        value={customerId} 
                                        onChange={(e) => setCustomerId(e.target.value)}
                                        required
                                >
                                    <option value="" disabled>Select Customer</option>
                                    {customers.map((customer) => (
                                        <option key={customer.id} value={customer.id}>
                                            {customer.firstname} {customer.lastname}, {customer.phone}
                                        </option>
                                    ))}
                                </select>
                            </div>
                            <div className="add-appointment-form">
                                <label>Select Date</label>
                                <input type="date"
                                        name="date"
                                        value={date}
                                        onChange={(e) => setDate(e.target.value)}
                                        required
                                />
                            </div>
                            <div className="add-appointment-form">
                                <label>Select Time</label>
                                <input type="time"
                                        name="startTime"
                                        value={startTime}
                                        onChange={(e) => setStartTime(e.target.value)}
                                        required
                                />
                            </div>
                            <div className="add-appointment-form">
                                <label>Select Employee</label>
                                <select name="employeeId" 
                                        value={employeeId} 
                                        onChange={(e) => setEmployeeId(e.target.value)}
                                        required
                                >
                                    <option value="" disabled>Select Employee</option>
                                    {availableEmployees.map((employee) => (
                                        <option key={employee.id} value={employee.id}>
                                            {employee.firstname} {employee.lastname}, {employee.phone}
                                        </option>
                                    ))}
                                </select>
                            </div>

                            <button type="submit" className="save-new-appointment-button">Save Appointment</button>

                        </form>
                    </div>
                </div>
            </div>

            <Footer/>

        </div>
    );
}

export default AddAppointment