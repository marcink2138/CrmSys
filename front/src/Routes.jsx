import React, { Component } from "react";
import { Router, Switch, Route } from "react-router-dom";
import ContactFormComponent from "./contactFormComponent/contactForm";
import LoginComponent from './loginComponent/login';
import MainPageComponent from "./mainPageComponent/mainPage";
import AppLauncherComponent from "./appLauncherComponent/appLauncher";
export default class Routes extends Component {
    render() {
        return (
            <Switch>
                <Route path="/" exact component={LoginComponent} />
                <Route path="/landing" exact component={MainPageComponent}/>
                <Route path="/landing?type=appLauncher" exact component={() => <MainPageComponent type={'appLauncher'}/>}/>
                <Route path="/landing?type=datatableLauncher" exact component={MainPageComponent}/>
                <Route path="/landing?type=chatter" exact component={MainPageComponent}/>
                <Route path="/landing?type=contacts" exact component={MainPageComponent}/>
                <Route path="/landing?type=products" exact component={MainPageComponent}/>
                <Route path="/landing?type=groups" exact component={MainPageComponent}/>
                <Route path="/landing?type=groupMail" exact component={MainPageComponent}/>
                <Route path="/contactForm" exact component={ContactFormComponent}/>
            </Switch>
        )
    }
}