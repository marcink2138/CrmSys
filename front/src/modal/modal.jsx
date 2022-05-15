import React, { useState } from "react";
import "./style.scss";
import 'bootstrap/dist/css/bootstrap.min.css'
import { Container, Row, Col, Button, Stack, Modal, Form, Card } from 'react-bootstrap';
import { Inner } from "./inner";
import Cookies from 'js-cookie';


export class ModalMy extends React.Component {

    constructor(props) {
        super(props);
        this.parentCallbackDatatable = this.parentCallbackDatatable.bind(this);
        this.parentCallbackInner = this.parentCallbackInner.bind(this);
        this.state = {
            showModal: false,
            record: this.props.record,
            fields: this.props.fields,
            buttonText: this.props.buttonText,
            method: this.props.method,
            tableName: this.props.tableName,
            tableNameCaption: this.capitalizeFirstLetter(this.props.tableName),
            listOfFieldsToSend: {},
            renderErrorLog: false
        }
        // console.log('record:')
        // console.log(this.state.record)
        // console.log('fields:')
        // console.log(this.state.fields)
    }

    componentDidMount() {
        //console.log('landed');
        this.createListOfFields()
        // console.log(this.state.listOfFieldsToSend)
        // console.log(this.state.record)
    }

    componentDidUpdate() {
        //console.log(this.state.showModal)
    }

    parentCallbackDatatable = () => {
        console.log('wywoluje')
        this.setState({ showModal: false })
        this.props.parentCallbackDatatable()
    }

    createListOfFields() {
        for (var column of this.state.fields) {
            // console.log('xdddddd:')
            // console.log(column)
            // console.log(this.getValue(column.columnName))
            this.state.listOfFieldsToSend[column.columnName] = this.getValue(column.columnName)
            //console.log(this.state.listOfFieldsToSend)
        }
    }

    handleShow = (e) => {
        if (this.state.method == 'delete') {
            this.handleDelete()
        } else {
            this.setState({ showModal: true });
        }
    }

    handleCloseSend = (e) => {
        this.handleSendingTo()
        //this.setState({ showModal: false });
    }

    handleDelete() {
        if (this.state.method == 'delete') {
            //console.log(this.state.listOfFieldsToSend)
            var line = this.state.record.id
            const data1 = [
                line
            ];
            console.log('towysylam')
            console.log(data1)
            console.log(JSON.stringify(data1))
            fetch(`http://localhost:8080/api/${this.state.tableName}/delete`, {
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
                    console.log('Success:', JSON.stringify(data));
                    var errorFinderData = JSON.stringify(data);
                    //console.log(errorFinderData.search('Error'))
                    if (errorFinderData.search('Error') != -1) {
                        this.setState({ renderErrorLog: true })
                    } else {
                        this.setState({ renderErrorLog: false })
                        this.parentCallbackDatatable();
                    }
                })
                .catch((error) => {
                    console.error('Error', JSON.stringify(error));
                    this.setState({ renderErrorLog: true })
                });
        }
    }

