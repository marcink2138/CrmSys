import React from 'react';
import { BrowserRouter as Router } from 'react-router-dom';
import '../node_modules/bootstrap/dist/css/bootstrap.min.css';
import Routes from './Routes';

class App extends React.Component {
    render() { return( 
        <Router>
            <Routes/>  
        </Router>
      )
    }
}

export default App;