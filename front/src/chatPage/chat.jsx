import React, { useState } from "react";
import "./style.scss";
import 'bootstrap/dist/css/bootstrap.min.css'
import { Container, Row, Col, ListGroup, Card, CardGroup } from 'react-bootstrap';
import PersonContact from "./personContact";
import MessageScreen from "./messageScreen";
import Cookies from 'js-cookie';
import { BsChevronLeft } from "react-icons/bs";
import { BsChevronRight } from "react-icons/bs";
import { BsArrowRepeat } from "react-icons/bs";

export class Chat extends React.Component {

    constructor(props) {
        super(props);
        this.parentCallback = this.parentCallback.bind(this);
        this.parentCallbackMessageScreen = this.parentCallbackMessageScreen.bind(this);
        this.state = {
            shouldWeRender: false,
            peopleList: [],
            peopleList1: [],
            currnetSelectedData: [{
                name: '',
                surname: '',
                email: '',
                emailGroupName: '',
                id: 0
            }],
            numberOfPages: 0,
            currentPage: 1,
            pageSize: 8,
            pageToShow: 1,
            peopleToShow: [],
            peopleOrGroups: true
        }
        //this.getRecords();
    }

    componentDidMount() {
        //console.log('chat')
        this.getRecords();
        this.changeSetOfContacts();
    }

    componentDidUpdate() {
        //console.log(this.state.currnetSelectedData)
    }

    getRecords = (e) => {
        if (this.state.peopleOrGroups) {
            fetch(`http://localhost:8080/api/chat/chat-rooms`, {
                method: 'GET',
                headers: {
                    'accessToken': Cookies.get('accessToken')
                }
            })
                .then(response => response.json())
                .then(data => {
                    this.setState({ peopleList1: data })
                    this.setState({ peopleList: data }, function () {
                        this.countNumberOfPages();
                        //this.setState({ shouldWeRender: true })
                        console.log('ludzie');
                        console.log(data)
                        //console.log(this.state.peopleList)
                        var firstContact = this.state.peopleList[0]
                        this.setState({
                            currnetSelectedData: [{
                                name: firstContact.name,
                                surname: firstContact.surname,
                                email: firstContact.emailAddress,
                                emailGroupName: firstContact.emailGroupName,
                                id: firstContact.id
                            }]
                        })
                        this.divideIntoPages()
                    });
                })
                .catch((error) => {
                    console.error('Error', JSON.stringify(error));
                });
        } else {
            fetch(`http://localhost:8080/api/EmailGroup/read`, {
                method: 'GET',
                headers: {
                    'accessToken': Cookies.get('accessToken')
                }
            })
                .then(response => response.json())
                .then(data => {
                    this.setState({ peopleList1: data })
                    this.setState({ peopleList: data }, function () {
                        this.countNumberOfPages();
                        //this.setState({ shouldWeRender: true })
                        console.log('grupa');
                        console.log(data)
                        //console.log(this.state.peopleList)
                        var firstContact = this.state.peopleList[0]
                        this.setState({
                            currnetSelectedData: [{
                                name: firstContact.name,
                                surname: firstContact.surname,
                                email: firstContact.emailAddress,
                                emailGroupName: firstContact.emailGroupName,
                                id: firstContact.id
                            }]
                        })
                        this.divideIntoPages()
                    });
                })
                .catch((error) => {
                    console.error('Error', JSON.stringify(error));
                });
        }
    }

    countNumberOfPages = (e) => {
        const numberOfRecords = this.state.peopleList1.length;
        let pageCount = numberOfRecords / this.state.pageSize;
        pageCount = Math.trunc(pageCount);
        if (numberOfRecords % this.state.pageSize != 0) {
            pageCount += 1;
        }

        this.setState({ numberOfPages: pageCount });
    }

    divideIntoPages = (e) => {
        this.countNumberOfPages();
        this.setState({ shouldWeRender: false });
        let currPageRecords = [];
        for (let i = 0; i < this.state.pageSize; i++) {
            if ((this.state.currentPage - 1) * this.state.pageSize + i >= this.state.peopleList1.length) {
                break;
            }
            currPageRecords.push(this.state.peopleList1[((this.state.currentPage - 1) * this.state.pageSize) + i]);
            //console.log(currPageRecords)
        }
        // for (let i = currPageRecords.length; i < this.state.pageSize; i++) {
        //     currPageRecords.push({ name: '-1', surname: '-1', emailAddress: '-1' })
        //     console.log(currPageRecords)
        // }
        //console.log(currPageRecords)
        this.setState({ peopleToShow: currPageRecords }, function () {
            this.setState({ shouldWeRender: true });
        });
    }

