import React, { useState } from "react";
import "./groupMail.scss";
import 'bootstrap/dist/css/bootstrap.min.css'
import { Container, Row, Col, ListGroup, Image, Button } from 'react-bootstrap';


export default class ForecastRow extends React.Component {

    constructor(props) {
        super(props);
        this.parentCallbackForecast = this.parentCallbackForecast.bind(this);
        this.state = {
            which: this.props.which,
            index: this.props.index,
            first: this.props.first,
            second: this.props.second,
            id: this.props.id
        }
    }

    componentDidMount() {

    }

    parentCallbackForecast = (e) => {
        var addOrDelete = e.target.value
        this.props.parentCallbackForecast(addOrDelete, this.state.first);
    }

    render() {
        const { which } = this.state;
        const { index } = this.state;
        const { first } = this.state;
        const { second } = this.state;

        let rowToRender;
        if (which == 'is') {
            rowToRender =
                <Row style={{ marginTop: '4px', borderRadius: '10px', backgroundColor: 'rgba(233, 233, 233, 0.856)' }}>
                    <Col xs={12} sm={5} lg={5} xl={5} xxl={5} style={{ alignSelf: "center" }}>
                        {first}
                    </Col>
                    <Col xs={12} sm={5} lg={5} xl={5} xxl={5} style={{ alignSelf: "center" }}>
                        {second}
                    </Col>
                    <Col xs={12} sm={0} lg={1} xl={1} xxl={1} style={{ alignSelf: "center" }}>
                    </Col>
                    <Col xs={12} sm={2} lg={1} xl={1} xxl={1} style={{ alignSelf: "center" }}>
                        <Row style={{ marginRight: '4px' }}>
                            <Button value={'delete'} variant="primary" onClick={this.parentCallbackForecast} style={{ alignSelf: "end", borderRadius: '10px', marginBottom: '4px', marginTop: '4px' }}>
                                Delete
                            </Button>
                        </Row>
                    </Col>
                </Row>
        } else {
            rowToRender =
                <Row style={{ marginTop: '4px', borderRadius: '10px', backgroundColor: 'rgba(233, 233, 233, 0.856)' }}>
                    <Col xs={12} sm={4} lg={5} xl={5} xxl={5} style={{ alignSelf: "center" }}>
                        {first}
                    </Col>
                    <Col xs={12} sm={4} lg={5} xl={5} xxl={5} style={{ alignSelf: "center" }}>
                        {second}
                    </Col>
                    <Col xs={12} sm={1} lg={1} xl={1} xxl={1} style={{ alignSelf: "center" }}>
                    </Col>
                    <Col xs={12} sm={1} lg={1} xl={1} xxl={1} style={{ alignSelf: "center" }}>
                        <Row style={{ marginRight: '4px' }}>
                            <Button value={'add'} variant="primary" onClick={this.parentCallbackForecast} style={{ alignSelf: "end", borderRadius: '10px', marginBottom: '4px', marginTop: '4px' }}>
                                Add
                            </Button>
                        </Row>
                    </Col>
                </Row>
        }
        return (
            rowToRender
        )
    }
}