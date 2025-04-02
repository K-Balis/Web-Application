import React from "react";
import '../styles/Footer.css'

function Footer(){

    return(
        <div>
            <footer className="footer">
                &copy; {new Date().getFullYear()} Barbershop Application.
            </footer>
        </div>
    )
}

export default Footer