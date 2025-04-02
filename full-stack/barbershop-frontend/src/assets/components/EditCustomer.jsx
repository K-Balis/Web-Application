import React, { useState, useEffect } from "react";
import Header from "./Header";
import NavigationBar from "./NavigationBar";
import Footer from "./Footer";
import {updateCustomer, getCustomerById} from "../services/CustomerService";
import { useNavigate, useParams } from "react-router-dom";
import '../styles/EditCustomer.css'

function EditCustomer(){

    const [firstname, setFirstName] = useState('');
    const [lastname, setLastName] = useState('');
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [email, setEmail] = useState('');
    const [phone, setPhone] = useState('');
    const [role, setRole] = useState('');

    const [usernameErrorMessage, setUsernameErrorMessage] = useState('');
    const [emailErrorMessage, setEmailErrorMessage] = useState('');
    const [phoneErrorMessage, setPhoneErrorMessage] = useState('');
    
    const navigate = useNavigate();
    const {id} = useParams();

    useEffect(() => {
        document.title = "Edit Customer";
    }, []);

    useEffect(() => {
        if(id){
            getCustomerById(id).then((response) => {
                setFirstName(response.data.firstname);
                setLastName(response.data.lastname);
                setUsername(response.data.username);
                setPassword(response.data.password);
                setEmail(response.data.email);
                setPhone(response.data.phone);
                setRole(response.data.role);
            })
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

    const handleEmailChange = (e) =>{
        setEmail(e.target.value);
    }

    const handlePhoneChange = (e) => {
        setPhone(e.target.value);
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

    const handleSaveCustomer = (e) => {
        e.preventDefault();
        setUsernameErrorMessage('');
        setEmailErrorMessage('');
        setPhoneErrorMessage('');
        const customer = {firstname, lastname, username, password, email, phone, role};
        updateCustomer(id , customer).then(() => navigate("/dashboard")).catch((err) => {handleErrorMessage(err)});
    }

    return(
        <div>
            <Header/>

            <NavigationBar/>

            <div className="edit-customer-container">
                <div className="card">
                    <h2 className="edit-card-title">Update Customer</h2>
                    <div className="edit-employe-card-body">
                        <form onSubmit={handleSaveCustomer}>

                            <div className="edit-customer-form">
                                <label>First Name</label>
                                <input type="text" name="firstname" value={firstname} onChange={handleFirstnameChange} placeholder="Enter First Name" required/>
                            </div>

                            <div className="edit-customer-form">
                                <label>Last Name</label>
                                <input type="text" name="lastname" value={lastname} onChange={handleLastnameChange} placeholder="Enter Last Name" required/>
                            </div>

                            <div className="edit-customer-form">
                                <label>Username</label>
                                <input type="text" name="username" value={username} onChange={handleUsernameChange} placeholder="Enter Username" required/>
                                {usernameErrorMessage && <span className="edit-customer-error-message">{usernameErrorMessage}</span>}
                            </div>

                            <div className="edit-customer-form">
                                <label>E-mail</label>
                                <input type="text" name="email" value={email} onChange={handleEmailChange} placeholder="Enter E-mail" required/>
                                {emailErrorMessage && <span className="edit-customer-error-message">{emailErrorMessage}</span>}
                            </div>

                            <div className="edit-customer-form">
                                <label>Phone</label>
                                <input type="text" name="phone" value={phone} onChange={handlePhoneChange} placeholder="Enter Phone" required/>
                                {phoneErrorMessage && <span className="edit-customer-error-message">{phoneErrorMessage}</span>}
                            </div>

                            <button type="submit" className="save-customer-button">Update Customer</button>

                        </form>

                    </div>

                </div>

            </div>

            <Footer/>

        </div>
    )
}

export default EditCustomer