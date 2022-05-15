import React from "react";
import { withRouter } from "react-router";
import { useLocation } from 'react-router-dom';
import Cookies from 'js-cookie';
import './login.scss';

export default class LoginComponent extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            isFailed: false
        }
    }

    handleEmailChange = (e) => {
        this.setState({email: e.target.value});
    }
    
    handlePasswordChange = (e) => {
        this.setState({password: e.target.value});
    }

    handleLogin = (e) => {
        const data = {
            email: this.state.email,
            password: this.state.password
        };
        fetch('http://localhost:8080/api/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(data),
        })
        .then(response => response.json())
        .then(data => {
            console.log('Success:', JSON.stringify(data));
            if (data.hasOwnProperty('accessToken')) {
                Cookies.set('accessToken', data.accessToken);
                Cookies.set('refreshToken', data.refreshToken);
                this.props.history.push('/landing');
            } else {
                this.setState( {isFailed: true} );
            }
        })
        .catch((error) => {
            console.error('Error', JSON.stringify(error));
        });
    }

    render() {
        return (
            <div className="background">
            <div className="loginPane">
                <div className="col-md-4">
                    <center><h2>Login to system</h2></center>
                    <div className="form-group mt-3">
                        <label>Email address</label>
                        <input
                        type="email"
                        className="form-control"
                        id="EmailInput"
                        name="EmailInput"
                        aria-describedby="emailHelp"
                        placeholder="Enter email"
                        onChange={this.handleEmailChange}
                        />
                        <small id="emailHelp" className="text-danger form-text">
                        {}
                        </small>
                    </div>
                    <div className="form-group">
                        <label>Password</label>
                        <input
                        type="password"
                        className="form-control"
                        id="exampleInputPassword1"
                        placeholder="Password"
                        onChange={this.handlePasswordChange}
                        />
                        <small id="passworderror" className="text-danger form-text">
                        {}
                        </small>
                    </div>
                        <button type="submit" className="btn btn-primary mt-2" onClick={this.handleLogin}>
                            Submit
                        </button>
                </div>
                {this.state.isFailed === true && <div class="alert alert-danger wrongLogin" role="alert">Wrong login or password</div>}
            </div>
            </div>)
    }
}

export const Login = withRouter(LoginComponent)