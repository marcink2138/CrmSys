import React, { useState } from "react";
import 'bootstrap/dist/css/bootstrap.min.css'
import { Container, Row, Col, Button, Stack, Modal, Form, Card } from 'react-bootstrap';
import Cookies from 'js-cookie';
import './groupDatatable.scss';

export class ModalGroup extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            showModal: false,
            record: this.props.record,
            fields: this.props.fields,
            buttonText: this.props.buttonText,
            method: this.props.method,
            tableName: this.props.tableName,
            listOfFieldsToSend: {},
            inputValue: ""
        }
    }

    componentDidMount() {
    }

    componentDidUpdate() {
    }

    handleShow = (e) => {
        this.setState({ showModal: true });
    }

    handleCloseSend = (e) => {
        this.handleSendingTo()
        //this.setState({ showModal: false });
    }

    handleSendingTo() {
        const line = this.state.inputValue
        const data1 = {
            emailGroupName: line
        };
        const data = [];
        data.push(data1);
        console.log(JSON.stringify(data));
        fetch(`http://localhost:8080/api/EmailGroup/create`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'accessToken': Cookies.get('accessToken')
            },
            body: JSON.stringify(data1),
        })
        .then(response => response.json())
        .then(data => {
            //console.log(JSON.stringify(data))
            this.props.addModalCallback();
            this.setState({showModal: false});
            console.log('Success:', JSON.stringify(data));
        })
        .catch((error) => {
            console.error('Error', JSON.stringify(error));
        });
    }

    handleClose = (e) => {
        this.setState({ showModal: false })
    }

    changeInputValue = (e) => {
        this.setState({ inputValue: e.target.value});
    }

    render() {
        const { showModal } = this.state;
        const { buttonText } = this.state;
        const { fields } = this.state;
        const { listOfFieldsToSend } = this.state;
        const { tableName } = this.state;
        return (
            <Container>
                <Button onClick={this.handleShow}>
                    {buttonText}
                </Button>
                <Modal show={showModal} size="lg" aria-labelledby="contained-modal-title-vcenter"
                    centered onHide={this.handleClose}>
                    <Modal.Header closeButton>
                        <Modal.Title>{tableName}</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                    <div class="form-group">
                        <h2 className="modalLabel">Add Group</h2>
                        <label for="Group Name">Group Name</label>
                        <input type="text" class="form-control" placeholder="Group Name" onChange={this.changeInputValue}/>
                    </div>
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
        )
    }
}