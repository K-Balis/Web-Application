import {BrowserRouter, Route, Routes} from 'react-router-dom'
import Home from './assets/components/Home';
import About from './assets/components/About.jsx';
import Dashboard from './assets/components/Dashboard.jsx';
import AddEmployee from './assets/components/AddEmployee.jsx';
import AddCustomer from './assets/components/AddCustomer.jsx';
import EditEmployee from './assets/components/EditEmployee.jsx';
import EditCustomer from './assets/components/EditCustomer.jsx';
import EmployeeSchedule from './assets/components/EmployeeSchedule.jsx';
import AddEmployeeSchedule from './assets/components/AddEmployeeSchedule.jsx';
import Appointments from './assets/components/Appointment.jsx';
import AddAppointment from './assets/components/AddAppointment.jsx';
import EditAppointment from './assets/components/EditAppointment.jsx';

function App() {

  return (
    <>
      <BrowserRouter>
        <Routes>
          <Route path='/' element = { <Home username="User" />  }></Route>
          <Route path='/about' element = { <About />  }></Route>
          <Route path='/dashboard' element = { <Dashboard />  }></Route>
          <Route path='/add-employee' element = { <AddEmployee />  }></Route>
          <Route path='/add-customer' element = { <AddCustomer />  }></Route>
          <Route path='/edit-employee/:id' element = { <EditEmployee />  }></Route>
          <Route path='/edit-customer/:id' element = { <EditCustomer />  }></Route>
          <Route path='/schedule' element = { <EmployeeSchedule />  }></Route>
          <Route path='/add-schedule' element = { <AddEmployeeSchedule />  }></Route>
          <Route path='/appointments' element = { <Appointments />  }></Route>
          <Route path='/add-appointment' element = { <AddAppointment/> }></Route>
          <Route path='/edit-appointment/:id' element = { <EditAppointment/> }></Route>
        </Routes>
      </BrowserRouter>
    </>
  )
}

export default App