    parentCallback(childDataName, childDataSurname, childDataEmail, childDataEmailGroupName, childDataId) {
        this.setState({ shouldWeRender: false }, function () {
            this.setState({
                currnetSelectedData: [{
                    name: childDataName,
                    surname: childDataSurname,
                    email: childDataEmail,
                    emailGroupName: childDataEmailGroupName,
                    id: childDataId
                }]
            }, function () {
                this.setState({ shouldWeRender: true })
            })
        });
        console.log(this.state.currnetSelectedData)
    }

    nextPage = (e) => {
        this.setState({ currentPage: parseInt(this.state.currentPage) + 1 }, this.divideIntoPages);
        this.setState({ pageToShow: parseInt(this.state.currentPage) + 1 });
    }

    previousPage = (e) => {
        if (this.state.currentPage > 1) {
            this.setState({ currentPage: this.state.currentPage - 1 }, this.divideIntoPages);
            this.setState({ pageToShow: this.state.currentPage - 1 });
        }
    }

    changeSetOfContacts = (e) => {
        console.log(this.state.peopleOrGroups)
        if (this.state.peopleOrGroups) {
            console.log('zmieniam')
            this.setState({ peopleOrGroups: false })
        } else {
            this.setState({ peopleOrGroups: true })
        }
        this.getRecords();
    }

    parentCallbackMessageScreen() {
        this.setState({ shouldWeRender: false }, function () {
            this.setState({ shouldWeRender: true })
        });
    }

    searchForRecords = (e) => {
        const searchValue = e.target.value;
        let records = [];
        this.state.peopleList.map((record) => {
            let stringRecord = "";
            for (let field in record) {
                stringRecord += record[field];
            }
            if (stringRecord.search(new RegExp(searchValue, "i")) != -1) {
                records.push(record);
            }
        });
        this.setState({ peopleList1: records }, this.divideIntoPages);
    }

    render() {
        const { shouldWeRender } = this.state;
        const { peopleList } = this.state;
        const { currnetSelectedData } = this.state;
        const { pageToShow } = this.state;
        const { numberOfPages } = this.state;
        const { peopleToShow } = this.state;
        return (
            //<div className="whole-page">
            <Container fluid className=" bg-dark">
                <Row>
                    {/* <CardGroup> */}
                    {/* Personlist */}
                    <Col xs={12} sm={6} lg={5} xl={4} xxl={3}>
                        <div className="person-screen">
                            <Card style={{ minHeight: '100%', maxHeight: '100%', overflowY: "clip" }} className=" bg-dark">
                                <Row>
                                    <Col>
                                        <ListGroup>
                                            <ListGroup.Item style={{ marginTop: '1vh', minWidth: '100%', maxWidth: '100%', minHeight: '6vh', maxHeight: '6vh', backgroundColor: "#9933ff" }} action onClick={this.onClicked}>
                                                <Container >
                                                    <Row >
                                                        <Col xs={78} sm={7} md={7}>
                                                            <div className="actionBarInput">
                                                                <input type="text" placeholder="Search for person" onChange={this.searchForRecords} />
                                                            </div>
                                                        </Col>
                                                        <Col xs={5} sm={5} md={5}>
                                                            <button className="butoon-to-swipe" onClick={this.previousPage}><BsChevronLeft style={{ color: '#fff' }} /></button>
                                                            <label style={{ color: '#fff' }}> {pageToShow} / {numberOfPages} </label>
                                                            <button className="butoon-to-swipe" onClick={this.nextPage}><BsChevronRight style={{ color: '#fff' }} /></button>
                                                            <button className="butoon-to-swipe" onClick={this.changeSetOfContacts}><BsArrowRepeat style={{ color: '#fff' }} /></button>
                                                        </Col>
                                                    </Row>
                                                </Container>
                                            </ListGroup.Item>
                                            {shouldWeRender
                                                && peopleToShow.map((people, index) => (
                                                    <PersonContact
                                                        parentCallback={this.parentCallback}
                                                        key={index}
                                                        index={index}
                                                        name={people.name}
                                                        surname={people.surname}
                                                        email={people.emailAddress}
                                                        emailGroupName={people.emailGroupName}
                                                        id={people.id}
                                                    />
                                                ))
                                            }
                                        </ListGroup>
                                    </Col>

                                </Row>
                            </Card>
                        </div>
                    </Col>
                    <Col xs={12} sm={6} lg={7} xl={8} xxl={9}>
                        <div className="message-screen">
                            <Container style={{ minHeight: '100%', maxHeight: '100%' }} className=" bg-secendary">
                                {/* Chat */}
                                {shouldWeRender
                                    && currnetSelectedData.map((user, index) => (
                                        <MessageScreen
                                            parentCallbackMessageScreen={this.parentCallbackMessageScreen}
                                            key={index}
                                            name={user.name}
                                            surname={user.surname}
                                            email={user.email}
                                            id={user.id}
                                            emailGroupName={user.emailGroupName}
                                        />
                                    ))
                                }
                            </Container>
                        </div>
                    </Col>
                </Row>
                {/* </CardGroup> */}
            </Container>
            //</div>
        )
    }
}