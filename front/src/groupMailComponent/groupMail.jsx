import React, { useState } from "react";
import "./groupMail.scss";
import 'bootstrap/dist/css/bootstrap.min.css'
import { Container, Row, Col, ListGroup, Card, CardGroup, Accordion } from 'react-bootstrap';
import Cookies from 'js-cookie';
import { BsChevronLeft } from "react-icons/bs";
import { BsChevronRight } from "react-icons/bs";
import ForecastRow from "./forcecastRow";

export class GroupMail extends React.Component {

    constructor(props) {
        super(props);
        this.parentCallbackForecast = this.parentCallbackForecast.bind(this);
        this.state = {
            shouldWeRender: true,
            listWithForecast: [],
            listWithForecastAdd: []
        }
    }

    componentDidMount() {
        // this.setState({ shouldWeRender: false }, function () {
        //     this.getRecords();
        // });
        this.parentCallbackForecast();
    }

    componentDidUpdate() {
        console.log('Updacik')
    }

    getRecords = (e) => {
        this.setState({ shouldWeRender: false }, function () {
            this.setState({ listWithForecast: [] }, function () {
                this.setState({ listWithForecastAdd: [] }, function () {
                    fetch(`http://localhost:8080/api/EmailGroup/read`, {
                        method: 'GET',
                        headers: {
                            'accessToken': Cookies.get('accessToken')
                        }
                    })
                        .then(response => response.json())
                        .then(data => {
                            console.log('Success:', JSON.stringify(data));
                            var index = 0;
                            for (var column of data) {
                                if (column.forecastSub == true) {
                                    console.log(column)
                                    this.state.listWithForecast.push(column)
                                } else {
                                    this.state.listWithForecastAdd.push(column)
                                    console.log(column)
                                }
                                index = index + 1;
                            }
                            this.setState({ listWithForecast: this.state.listWithForecast }, function () {
                                this.setState({ shouldWeRender: true })
                            });
                        })
                        .catch((error) => {
                            console.error('Error', JSON.stringify(error));
                        });
                });
            });
        });
    }

    parentCallbackForecast = (addOrDelete, emailGroupName) => {
        if (addOrDelete == 'add') {
            const data1 = [
                emailGroupName
            ];
            fetch(`http://localhost:8080/api/EmailGroup/forecast/add`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'accessToken': Cookies.get('accessToken')
                },
                body: JSON.stringify(data1),
            })
                .then(response => response.json())
                .then(data => {
                    console.log('Success:', JSON.stringify(data));
                })
                .catch((error) => {
                    console.error('Error', JSON.stringify(error));
                });
        } else {
            const data1 = [
                emailGroupName
            ];
            console.log('wysylanie delete mail')
            console.log(data1)
            fetch(`http://localhost:8080/api/EmailGroup/forecast/delete`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'accessToken': Cookies.get('accessToken')
                },
                body: JSON.stringify(data1),
            })
                .then(response => response.json())
                .then(data => {
                    console.log('Success:', JSON.stringify(data));
                })
                .catch((error) => {
                    console.error('Error', JSON.stringify(error));
                });
        }
        this.setState({ shouldWeRender: false }, function () {
            this.getRecords();
        });
    }

    render() {
        const { shouldWeRender } = this.state;
        const { listWithForecast } = this.state;
        const { listWithForecastAdd } = this.state;
        return (
            <Container>
                <Card style={{ width: '100%', marginTop: '3vh', borderRadius: '20px' }}>
                    <Card.Body>
                        <Accordion defaultActiveKey={['0', '1']} alwaysOpen flush>
                            <Accordion.Item eventKey="0">
                                <Accordion.Header>Energy forecast group</Accordion.Header>
                                <Accordion.Body>
                                    {shouldWeRender
                                        && listWithForecast.map((rows, index) => (
                                            <ForecastRow
                                                parentCallbackForecast={this.parentCallbackForecast}
                                                key={index}
                                                which={'is'}
                                                first={rows.emailGroupName}
                                                second={rows.emailGroupName}
                                                id={rows.id}
                                            />
                                        ))
                                    }
                                </Accordion.Body>
                            </Accordion.Item>
                            <Accordion.Item eventKey="1">
                                <Accordion.Header>Add group to energy forecast</Accordion.Header>
                                <Accordion.Body>
                                    {shouldWeRender
                                        && listWithForecastAdd.map((rows, index) => (
                                            <ForecastRow
                                                parentCallbackForecast={this.parentCallbackForecast}
                                                key={index}
                                                which={'add'}
                                                first={rows.emailGroupName}
                                                second={rows.emailGroupName}
                                                id={rows.id}
                                            />
                                        ))
                                    }
                                </Accordion.Body>
                            </Accordion.Item>
                        </Accordion>
                    </Card.Body>
                </Card>
            </Container>
        )
    }
}