import React, {useEffect, useState} from "react";
import Header from "./Header";
import NavigationBar from "./NavigationBar";
import Footer from "./Footer";
import { useNavigate } from "react-router-dom";
import { getAllEmployees } from "../services/EmployeeService";
import { deleteEmployeeSchedule, getAllEmployeeSchedules, getScheduleByDate, getSchedulesByDates, getSchedulesByEmployeeId } from "../services/EmployeeScheduleService";
import '../styles/EmployeeSchedule.css'

function EmployeeSchedule(){

    const [employees, setEmployees] = useState([])
    const [selectedOption, setSelectedOption] = useState("")
    const [specificDate, setSpecificDate] = useState('');
    const [dateRangeStart, setDateRangeStart] = useState('');
    const [dateRangeEnd, setDateRangeEnd] = useState('');
    const [employeeId, setEmployeeId] = useState('');
    const [scheduleFilterResult, setScheduleFilterResult] = useState([])
    
    const navigate = useNavigate();

    useEffect(() => {
        document.title = "Schedule";
    }, []);

    useEffect(() => {
        getAllEmployees().then((response) => setEmployees(response.data));
    }, []);

    useEffect(() => {
        setSpecificDate('');
        setDateRangeStart('');
        setDateRangeEnd('');
        setEmployeeId('');
    }, [selectedOption]);

    const handleSelectOption = (e) => {
        setSelectedOption(e.target.value);
    };
    
    const handleSpecificDateChange = (e) => {
        setSpecificDate(e.target.value);
    };

    const handleDateRangeStartChange = (e) => {
        setDateRangeStart(e.target.value);
    };

    const handleDateRangeEndChange = (e) => {
        setDateRangeEnd(e.target.value);
    };

    const handleEmployeeIdChange = (e) => {
        setEmployeeId(e.target.value);
    };

    const filterSchedule =() => {
        if (selectedOption === "All") {
            getAllEmployeeSchedules().then((response) => setScheduleFilterResult(response.data));
        }
        else if (selectedOption === "Date" && specificDate) {
            getScheduleByDate(specificDate).then((response) => setScheduleFilterResult(response.data));
        }
        else if (selectedOption === "Date Range" && dateRangeStart && dateRangeEnd){
            getSchedulesByDates(dateRangeStart, dateRangeEnd).then((response) => setScheduleFilterResult(response.data));
        }
        else if (selectedOption === "Employee" && employeeId){
            getSchedulesByEmployeeId(employeeId).then((response) => setScheduleFilterResult(response.data));
        }
    }

    useEffect(() => {
        filterSchedule();
    }, [selectedOption, specificDate, dateRangeStart, dateRangeEnd, employeeId]);

    const handleDeleteSchedule = (scheduleId) => {
        deleteEmployeeSchedule(scheduleId).then(() => {
            filterSchedule();
        });
    }

    return (

        <div>

            <Header/>

            <NavigationBar/>

            <div className="schedule-container">

                <h1 className="schedule-header">Schedule</h1>
                <hr className="schedule-hor-line"/>

                <div>
                    <select className="schedule-select-option" onChange={handleSelectOption} defaultValue="">
                        <option value="" disabled>Filter Schedule</option>
                        <option>All</option>
                        <option>Date</option>
                        <option>Date Range</option>
                        <option>Employee</option>
                    </select>

                    {selectedOption === "Date" &&
                        <input 
                            className="date-select-option"
                            type="date" 
                            name="specificDate" 
                            value={specificDate} 
                            onChange={handleSpecificDateChange} 
                        />
                    }
                    {selectedOption === "Date Range" &&
                        <>
                            <input
                                className="dates-start-select-option"
                                type="date" 
                                name="dateRangeStart" 
                                value={dateRangeStart} 
                                onChange={handleDateRangeStartChange}
                            />
                            <input
                                className="dates-end-select-option"
                                type="date" 
                                name="dateRangeEnd" 
                                value={dateRangeEnd} 
                                onChange={handleDateRangeEndChange}
                            />
                        </>
                    }
                    {selectedOption === "Employee" &&
                        <select
                            className="employee-select-option"
                            name="employeeId"
                            value={employeeId}
                            onChange={handleEmployeeIdChange}
                            defaultValue=""
                        >
                            <option value="" disabled>Select Employee</option>
                            {employees.map(employee => (
                                <option key={employee.id} value={employee.id}>
                                    {employee.firstname} {employee.lastname}, {employee.username}
                                </option>
                            ))}
                        </select>
                    }
                    <button className="schedule-add-button" onClick={() => navigate("/add-schedule")}>Add to Schedule</button>
                </div>

                {selectedOption != "" &&
                    <table className="schedule-table">
                        <thead>
                            <tr>
                                <th className="schedule-table-head">Employee</th>
                                <th className="schedule-table-head">Date</th>
                                <th className="schedule-table-head">Start Time</th>
                                <th className="schedule-table-head">End Time</th>
                                <th className="schedule-table-head">Manage Schedule</th>
                            </tr>
                        </thead>
                        <tbody>
                            {scheduleFilterResult.map(employeeSchedule => (
                                <tr key={employeeSchedule.id}>
                                    <td className="schedule-table-data">{employeeSchedule.employee.firstname} {employeeSchedule.employee.lastname}</td>
                                    <td className="schedule-table-data">{new Date(employeeSchedule.date).toLocaleDateString("en-GB")}</td>
                                    <td className="schedule-table-data">{employeeSchedule.scheduleStartTime.substring(0, 5)}</td>
                                    <td className="schedule-table-data">{employeeSchedule.scheduleEndTime.substring(0, 5)}</td>
                                    <td className="schedule-table-data">
                                        <button className="schedule-delete-button" onClick={() => handleDeleteSchedule(employeeSchedule.id)}>Delete Schedule</button>
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

export default EmployeeSchedule