import React, { useState } from "react";
import 'bootstrap/dist/css/bootstrap.min.css'
import { Container, Row, Col, Button, Stack, Modal, Form, Card } from 'react-bootstrap';
import Cookies from 'js-cookie';
import './groupDatatable.scss';
import EmailGroupContactAddComponent from "../emailGroupManagmentComponent/emailGroupContactAdd";
import EmailGroupModifyWrapperComponent from "../emailGroupManagmentComponent/emailGroupModifyWrapper";

export class ModifyGroupModal extends React.Component {

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

    handleClose = (e) => {
        console.log(this.props.record);
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
            <Container className="buttonCell">
                <Button onClick={this.handleShow}>
                    {buttonText}
                </Button>
                <Modal show={showModal} size="lg" aria-labelledby="contained-modal-title-vcenter"
                    centered onHide={this.handleClose}>
                    <Modal.Header closeButton>
                        <Modal.Title>{tableName}</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <EmailGroupModifyWrapperComponent recordId={this.props.record['id']}/>
                    </Modal.Body>
                    <Modal.Footer>
                        <Button variant="secondary" onClick={this.handleClose}>
                            Cancel
                        </Button>
                    </Modal.Footer>
                </Modal>
            </Container>
        )
    }
}