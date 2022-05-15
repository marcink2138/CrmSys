import React from "react";
import Cookies from "js-cookie";
import "./emailGroupContactAdd"

export default class GroupContactsComponent extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            contactList: [],
            readyToShow: false
        }
        this.getContacts();
    }

    getContacts = (e) => {
        fetch(`http://localhost:8080/api/contact/get-by-email-group/${this.props.groupId}`, {
            method: 'GET',
            headers: {
                'accessToken': Cookies.get('accessToken')
            }
        })
        .then(response => response.json())
        .then(data => {
            this.setState({contactList: data});
            console.log(JSON.stringify(data));
            this.setState({readyToShow: true});
        })
        .catch((error) => {
            console.error('Error', JSON.stringify(error));
        });
        console.log("jeszcze raz!!!");
        console.log(this.state.contactList);
    }

    removeContactFrom = (e) => {
        let contactsEmails = [];
        contactsEmails.push(e.target.value);
        const data = {
            emailGroupId: this.props.groupId,
            contactsEmails: contactsEmails
        };
        fetch('http://localhost:8080/api/EmailGroup/delete-contact', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'accessToken': Cookies.get('accessToken')
            },
            body: JSON.stringify(data),
        })
        .then(response => response.json())
        .then(data => {
            console.log('Success:', JSON.stringify(data));
            this.getContacts();
        })
        .catch((error) => {
            console.error('Error', JSON.stringify(error));
            this.getContacts();
        });
    }

    componentWillReceiveProps(nextProps) {
        this.getContacts();
    }

    render() {
        return (
        <div className="Container">
            <div className="contactsWrapper">
                {this.state.readyToShow && this.state.contactList.map((app) => (
                    <div className="contactPill">
                        {app['name'] + ' ' + app['surname']} 
                        <button type="button" class="btn-close buttonClose" value={app['emailAddress']} onClick={this.removeContactFrom}></button>
                    </div>
                ))}
            </div>
        </div>
        )
    }

}
