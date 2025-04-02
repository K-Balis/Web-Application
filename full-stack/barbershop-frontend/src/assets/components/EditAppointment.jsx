import React, { useEffect, useState } from "react";
import Header from "./Header";
import NavigationBar from "./NavigationBar";
import Footer from "./Footer";
import { useNavigate, useParams } from "react-router-dom";
import { getAppointmentById, updateAppointment } from "../services/AppointmentService";
import { getAvailableEmployees } from "../services/EmployeeService";
import '../styles/EditAppointment.css'

function EditAppointment(){

    const [employee, setEmployee] = useState('');
    const [customer, setCustomer] = useState('');
    const [employeeSchedule, setEmployeeSchedule] = useState('');
    const [date, setDate] = useState('');
    const [startTime, setStartTime] = useState('');
    const [endTime, setEndTime] = useState('');

    const [employeeId, setEmployeeId] = useState('');
    const [availableEmployees, setAvailableEmployees] = useState([]);

    const navigate = useNavigate();
    const {id} = useParams();

    useEffect(() => {
        document.title ="Edit Appointment";
    }, []);

    useEffect(() => {
        if(id){
            getAppointmentById(id).then((response) => {
                setEmployee(response.data.employee);
                setCustomer(response.data.customer);
                setEmployeeSchedule(response.data.employeeSchedule);
                setDate(response.data.date);
                setStartTime(response.data.appointmentStart);
                setEndTime(response.data.appointmentEnd);
            })
        }
    }, [id]);

    useEffect(() => {
        if(date && startTime){
            getAvailableEmployees(date, startTime).then((response) => {
                console.log(response.data);
                setAvailableEmployees(response.data);
            });
        }
        else {
            setAvailableEmployees([]);
        }
    }, [date, startTime]);

    const handleSubmitEditedAppointment = (e) => {
        e.preventDefault();
        const appointment = {   employeeId, 
                                customerId : customer?.id, 
                                scheduleId : employeeSchedule?.id, 
                                date, 
                                startTime, 
                                endTime};

        updateAppointment(id, appointment).then((response) => navigate("/appointments"));
    }

    return(
        <div>

            <Header/>

            <NavigationBar/>

            <div className="edit-appointment-container">
                <div className="card">
                    <h2 className="edit-appointment-card-title">Edit Appointment</h2>
                    <div className="edit-appointment-card-body">
                        <form onSubmit={handleSubmitEditedAppointment}>
                            <div className="edit-appointment-form">
                                <label>Customer</label>
                                <select disabled>
                                    <option>
                                        {customer.firstname} {customer.lastname}
                                    </option>
                                </select>
                            </div>
                            <div className="edit-appointment-form">
                                <label>Select Date</label>
                                <input type="date"
                                        name="date"
                                        value={date}
                                        onChange={(e) => setDate(e.target.value)}
                                        required
                                />
                            </div>
                            <div className="edit-appointment-form">
                                <label>Select Time</label>
                                <input type="time"
                                        name="startTime"
                                        value={startTime}
                                        onChange={(e) => setStartTime(e.target.value)}
                                        required
                                />
                            </div>
                            <div className="edit-appointment-form">
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

                            <button type="submit" className="save-edited-appointment-button">Update Appointment</button>

                        </form>
                    </div>
                </div>
            </div>

            <Footer/>

        </div>
    );

}

export default EditAppointment