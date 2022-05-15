import React from "react";
import Cookies from "js-cookie";
import "./emailGroupContactAdd"
import EmailGroupContactAddComponent from "./emailGroupContactAdd";
import GroupContactsComponent from "./groupContacts";

export default class EmailGroupModifyWrapperComponent extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            contacts:[],
            readyToShow: false,
            keyRender: 1
        }
        console.log(this.props.recordId);
    }

    addRecord = (e) => {
        let emails = [];
        emails.push(e);
        const data = {
            emailGroupId: this.props.recordId,
            contactsEmails: emails
        };
        console.log(JSON.stringify(data));
        fetch('http://localhost:8080/api/EmailGroup/add-contact', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'accessToken': Cookies.get('accessToken')
            },
            body: JSON.stringify(data),
        })
        .then(response => response.json())
        .then(data => {
            console.log("nie błąd");
            console.log('Success:', JSON.stringify(data));
        })
        .catch((error) => {
            console.log("błond");
            this.setState({keyRender: this.state.keyRender});
            console.error('Error', JSON.stringify(error));
        });
    }

    render() {
        return (
        <div className="main">
            {<GroupContactsComponent groupId={this.props.recordId} keyRender={this.state.keyRender}/>}
            <EmailGroupContactAddComponent addRecord={this.addRecord}/>
        </div>
        )
    }

}
