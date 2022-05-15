import React, { useState } from "react";
import "./style.scss";
import 'bootstrap/dist/css/bootstrap.min.css'
import { Container, Row, Col, Button, Stack, Modal, Form, Card } from 'react-bootstrap';


export class Inner extends React.Component {

    constructor(props) {
        super(props);
        this.parentCallbackInner = this.parentCallbackInner.bind(this);
        this.state = {
            name: this.props.name,
            type: this.props.type,
            size: this.props.size,
            value: this.props.value
        }
    }

    componentDidMount() {
        //console.log('landed 2');
    }

    componentDidUpdate() {
        //console.log(this.state.showModal)
    }

    handleChangeOfInput = (e) => {
        this.setState({value: e.target.value}, function () {
            this.parentCallbackInner(e, this.state.name, this.state.value);});

    }

    parentCallbackInner = (e, name2, value2) => {
        this.props.parentCallbackInner(name2, value2)
    }

    render() {
        const { name } = this.state;
        const { value } = this.state;
        return (
            <Col xs={6} sm={6} lg={3} xl={3} xxl={3}>
                <Card>
                    <Card.Body>
                        <Form>
                            <Form.Label>{name}</Form.Label>
                            <Form.Control value={value} onChange={this.handleChangeOfInput}></Form.Control>
                        </Form>
                    </Card.Body>
                </Card>
            </Col>
        )
    }
}