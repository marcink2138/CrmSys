import React, { useState } from "react";
import "./style.scss";
import 'bootstrap/dist/css/bootstrap.min.css'
import profilePicture from "./userdefault2.png";
import { Container, Row, Col, ListGroup, Image } from 'react-bootstrap';


export default class PersonContact extends React.Component {

    constructor(props) {
        super(props);
        this.onClicked = this.onClicked.bind(this);
        this.state = {
            name: this.props.name,
            surname: this.props.surname,
            index: this.props.index,
            email: this.props.email,
            emailGroupName: this.props.emailGroupName,
            id: this.props.id
        }
    }

    componentDidMount() {
        //console.log(this.state.emailGroupName)
    }

    onClicked = (e) => {
        //console.log(this.state.name, this.state.surname);
        this.props.parentCallback(this.state.name, this.state.surname, this.state.email, this.state.emailGroupName, this.state.id);
        e.preventDefault();
    }

    render() {
        const { email } = this.state
        const { index } = this.state
        const { emailGroupName } = this.state;
        let personToRender;
        if (index % 2 == 1) {
            personToRender =
                <ListGroup.Item style={{ minWidth: '100%', maxWidth: '100%', minHeight: '10vh', maxHeight: '10vh', backgroundColor: "#9933ff" }} action onClick={this.onClicked}>
                    <Container >
                        <Row >
                            <Col xs={6} sm={6} md={4}>
                                <Image src={profilePicture} style={{ minHeight: '7vh', maxHeight: '7vh' }} />
                                {/* <div className="image"> 
                            <img src={profilePicture}/>
                        </div> */}
                            </Col>
                            <Col xs={6} sm={6} md={8}>
                                {this.state.name} {this.state.surname}
                            </Col>
                        </Row>
                    </Container>
                </ListGroup.Item>
        } else {
            personToRender =
                <ListGroup.Item style={{ minWidth: '100%', maxWidth: '100%', minHeight: '10vh', maxHeight: '10vh', backgroundColor: "#fff" }} action onClick={this.onClicked}>
                    <Container >
                        <Row >
                            <Col xs={6} sm={6} md={4}>
                                <Image src={profilePicture} style={{ minHeight: '7vh', maxHeight: '7vh' }} />
                                {/* <div className="image"> 
                            <img src={profilePicture}/>
                        </div> */}
                            </Col>
                            <Col xs={6} sm={6} md={8}>
                                {this.state.name} {this.state.surname}
                            </Col>
                        </Row>
                    </Container>
                </ListGroup.Item>
        }
        if (emailGroupName != undefined && index % 2 == 0) {
            personToRender =
                <ListGroup.Item style={{ minWidth: '100%', maxWidth: '100%', minHeight: '10vh', maxHeight: '10vh', backgroundColor: "#fff" }} action onClick={this.onClicked}>
                    <Container >
                        <Row >
                            <Col xs={6} sm={6} md={4}>
                                <Image src={profilePicture} style={{ minHeight: '7vh', maxHeight: '7vh' }} />
                                {/* <div className="image"> 
                    <img src={profilePicture}/>
                </div> */}
                            </Col>
                            <Col xs={6} sm={6} md={8}>
                                {this.state.emailGroupName}
                            </Col>
                        </Row>
                    </Container>
                </ListGroup.Item>
        }
        if (emailGroupName != undefined && index % 2 == 1) {
            personToRender =
                <ListGroup.Item style={{ minWidth: '100%', maxWidth: '100%', minHeight: '10vh', maxHeight: '10vh', backgroundColor: "#9933ff" }} action onClick={this.onClicked}>
                    <Container >
                        <Row >
                            <Col xs={6} sm={6} md={4}>
                                <Image src={profilePicture} style={{ minHeight: '7vh', maxHeight: '7vh' }} />
                                {/* <div className="image"> 
                    <img src={profilePicture}/>
                </div> */}
                            </Col>
                            <Col xs={6} sm={6} md={8}>
                                {this.state.emailGroupName}
                            </Col>
                        </Row>
                    </Container>
                </ListGroup.Item>
        }
        return (
            personToRender
        )
    }
}