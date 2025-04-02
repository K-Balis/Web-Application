import React, { useEffect, useState } from "react";
import Header from "./Header";
import Footer from "./Footer";
import NavigationBar from "./NavigationBar";
import {getAllEmployees, deleteEmployee} from "../services/EmployeeService";
import {getAllCustomers, deleteCustomer} from "../services/CustomerService";
import { useNavigate } from "react-router-dom";
import '../styles/Dashboard.css'

function Dashboard(){

    const [selectedOption, setSelectedOption] = useState("")
    const [employees, setEmployees] = useState([])
    const [customers, setCustomers] = useState([])

    const navigate = useNavigate();

    useEffect(() => {
        document.title = "Dashboard";
    }, []);

    const handleSelectedOption = (e) => {
        setSelectedOption(e.target.value);
    }

    const getUserDataFromDb = () => {
        if (selectedOption === "Employees") {
            getAllEmployees().then((response) => setEmployees(response.data));
        }
        else if (selectedOption === "Customers"){
            getAllCustomers().then((response) => setCustomers(response.data));
        }
    }

    useEffect( () => {
        getUserDataFromDb();
    }, [selectedOption])

    const deleteUser = (userId) => {
        if (selectedOption === "Employees") {
            deleteEmployee(userId).then(() => {
                getUserDataFromDb();
            })
        }
        else if (selectedOption === "Customers") {
            deleteCustomer(userId).then(() => {
                getUserDataFromDb();
            });
        }
    }

    return(
        <div>

            <Header/>

            <NavigationBar/>

            <div className="dashboard-container">
                <h1 className="dashboard-header">Dashboard</h1>
                <hr className="dashboard-hor-line"/>
                <select className="select" onChange={handleSelectedOption} defaultValue="">
                    <option value="" disabled>Select Group</option>
                    <option>Employees</option>
                    <option>Customers</option>  
                </select>

                {selectedOption === "Employees" && <button className="dashboard-add-button" onClick={() => navigate(`/add-employee`)}>Add New Employee</button>}
                {selectedOption === "Customers" && <button className="dashboard-add-button" onClick={() => navigate(`/add-customer`)}>Add New Customer</button>}

                {selectedOption !== "" &&
                    <table className="dashboard-table">
                        <thead>
                            {selectedOption === "Employees" &&
                                <tr>
                                    <th className="table-head">NAME</th>
                                    <th className="table-head">USERNAME</th>
                                    <th className="table-head">ROLE</th>
                                    <th className="table-head">EMAIL</th>
                                    <th className="table-head">PHONE</th>
                                    <th className="table-head">MANAGE</th>
                                </tr>
                            }
                            {selectedOption === "Customers" &&
                                <tr>
                                    <th className="table-head">NAME</th>
                                    <th className="table-head">USERNAME</th>
                                    <th className="table-head">EMAIL</th>
                                    <th className="table-head">PHONE</th>
                                    <th className="table-head">MANAGE</th>
                                </tr>
                            }
                        </thead>
                        <tbody>
                            {selectedOption === "Employees" && 
                                employees.map(employee => (
                                    <tr key={employee.id}>
                                        <td className="table-data">{employee.firstname} {employee.lastname}</td>
                                        <td className="table-data">{employee.username}</td>
                                        <td className="table-data">{employee.role}</td>
                                        <td className="table-data">{employee.email}</td>
                                        <td className="table-data">{employee.phone}</td>
                                        <td className="table-data">
                                            <button className="edit-table-button" onClick={() => navigate(`/edit-employee/${employee.id}`)}>Edit</button>
                                            <button className="delete-table-button" onClick={() => deleteUser(employee.id)}>Remove</button>
                                        </td>
                                    </tr>
                                ))
                            }
                            {selectedOption === "Customers" &&
                                customers.map(customer => (
                                    <tr key={customer.id}>
                                        <td className="table-data">{customer.firstname} {customer.lastname}</td>
                                        <td className="table-data">{customer.username}</td>
                                        <td className="table-data">{customer.email}</td>
                                        <td className="table-data">{customer.phone}</td>
                                        <td className="table-data">
                                            <button className="edit-table-button" onClick={() => navigate(`/edit-customer/${customer.id}`)}>Edit</button>
                                            <button className="delete-table-button" onClick={() => deleteUser(customer.id)}>Remove</button>
                                        </td>
                                    </tr>
                                ))
                            }
                        </tbody>
                    </table>
                }
            </div>

            <Footer/>

        </div>
    )
}

export default Dashboard