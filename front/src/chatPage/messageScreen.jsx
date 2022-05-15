import React, { useState } from "react";
import "./style.scss";
import 'bootstrap/dist/css/bootstrap.min.css'
import { Container, Row, Col, Button, Stack, Modal, Form } from 'react-bootstrap';
import MyMessage from "./myMessagge";
import Cookies from 'js-cookie';


export default class MessageScreen extends React.Component {

    constructor(props) {
        super(props);
        this.reRenderFromParent = this.reRenderFromParent.bind(this);
        this.state = {
            showModal: false,
            messageList: [],
            shouldWeRender: false,
            topicToSend: '',
            textToSend: '',
            email: this.props.email,
            emailGroupName: this.props.emailGroupName,
            id: this.props.id,
            firstTime: true,
            whoWeSee: this.makeName(this.props.name, this.props.surname)
        }

    }

    componentDidMount() {
        this.getRecords();
        //console.log('screen')
        // if (this.state.whoWeSee == 'Michał Karcz') {
        //     this.setState({ messageList: messages })
        // } else {
        //     this.setState({ messageList: messages2 })
        // }
        // this.setState({ shouldWeRender: true });
        // console.log(this.state.messageList)
        // console.log(this.state.email)
        // this.getRecords();
    }

    getRecords = (e) => {
        //console.log(this.state.shouldWeRender)
        if (this.state.email != undefined) {
            fetch(`http://localhost:8080/api/chat/messages/${this.state.email}`, {
                method: 'GET',
                headers: {
                    'accessToken': Cookies.get('accessToken')
                }
            })
                .then(response => response.json())
                .then(data => {
                    //console.log('data')
                    //console.log(data)
                    this.setState({ messageList: data }, function () {
                        this.setState({ shouldWeRender: true })
                        //console.log('messagess list');
                        //console.log(this.state.messageList)
                    });
                })
                .catch((error) => {
                    console.error('Error', JSON.stringify(error));
                });
        } else {
            console.log('groupmessags')
            fetch(`http://localhost:8080/api/chat/email-group-messages/${this.state.id}`, {
                method: 'GET',
                headers: {
                    'accessToken': Cookies.get('accessToken')
                }
            })
                .then(response => response.json())
                .then(data => {
                    console.log('data')
                    console.log(data)
                    this.setState({ messageList: data }, function () {
                        this.setState({ shouldWeRender: true })
                        //console.log('messagess list');
                        //console.log(this.state.messageList)
                    });
                })
                .catch((error) => {
                    console.error('Error', JSON.stringify(error));
                });
                if(this.state.firstTime)
                {
                    this.setState({ firstTime: false}, function () {
                        this.setState({ firstTime: false })
                        //console.log('messagess list');
                        //console.log(this.state.messageList)
                    });
                }
        }
    }

    makeName(name, surname) {
        var fullName = name + ' ' + surname;
        return fullName;
    }

    handleShow = (e) => {
        this.setState({ showModal: true });
    }

    handleCloseSend = (e) => {
        if (this.state.topicToSend == '' && this.state.textToSend == '') {
            //hehe
        } else {
            if (this.state.email != undefined) {
                this.setState({ showModal: false })
                const data = {
                    topic: this.state.topicToSend,
                    messageContent: this.state.textToSend,
                    email: this.state.email
                };
                //console.log('towysylam ludzie')
                //console.log(data)
                console.log(JSON.stringify(data))
                fetch(`http://localhost:8080/api/chat/user/send`, {
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
                this.reRenderFromParent();
            }
            if (this.state.emailGroupName != undefined) {
                this.setState({ showModal: false })
                const data = {
                    emailGroupId: this.state.id,
                    topic: this.state.topicToSend,
                    messageContent: this.state.textToSend
                };
                //console.log('towysylam groupa')
                //console.log(data)
                console.log(JSON.stringify(data))
                fetch(`http://localhost:8080/api/chat/user/send/group`, {
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
                this.reRenderFromParent();
            }
        }
    }

    handleClose = (e) => {
        this.setState({ topicToSend: '' });
        this.setState({ textToSend: '' });
        this.setState({ showModal: false })
    }

    handleChangeTopicValue = (e) => {
        this.setState({ topicToSend: e.target.value });
    }

    handleChangeTextValue = (e) => {
        this.setState({ textToSend: e.target.value });
    }

    reRenderFromParent = () => {
        this.props.parentCallbackMessageScreen()
    }

    render() {
        const { shouldWeRender } = this.state;
        const { messageList } = this.state;
        const { showModal } = this.state;
        return (
            //<div className="message-screen">
            <Col>
                <Row>
                    <Container fluid>
                        {shouldWeRender
                            && messageList.map((message, index) => (
                                <MyMessage
                                    key={index}
                                    topic={message.topic}
                                    text={message.messageContent}
                                    who={message.type}
                                />
                            ))
                        }
                    </Container>
                </Row>
                <Row>
                    <Col xs={0} sm={4} lg={5} xl={5} xxl={5}></Col>
                    <Col xs={12} sm={4} lg={2} xl={2} xxl={2}>
                        <Container style={{ padding: '1rem', margin: '10px' }}>
                            <Button variant="light" onClick={this.handleShow}>
                                New Message
                            </Button>

                            <Modal show={showModal} size="lg" aria-labelledby="contained-modal-title-vcenter"
                                centered onHide={this.handleClose}>
                                <Modal.Header closeButton>
                                    <Modal.Title>New Message</Modal.Title>
                                </Modal.Header>
                                <Modal.Body>
                                    <Form>
                                        <Row>
                                            <Form.Label>Topic: </Form.Label>
                                        </Row>
                                        <Row>
                                            <input type={"text"} value={this.state.topicToSend} onChange={this.handleChangeTopicValue} />
                                        </Row>
                                        <Row>
                                            <Form.Label>Text: </Form.Label>
                                        </Row>
                                        <Row>
                                            <textarea value={this.state.textToSend} onChange={this.handleChangeTextValue} />
                                        </Row>
                                    </Form>
                                </Modal.Body>
                                <Modal.Footer>
                                    <Button variant="secondary" onClick={this.handleClose}>
                                        Close
                                    </Button>
                                    <Button variant="primary" onClick={this.handleCloseSend}>
                                        Send
                                    </Button>
                                </Modal.Footer>
                            </Modal>
                        </Container>
                    </Col>
                    <Col xs={0} sm={4} lg={5} xl={5} xxl={5}></Col>
                </Row>
            </Col>
            //</div>
        )
    }
}