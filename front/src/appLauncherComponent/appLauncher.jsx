import React from "react";
import AppCardComponent from "../appCardComponent/appCard";
import { withRouter } from "react-router";
import "./appLauncher.scss"
import contactLogo from './contactBook.jpg';
import messageLogo from './messageIcon.png';
import assetLogo from './assetIcon.jpg';
import groupLogo from './groupIcon.webp';
import energyLogo from './energyIcon.webp';
import productLogo from './productIcon.png';
import leadLogo from './leadIcon.jpg';
export default class AppLauncherComponent extends React.Component {

    
    
    
    constructor(props) {
        super(props);
        this.state = {
            appList: [
                {
                    "appName":"Contacts",
                    "link":"/landing?type=contacts",
                    "type":"contacts",
                    "icon": contactLogo
                },
                {
                    "appName":"Chatter",
                    "link":"/landing?type=chatter",
                    "type":"chatter",
                    "icon": messageLogo
                },
                {
                  "appName":"Assets",
                  "link":"/landing?type=assets",
                  "type":"assets",
                  "icon": assetLogo
                },
                {
                    "appName":"Contact Groups",
                    "link":"/landing?type=groups",
                    "type":"groups",
                    "icon": groupLogo
                },
                {
                    "appName":"Newsletter Groups",
                    "link":"/landing?type=groupMail",
                    "type":"groupMail",
                    "icon": energyLogo
                },
                {
                    "appName":"Products",
                    "link":"/landing?type=products",
                    "type":"products",
                    "icon": productLogo
                },
                {
                    "appName":"Leads",
                    "link":"/landing?type=leads",
                    "type":"leads",
                    "icon": leadLogo
                }
            ],
            filteredList: []
        }
        this.fullListReset(this.props.filter);
    }

    fullListReset = (e) => {
        let records = [];
        this.state.appList.map((app) => {
            if (app.appName.search(new RegExp(e, "i")) != -1) {
                records.push(app);
            }
        });
        this.setState({filteredList: records});
    }

    handleCardClick = (e) => {
        this.props.addApp(e);
    }

    componentDidMount() {
        this.fullListReset(this.props.filter);
    }

    componentWillReceiveProps(nextProps) {
        this.fullListReset(nextProps.filter);
    }

    render() {
        const appList = this.state.filteredList;
        return (
            <div className="appContainer">
                {appList.map( (app) => (
                    <div className="appCard">
                        <AppCardComponent
                            type={app.type}
                            link={app.link}
                            appName={app.appName}
                            onCardClick={this.handleCardClick}
                            icon={app.icon}
                        />
                    </div>
                ))}
            </div>)
    }

}

export const AppLauncher = withRouter(AppLauncherComponent)