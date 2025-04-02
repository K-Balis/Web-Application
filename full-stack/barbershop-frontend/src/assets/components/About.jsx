import React, {useState , useEffect} from "react";
import Header from "./Header";
import Footer from "./Footer";
import NavigationBar from "./NavigationBar";
import { getAllEmployees } from "../services/EmployeeService";
import iconimage from "../images/image.png" 
import '../styles/About.css'

function About(){

    const [employees, setEmployees] = useState([])

    useEffect(() => {
        document.title = "About";
    }, []);

    useEffect(() => {
        getAllEmployees().then((response) => {
            setEmployees(response.data);
        }).catch(error => {
            console.error(error)
        })
    }, [])

    return(
        <div>

            <Header/>

            <NavigationBar/>

            <div className="about-page">
                <h1 className="about-team-header">Our Team</h1>
                <hr className="about-team-hor-line"/>
                <div className="about-team-container">
                    {employees.map((employees) => (
                        <div key={employees.id} className="person-card">
                            <div className="employee-image">
                                <img src={iconimage} alt="Employee"/>
                            </div>
                            <div className="employee-info">
                                <h2 className="employee-name">{employees.firstname + " " + employees.lastname}</h2>
                                <hr className="employee-hor-line"/>
                                <p className="employee-role">{employees.role.charAt(0).toUpperCase() + employees.role.slice(1)}</p>
                                <p className="employee-contact">
                                    Contact Information: {employees.email}, {employees.phone}
                                </p>
                                <p className="employee-bio">{employees.bio}</p>
                            </div>
                        </div>
                    ))}

                </div>
            </div>

            <Footer/>

        </div>
    )
}

export default About