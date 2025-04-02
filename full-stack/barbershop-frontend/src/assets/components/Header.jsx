import React from 'react';
import { useNavigate } from 'react-router-dom';
import '../styles/Header.css'

function Header(){

    const navigate = useNavigate();
    
    return(
        <div>
            <header className="header">
                <h1 className="header-title">
                    <button className='header-link' onClick={() => navigate("/")}>Barbershop App</button>
                </h1>
            </header>
        </div>
    )
}

export default Header