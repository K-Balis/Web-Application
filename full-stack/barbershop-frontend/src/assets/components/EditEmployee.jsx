import React, { useEffect, useState } from "react";
import Header from "./Header";
import NavigationBar from "./NavigationBar";
import Footer from "./Footer";
import {getEmployeeById, updateEmployee} from "../services/EmployeeService";
import { useNavigate, useParams } from "react-router-dom";
import '../styles/EditEmployee.css'

function EditEmployee(){

    const [firstname, setFirstName] = useState('');
    const [lastname, setLastName] = useState('');
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [email, setEmail] = useState('');
    const [phone, setPhone] = useState('');
    const [role, setRole] = useState('');
    const [bio, setBio] = useState('');

    const [usernameErrorMessage, setUsernameErrorMessage] = useState('');
    const [emailErrorMessage, setEmailErrorMessage] = useState('');
    const [phoneErrorMessage, setPhoneErrorMessage] = useState('');

    const navigate = useNavigate();
    const {id} = useParams();

    useEffect(() => {
        document.title = "Edit Employee";
    }, []);

    useEffect(() => {
        if(id){
            getEmployeeById(id).then((response) => {
                                    setFirstName(response.data.firstname);
                                    setLastName(response.data.lastname);
                                    setUsername(response.data.username);
                                    setPassword(response.data.password);
                                    setEmail(response.data.email);
                                    setPhone(response.data.phone);
                                    setRole(response.data.role);
                                    setBio(response.data.bio);})
        }
    }, [id])
    
    const handleFirstnameChange = (e) =>{
        setFirstName(e.target.value);
    }

    const handleLastnameChange = (e) =>{
        setLastName(e.target.value);
    }

    const handleUsernameChange = (e) => {
        setUsername(e.target.value);
    }

    const handleRoleChange = (e) => {
        setRole(e.target.value);
    }

    const handleEmailChange = (e) =>{
        setEmail(e.target.value);
    }

    const handlePhoneChange = (e) => {
        setPhone(e.target.value);
    }

    const handleBioChange = (e) => {
        setBio(e.target.value);
    }

    const handleErrorMessage = (err) => {
        const errorMsg = err.response.data.message;

        if (errorMsg.includes("(username") && errorMsg.includes("already exists")) {
            setUsernameErrorMessage("*Username already exists!");
            console.log("username error");
        }
        else if (errorMsg.includes("(email)") && errorMsg.includes("already exists")) {
            setEmailErrorMessage("*Email already exists!");
            console.log("email error");
        }
        else if (errorMsg.includes("(phone)") && errorMsg.includes("already exists")) {
            setPhoneErrorMessage("*Phone already exists!");
            console.log("phone error");
        }
    }

    const handleSaveEmployee = (e) => {
        e.preventDefault();
        setUsernameErrorMessage('');
        setEmailErrorMessage('');
        setPhoneErrorMessage('');
        const employee = {firstname, lastname, username, password, email, phone, role, bio};
        updateEmployee(id, employee).then(() => navigate("/dashboard")).catch((err) => {handleErrorMessage(err)});
    }

    return(
        <div>
            <Header/>

            <NavigationBar/>

            <div className="edit-employee-container">
                <div className="card">
                    <h2 className="edit-card-title">Edit Employee</h2>
                    <div className="edit-employe-card-body">
                        <form onSubmit={handleSaveEmployee}>

                            <div className="edit-employee-form">
                                <label>First Name</label>
                                <input type="text" name="firstname" value={firstname} onChange={handleFirstnameChange} placeholder="Enter First Name" required/>
                            </div>

                            <div className="edit-employee-form">
                                <label>Last Name</label>
                                <input type="text" name="lastname" value={lastname} onChange={handleLastnameChange} placeholder="Enter Last Name" required/>
                            </div>

                            <div className="edit-employee-form">
                                <label>Username</label>
                                <input type="text" name="username" value={username} onChange={handleUsernameChange} placeholder="Enter Username" required/>
                                {usernameErrorMessage && <span className="edit-employee-error-message">{usernameErrorMessage}</span>}
                            </div>

                            <div className="edit-employee-form">
                                <label>Role</label>
                                <select name="role" value={role} onChange={handleRoleChange} required>
                                    <option value="" disabled>Select Employee Role</option>
                                    <option value="Owner">Owner</option>
                                    <option value="Employee">Employee</option>
                                </select>
                            </div>

                            <div className="edit-employee-form">
                                <label>E-mail</label>
                                <input type="text" name="email" value={email} onChange={handleEmailChange} placeholder="Enter E-mail" required/>
                                {emailErrorMessage && <span className="edit-employee-error-message">{emailErrorMessage}</span>}
                            </div>

                            <div className="edit-employee-form">
                                <label>Phone</label>
                                <input type="text" name="phone" value={phone} onChange={handlePhoneChange} placeholder="Enter Phone" required/>
                                {phoneErrorMessage && <span className="edit-employee-error-message">{phoneErrorMessage}</span>}
                            </div>

                            <div className="edit-employee-form">
                                <label>Bio</label>
                                <textarea name="bio" value={bio} onChange={handleBioChange} placeholder="Enter Bio" required/>
                            </div>

                            <button type="submit" className="save-employee-button">Update Employee</button>

                        </form>

                    </div>

                </div>

            </div>

            <Footer/>

        </div>
    )
}

export default EditEmployee