import React from "react";
import { withRouter } from "react-router";
import Cookies from 'js-cookie';
import './hello.scss';

export default class HelloComponent extends React.Component {

    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div className="helloWrapper">
                <h1 className="helloMessage">Hello admin@admin.pl</h1>
            </div>)
    }
}

export const Hello = withRouter(HelloComponent)