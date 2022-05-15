import React from "react";
import './navbar.scss';
import { withRouter } from "react-router";

export default class NavbarComponent extends React.Component {

    constructor(props) {
        super(props);
    }

    redirectToMenu = (e) => {
        this.props.componentCallback();
    }

    render() {
        return (
            <nav className="navbar navbar-expand-lg navbar-dark bg-light">
                <div className="container">
                    <a class="navbar-brand" onClick={this.redirectToMenu}>Home</a>
                </div>
            </nav>
            )
    }

}

export const Navbar = withRouter(NavbarComponent);