    handleSendingTo() {
        //tutaj wysylamy w zaleznosci od metody
        if (this.state.method == 'modify') {
            //console.log(this.state.listOfFieldsToSend)
            const data1 = {
                id: this.state.record.id,
                columnsValuesMap: this.state.listOfFieldsToSend
            };
            const data = [];
            data.push(data1)
            console.log('towysylam')
            console.log(data)
            console.log(JSON.stringify(data))
            fetch(`http://localhost:8080/api/${this.state.tableName}/update`, {
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
                    var errorFinderData = JSON.stringify(data);
                    //console.log(errorFinderData.search('Error'))
                    if (errorFinderData.search('Error') != -1) {
                        this.setState({ renderErrorLog: true })
                    } else {
                        this.setState({ renderErrorLog: false })
                        this.parentCallbackDatatable();
                    }
                })
                .catch((error) => {
                    console.error('Error', JSON.stringify(error));
                    this.setState({ renderErrorLog: true })
                });
        }
        if (this.state.method == 'create') {
            const line = this.state.listOfFieldsToSend
            const data1 = {
                line
            };
            const data = [];
            data.push(data1)
            console.log('towysylam')
            console.log(data)
            console.log(JSON.stringify(data))
            fetch(`http://localhost:8080/api/${this.state.tableName}/create`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'accessToken': Cookies.get('accessToken')
                },
                body: JSON.stringify(line),
            })
                .then(response => response.json())
                .then(data => {
                    //console.log(JSON.stringify(data))
                    console.log('Success:', JSON.stringify(data));
                    var errorFinderData = JSON.stringify(data);
                    //console.log(errorFinderData.search('Error'))
                    if (errorFinderData.search('Error') != -1) {
                        console.log("disssssssssssssssssssssssssss")
                        this.setState({ renderErrorLog: true })
                    } else {
                        console.log("disssssssssssssssssssssssssss2")
                        this.setState({ renderErrorLog: false })
                        this.parentCallbackDatatable();
                    }
                })
                .catch((error) => {
                    console.error('Error', JSON.stringify(error));
                    console.log("disssssssssssssssssssssssssss3")
                    this.setState({ renderErrorLog: true })
                    //this.parentCallbackDatatable();
                });
        }
    }

    handleClose = (e) => {
        this.createListOfFields()
        this.setState({ renderErrorLog: false })
        this.setState({ showModal: false })
    }

    capitalizeFirstLetter(string) {
        return string.charAt(0).toUpperCase() + string.slice(1);
    }

    parentCallbackInner = (childDataName, childDataValue) => {
        //tutaj dostajemu name i value konkretnego pola
        //console.log(childDataName, childDataValue)
        this.state.listOfFieldsToSend[childDataName] = childDataValue
        //console.log(this.state.listOfFieldsToSend)
        //console.log(this.state.record)
    }

    getValue(nameOfRecord) {
        //console.log('tutaj')
        //console.log(nameOfRecord)
        var valueReturned = this.state.record[nameOfRecord]
        return valueReturned
    }

    render() {
        const { showModal } = this.state;
        const { buttonText } = this.state;
        const { fields } = this.state;
        const { listOfFieldsToSend } = this.state;
        const { tableNameCaption } = this.state;
        const { renderErrorLog } = this.state;

        let messageError;
        if (renderErrorLog) {
            messageError =
                <Container className="text-danger"> Error check all fields </Container>
        } else {
            <Container></Container>
        }
        return (
            <div className="buttonCell">
                <Button onClick={this.handleShow}>
                    {buttonText}
                </Button>
                <Modal show={showModal} size="lg" aria-labelledby="contained-modal-title-vcenter"
                    centered onHide={this.handleClose}>
                    <Modal.Header closeButton>
                        <Col style={{ textAlign: "center" }}>
                            <Modal.Title >{tableNameCaption}</Modal.Title>
                        </Col>
                    </Modal.Header>
                    <Modal.Body>
                        <Row>
                            {
                                fields.map((field, index) => (
                                    <Inner
                                        parentCallbackInner={this.parentCallbackInner}
                                        key={index}
                                        name={field.columnName}
                                        size={field.columnSize}
                                        type={field.columnType}
                                        value={this.getValue(field.columnName)}
                                    />
                                ))
                            }
                        </Row>
                    </Modal.Body>
                    <Modal.Footer>
                        <Container>
                            <Row>
                                <Col xs={9} sm={9} lg={9} xl={9} xxl={9} style={{ textAlign: "center" }}>
                                    {messageError}
                                </Col>
                                <Col xs={3} sm={3} lg={3} xl={3} xxl={3}>
                                    <Button variant="secondary" onClick={this.handleClose} style={{ marginLeft: '10px' }}>
                                        Close
                                    </Button>
                                    <Button variant="primary" onClick={this.handleCloseSend} style={{ marginLeft: '10px' }}>
                                        Send
                                    </Button>
                                </Col>
                            </Row>
                        </Container>
                    </Modal.Footer>
                </Modal>
            </div>
        )
    }
}