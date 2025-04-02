import React, { useEffect, useState } from "react";
import Header from "./Header";
import NavigationBar from "./NavigationBar";
import Footer from "./Footer";
import { useNavigate } from "react-router-dom";
import { saveEmployeeSchedule } from "../services/EmployeeScheduleService";
import { getAllEmployees } from "../services/EmployeeService";
import '../styles/AddEmployeeSchedule.css'

function AddEmployeeSchedule(){

    const [employees, setEmployees] = useState([]);
    const [employeeId, setEmployeeId] = useState("");
    const [date, setDate] = useState("");
    const [startTime, setStartTime] = useState("");
    const [endTime, setEndTime] = useState("");
    
    const [timeErrorMessage, setTimeErrorMessage] = useState('');

    const navigate = useNavigate();

    useEffect(() => {
        document.title = "Add to Schedule";
    }, []);

    useEffect(() => {
        getAllEmployees().then((response) => setEmployees(response.data))
    }, []);

    const handleNewEmployeeSchedule = (e) => {
        e.preventDefault();

        if(startTime >= endTime) {
            setTimeErrorMessage("*Schedule's start cannot be before later than its end!");
            return;
        }

        const newSchedule = {
            employeeId,
            date,
            startTime,
            endTime
        };

        saveEmployeeSchedule(newSchedule).then((response) => navigate("/schedule"));
    }

    return(
        <div>

            <Header/>

            <NavigationBar/>

            <div className="add-employee-schedule-container">
                <div className="card">
                    <h2 className="add-schedule-card-title">Add to Schedule</h2>
                    <div className="add-schedule-card-body">
                        <form onSubmit={handleNewEmployeeSchedule}>
                            <div className="add-schedule-form">
                                <label>Select Employee</label>
                                <select name="employeeId" 
                                        value={employeeId} 
                                        onChange={(e) => setEmployeeId(e.target.value)}
                                        defaultValue=""
                                        required>
                                    <option value="" disabled>Select Employee</option>
                                    {employees.map((employee) => (
                                        <option key={employee.id} value={employee.id}>
                                            {employee.firstname} {employee.lastname}, {employee.phone}
                                        </option>
                                    ))}
                                </select>
                            </div>
                            <div className="add-schedule-form">
                                <label>Select Date</label>
                                <input type="date"
                                        name="date"
                                        value={date}
                                        onChange={(e) => setDate(e.target.value)}
                                        required
                                />
                            </div>
                            <div className="add-schedule-form">
                                <label>Select Start Time</label>
                                <input type="time"
                                        name="startTime"
                                        value={startTime}
                                        onChange={(e) => setStartTime(e.target.value)}
                                        required
                                />
                                {timeErrorMessage && <span className="time-error-message">{timeErrorMessage}</span>}
                            </div>
                            <div className="add-schedule-form">
                                <label>Select End Time</label>
                                <input type="time"
                                        name="endTime"
                                        value={endTime}
                                        onChange={(e) => setEndTime(e.target.value)}
                                        required>
                                </input>
                            </div>

                            <button type="submit" className="save-new-schedule-button">Save Employee Schedule</button>

                        </form>
                    </div>
                </div>
            </div>

            <Footer/>

        </div>
    );
}

export default AddEmployeeSchedule