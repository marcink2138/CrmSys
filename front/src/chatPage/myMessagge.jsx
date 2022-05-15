import React, { useState } from "react";
import "./style.scss";
import 'bootstrap/dist/css/bootstrap.min.css'
import { Container, Row, Col, Card } from 'react-bootstrap';


export default class MyMessage extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            who: this.makeWho(this.props.who),
            text: this.props.text,
            topic: this.props.topic
        }
    }

    componentDidMount( ){
        //console.log(this.state.who, this.state.text, this.state.topic)
    }

    makeWho(who) {
        if(who == "SEND_BY_USER"){
            return 1
        }
        if(who == "SEND_BY_CONTACT"){
            return 2
        }
        if(who == "GROUP_EMAIL"){
            return 3
        }
    }

    render() {
        const { who } = this.state;
        const { topic } = this.state;
        const { text } = this.state;

        let messageToRender;
        if (who == 1) {
            messageToRender =
                <Row>
                    <Col xs={2} sm={2} md={4}>
                    </Col>
                    <Col xs={10} sm={10} md={8}>
                        <Card  style={{ padding: '1rem', margin: '10px', backgroundColor: '#fff', color: '#000' }}>
                        <Card.Title>{this.state.topic}</Card.Title>
                            {this.state.text}
                        </Card>
                    </Col>
                </Row>
        }
        if(who == 2) {
            messageToRender =
                <Row>
                    <Col xs={10} sm={10} md={8}>
                        <Card className="text-white" style={{ padding: '1rem', margin: '10px', backgroundColor: "#9933ff" }}>
                        <Card.Title>{this.state.topic}</Card.Title>
                            {this.state.text}
                        </Card>
                    </Col>
                    <Col xs={2} sm={2} md={4}>
                    </Col>
                </Row>
        }
        if(who == 3) {
            messageToRender =
                <Row>
                    <Col xs={1} sm={1} md={2}></Col>
                    <Col xs={10} sm={10} md={8}>
                        <Card className="text-white" style={{ padding: '1rem', margin: '10px', backgroundColor: "#0000FF" }}>
                        <Card.Title>{this.state.topic}</Card.Title>
                            {this.state.text}
                        </Card>
                    </Col>
                    <Col xs={1} sm={1} md={2}>
                    </Col>
                </Row>
        }
        return (
            messageToRender
        )
    }
}