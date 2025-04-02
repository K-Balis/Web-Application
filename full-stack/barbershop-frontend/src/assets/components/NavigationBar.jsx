import React from "react";
import { useNavigate } from "react-router-dom";
import '../styles/NavigationBar.css'

function NavigationBar(){

    const navigate = useNavigate();
    
    return(
        <div>
            <nav className="home-navbar">
                <ul className="navbar-list">
                    <li>
                        <button className="navbar-links" onClick={() => navigate("/")}>HOME</button>
                    </li>
                    <li>
                        <button className="navbar-links" onClick={() => navigate("/about")}>ABOUT</button>
                    </li>
                    <li>
                        <button className="navbar-links" onClick={() => navigate("/dashboard")}>DASHBOARD</button>
                    </li>
                    <li>
                        <button className="navbar-links" onClick={() => navigate("/schedule")}>SCHEDULE</button>
                    </li>
                    <li>
                        <button className="navbar-links" onClick={()=> navigate("/appointments")}>APPOINTMENTS</button>
                    </li>
                </ul>
            </nav>
        </div>
    )
}

export default NavigationBar