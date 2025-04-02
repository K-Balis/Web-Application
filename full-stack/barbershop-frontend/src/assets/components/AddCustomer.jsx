import React, { useState, useEffect } from "react";
import Header from "./Header";
import NavigationBar from "./NavigationBar";
import Footer from "./Footer";
import {createCustomer} from "../services/CustomerService";
import { useNavigate } from "react-router-dom";
import '../styles/AddCustomer.css'

function AddCustomer(){

    const [firstname, setFirstName] = useState('');
    const [lastname, setLastName] = useState('');
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [email, setEmail] = useState('');
    const [phone, setPhone] = useState('');
    const [role, setRole] = useState('user');

    const [usernameErrorMessage, setUsernameErrorMessage] = useState('');
    const [emailErrorMessage, setEmailErrorMessage] = useState('');
    const [phoneErrorMessage, setPhoneErrorMessage] = useState('');

    const navigate = useNavigate();

    useEffect(() => {
        document.title = "Create Customer";
    }, []);
    
    const handleFirstnameChange = (e) =>{
        setFirstName(e.target.value);
    }

    const handleLastnameChange = (e) =>{
        setLastName(e.target.value);
    }

    const handleUsernameChange = (e) => {
        setUsername(e.target.value);
    }

    const handlePasswordChange = (e) => {
        setPassword(e.target.value);
    }

    const handleEmailChange = (e) =>{
        setEmail(e.target.value);
    }

    const handlePhoneChange = (e) => {
        setPhone(e.target.value);
    }

    const handleErrorMessage = (err) => {
        const errorMsg = err.response.data.message;

        if (errorMsg.includes("(username)") && errorMsg.includes("already exists")) {
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

    const handleSaveCustomer = (e) => {
        e.preventDefault();
        setUsernameErrorMessage('');
        setEmailErrorMessage('');
        setPhoneErrorMessage('');
        const customer = {firstname, lastname, username, password, email, phone, role};
        createCustomer(customer).then(() => navigate("/dashboard")).catch((err) => {handleErrorMessage(err)});
    }

    return(
        <div>
            <Header/>

            <NavigationBar/>

            <div className="add-customer-container">
                <div className="card">
                    <h2 className="add-card-title">Create a new Customer</h2>
                    <div className="add-employe-card-body">
                        <form onSubmit={handleSaveCustomer}>

                            <div className="add-customer-form">
                                <label>First Name</label>
                                <input type="text" name="firstname" value={firstname} onChange={handleFirstnameChange} placeholder="Enter First Name" required/>
                            </div>

                            <div className="add-customer-form">
                                <label>Last Name</label>
                                <input type="text" name="lastname" value={lastname} onChange={handleLastnameChange} placeholder="Enter Last Name" required/>
                            </div>

                            <div className="add-customer-form">
                                <label>Username</label>
                                <input type="text" name="username" value={username} onChange={handleUsernameChange} placeholder="Enter Username" required/>
                                {usernameErrorMessage && <span className="add-customer-error-message">{usernameErrorMessage}</span>}
                            </div>

                            <div className="add-customer-form">
                                <label>Password</label>
                                <input type="password" name="password" value={password} onChange={handlePasswordChange} placeholder="Enter Password" required/>
                            </div>

                            <div className="add-customer-form">
                                <label>E-mail</label>
                                <input type="text" name="email" value={email} onChange={handleEmailChange} placeholder="Enter E-mail" required/>
                                {emailErrorMessage && <span className="add-customer-error-message">{emailErrorMessage}</span>}
                            </div>

                            <div className="add-customer-form">
                                <label>Phone</label>
                                <input type="text" name="phone" value={phone} onChange={handlePhoneChange} placeholder="Enter Phone" required/>
                                {phoneErrorMessage && <span className="add-customer-error-message">{phoneErrorMessage}</span>}
                            </div>

                            <button type="submit" className="save-customer-button">Save Customer</button>

                        </form>

                    </div>

                </div>

            </div>

            <Footer/>

        </div>
    )
}

export default AddCustomer