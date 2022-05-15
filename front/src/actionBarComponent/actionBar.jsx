import React from "react";
import './actionBar.scss';
import appsLogo from './rocket.webp';
import { withRouter } from "react-router";

export default class ActionBarComponent extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            openedApps: this.props.openedApps,
            isOpened: 0
        }
        this.handleAppsClick = this.handleAppsClick.bind(this);
    }

    componentWillReceiveProps(nextProps) {
        this.setState({ openedApps: nextProps.openedApps });  
    }

    handleAppsClick() {
        this.setState({isOpened: this.state.isOpened + 1});
        let routeLink = {type:'appLauncher', link:'/landing?type=appLauncher'}
        this.props.getRouteLink(routeLink);
        this.forceUpdate();
    }

    openCard = (e) => {
        let routeObject = {type:e.target.value, link:"/landing?type=" + e.target.value};
        this.props.getRouteLink(routeObject);
    }

    closeCard = (e) => {
        this.props.closeCard(e.target.value);
    }

    searchApp = (e) => {
        let x = {type:'appLauncher', link:"/landing?type=appLauncher", filter:e.target.value};
        this.props.filters(x);
    }

    render() {
        const {openedApps} = this.state;
        return (
            <div className="actionBar">
                <button type="button" class="btn btn-outline-primary actionBarButton" onClick={this.handleAppsClick}><img src={appsLogo} width={45} height={45}/>Apps</button>
                <div class="actionBarInput">
                    <input type="text" class="form-control" placeholder="Search for app" aria-label="Search for app" onChange={this.searchApp}></input>
                </div>
                <div className="pillMenu" >
                    {openedApps.map((app) => (
                        <button className="btn btn-primary btn-sm actionbarPill" onClick={this.openCard} value={app.type}>
                            {app.appName} 
                            <button type="button" className="btn-close buttonClose" aria-label="Close" onClick={this.closeCard} value={app.type}></button>
                        </button>
                    ))}
                </div>
            </div>
            )
    }

}

export const ActionBar = withRouter(ActionBarComponent)