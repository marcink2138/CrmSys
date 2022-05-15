import React from "react";
import { withRouter } from "react-router";
import './cardContent.scss';

export default class AppCardComponent extends React.Component {

    constructor(props) {
        super(props);
    }

    handleCardPick = () => {
        let appInfo = {link:this.props.link, appName:this.props.appName, type: this.props.type};
        this.props.onCardClick(appInfo);
    }


    render() {
        return (
            <div>
               <div className="card">
                    <div className="cardContent" onClick={this.handleCardPick}>
                        <h5 class="card-title">{this.props.appName}</h5>
                        <img src={this.props.icon} height={"150px"} width={"auto"}/>
                    </div>
                </div> 
            </div>
            )
    }

}

export const AppCard = withRouter(AppCardComponent)