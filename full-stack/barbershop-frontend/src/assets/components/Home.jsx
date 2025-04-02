import React, { useEffect } from 'react';
import Header from './Header';
import NavigationBar from './NavigationBar';
import Footer from './Footer';
import bg_barbershop from '../images/home_wallpaper.jpg'
import { useNavigate } from 'react-router-dom';
import '../styles/Home.css'

const Home = ({ username }) => {

  useEffect(()  => {
    document.title = "Home";
  }, []);

  const navigate = useNavigate();

  return (
    <div>

      <Header/>

      <NavigationBar/>

      <main className="home-main">
        <img src={bg_barbershop} alt="Barbershop background" className="home-bg-image" />
        <div className="overlay">
          <h2 className="welcome-message">Welcome</h2>
          <button className="reservation-button" onClick={() => navigate("/add-appointment")}>BOOK AN APPOINTMENT</button>
        </div>
      </main>

      <Footer/>

    </div>
  );
};

export default Home;
