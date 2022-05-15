import React from "react";
import { withRouter } from "react-router";
import NavbarComponent from "../navbar/navbar";
import ActionBarComponent from "../actionBarComponent/actionBar";
import { AppLauncher } from "../appLauncherComponent/appLauncher";
import { useLocation } from 'react-router';
import {jsCookie } from "js-cookie";
import { Chat } from '../chatPage/chat';
import {Datatable} from '../datatableComponent/datatable';
import "./mainPage.scss"
import { Hello } from "../helloComponent/hello";
import { GroupDatatable } from "../groupDatatableComponent/groupDatatable";
import { GroupMail } from "../groupMailComponent/groupMail";
import { Transition } from "react-transition-group";

export default class MainPageComponent extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            openedApps: [],
            appLauncherFilter: '',
            inProp: false,
            duration: 300
        }
        const type = "";
        this.getType();
        const transitionStyles = {
            entering: { opacity: 1 },
            entered:  { opacity: 1 },
            exiting:  { opacity: 0 },
            exited:  { opacity: 0 },
        };
    }

    getType = () => {
        this.type = this.props.location.search.match(/type=(.*)/);
        this.type = this.type?.[1];
    }

    returnToMenu = () => {
        this.props.history.push('/landing');
        this.type = undefined;
    }

    addApp = (e) => {
        this.props.history.push(e.link);
        this.type = e.type;
        const apps = this.state.openedApps;
        let isAppOpen = false;
        for (let app of this.state.openedApps) {
            if (app.type == e.type) {
                isAppOpen = true;
            }
        }
        if (!isAppOpen){
            apps.push(e);
            this.setState({openedApps:apps});
        };
    }

    getRouteLink = (routeLink) => {
        this.type=routeLink.type;
        this.props.history.push(routeLink.link);
        this.forceUpdate();
    }

    closeCard = (e) => {
        let apps = this.state.openedApps;
        apps = apps.filter((app) => {
            if (e == app.type) {
                return false;
            }
            return true;
        });
        this.setState({openedApps:apps});
        this.props.history.push('/landing?type=appLauncher');
        this.type = 'appLauncher';
    }

    changeToAppLauncher = (e) => {
        this.setState({appLauncherFilter: e.filter});
        this.props.history.push(e.link);
        this.type = e.type;
        console.log(this.state.appLauncherFilter);
    } 

    render() {
        return (
        <div className="main">
            <NavbarComponent props={this.props} componentCallback={this.returnToMenu}/>  
            <ActionBarComponent openedApps = {this.state.openedApps} getRouteLink={this.getRouteLink} closeCard={this.closeCard} filters={this.changeToAppLauncher}/>
            <div className="appLauncher">
                {this.type === undefined && <Hello/>}
                {this.type === 'chatter' && <Chat/>}
                {this.type === 'groupMail' && <GroupMail/>}
                {this.type === 'appLauncher' && <AppLauncher addApp={this.addApp} filter={this.state.appLauncherFilter}/>}
                {this.type === 'datatableLauncher' && <Datatable/>}
                {this.type === 'contacts' && <Datatable type='contact'/>}
                {this.type === 'products' && <Datatable type='product'/>}
                {this.type === 'assets' && <Datatable type='asset'/>}
                {this.type === 'leads' && <Datatable type='lead'/>}
                {this.type === 'groups' && <GroupDatatable type='EmailGroup'/>}
            </div>
        </div>
        )
    }

}

export const MainPage = withRouter(MainPageComponent)