import React from "react";
import { withRouter } from "react-router";
import Cookies from 'js-cookie';
import './contactForm.scss';

export default class ContactFormComponent extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            topicToSend: '',
            textToSend: '',
            email: '',
            trueOrFalse: false
        }
    }

    handleSubmit = (e) => {
        console.log('siema')
        console.log(this.state.email)
        console.log(this.state.textToSend)
        console.log(this.state.trueOrFalse)
        if ((this.state.topicToSend == '' && this.state.textToSend == '') || (this.state.email == '') || (this.state.trueOrFalse == false)) {
            //hehe
            console.log('siema2')
        } else {
            console.log('siema3')
            if (this.state.email != undefined) {
                const data = {
                    topic: this.state.topicToSend,
                    messageContent: this.state.textToSend,
                    email: this.state.email
                };
                console.log('towysylam ludzie')
                console.log(data)
                console.log(JSON.stringify(data))
                fetch(`http://localhost:8080/api/chat/client/send`, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                        'accessToken': Cookies.get('accessToken')
                    },
                    body: JSON.stringify(data),
                })
                    .then(response => response.json())
                    .then(data => {
                        //console.log(JSON.stringify(data))
                        console.log('Success:', JSON.stringify(data));
                    })
                    .catch((error) => {
                        console.error('Error', JSON.stringify(error));
                    });
                this.setState({ topicToSend: '' });
                this.setState({ textToSend: '' });
            }
        }
    }

    handleChangeTopicValue = (e) => {
        this.setState({ topicToSend: e.target.value });
    }

    handleChangeTextValue = (e) => {
        this.setState({ textToSend: e.target.value });
    }

    handleChangeEmailValue = (e) => {
        this.setState({ email: e.target.value });
    }

    handleChangeTrue = (e) => {
        this.setState({ trueOrFalse: e.target.value });
    }

    render() {
        return (
            <div className="contactForm">
                <h1 className="formHeader">Contact with us</h1>
                <form>
                    <div class="form-group">
                        <label for="exampleInputEmail1">Email address</label>
                        <input type="email" value={this.state.email} class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp" placeholder="Enter email" onChange={this.handleChangeEmailValue} />
                        <small id="emailHelp" class="form-text text-muted">We'll never share your email with anyone else.</small>
                    </div>
                    <div class="form-group">
                        <label for="exampleInputPassword1">Topic</label>
                        <input type="Topic" value={this.state.topicToSend} class="form-control" placeholder="Topic" onChange={this.handleChangeTopicValue} />
                    </div>
                    <div class="form-group ">
                        <label for="exampleInputPassword1">Message</label>
                        <textarea class="form-control" value={this.state.textToSend} id="exampleFormControlTextarea1" rows="3" onChange={this.handleChangeTextValue}></textarea>
                    </div>
                    <div class="form-check">
                        <input type="checkbox" class="form-check-input" id="exampleCheck1" onChange={this.handleChangeTrue}/>
                        <label class="form-check-label" for="exampleCheck1">I confirm that I have read the regulations</label>
                    </div>
                </form>
                <button type="submit" class="btn btn-primary" onClick={this.handleSubmit}>Submit</button>
            </div>)
    }
}

export const ContactForm = withRouter(ContactFormComponent)