import React from "react";
import { withRouter } from "react-router";
import "./datatable.scss"
import { BsChevronLeft } from "react-icons/bs";
import { BsChevronRight } from "react-icons/bs";
import { ModalMy } from "../modal/modal";
import Cookies from 'js-cookie';
import { FOCUSABLE_SELECTOR } from "@testing-library/user-event/dist/utils";
import { Button } from "bootstrap";

export default class DatatableComponent extends React.Component {

    constructor(props) {
        super(props);
        this.parentCallbackDatatable = this.parentCallbackDatatable.bind(this);
        this.state = {
            numberOfPages: 0,
            currentPage: 1,
            records: [],
            recordsFiltered: [],
            recordsToShow: [],
            columnsLabels: [],
            PAGE_SIZE: 15,
            dataType: this.props.type,
            pageToShow: 1,
            readyToShow: false
        }
        this.getLabels();
        this.getRecords();
    };

    parentCallbackDatatable = () => {
        console.log('no to co')
        this.setState({ readyToShow: false }, function () {
            this.getLabels();
            this.getRecords();
            this.setState({ readyToShow: true });
        });
    }

    getLabels = (e) => {
        fetch(`http://localhost:8080/api/db-info/table-columns/${this.state.dataType}`, {
            method: 'GET',
            headers: {
                'accessToken': Cookies.get('accessToken')
            }
        })
            .then(response => response.json())
            .then(data => {
                this.setState({ columnsLabels: data });
            })
            .catch((error) => {
                console.error('Error', JSON.stringify(error));
            });
    }

    getRecords = (e) => {
        fetch(`http://localhost:8080/api/${this.state.dataType}/read`, {
            method: 'GET',
            headers: {
                'accessToken': Cookies.get('accessToken')
            }
        })
            .then(response => response.json())
            .then(data => {
                this.setState({ records: data });
                this.setState({ recordsFiltered: data });
                this.divideIntoPages();
            })
            .catch((error) => {
                console.error('Error', JSON.stringify(error));
            });
        this.countNumberOfPages();
    }

    countNumberOfPages = (e) => {
        const numberOfRecords = this.state.recordsFiltered.length;
        let pageCount = numberOfRecords / this.state.PAGE_SIZE;
        pageCount = Math.trunc(pageCount);
        if (numberOfRecords % this.state.PAGE_SIZE != 0) {
            pageCount += 1;
        }

        this.setState({ numberOfPages: pageCount });
    }

    divideIntoPages = (e) => {
        this.countNumberOfPages();
        this.setState({ readyToShow: false });
        let currPageRecords = [];
        for (let i = 0; i < this.state.PAGE_SIZE; i++) {
            if ((this.state.currentPage - 1) * this.state.PAGE_SIZE + i >= this.state.recordsFiltered.length) {
                break;
            }
            currPageRecords.push(this.state.recordsFiltered[((this.state.currentPage - 1) * this.state.PAGE_SIZE) + i]);
        }
        this.setState({ recordsToShow: currPageRecords });
        this.setState({ readyToShow: true });
    }

    linesRender = (e) => {
        const record = e;
        const fieldList = [];
        for (var column of this.state.columnsLabels) {
            if (record == null) {
                continue;
            }
            if (column.columnName == 'id') {
                continue;
            }
            if (record.hasOwnProperty(column.columnName)) {
                fieldList.push(<td>{record[column.columnName]}</td>);
            } else {
                fieldList.push(<td></td>);
            }
        }
        fieldList.push(<td className="tdButtons"><ModalMy buttonText={'Modify'} record={record} fields={this.state.columnsLabels} method={'modify'} tableName={this.state.dataType} parentCallbackDatatable={this.parentCallbackDatatable}/>
        <ModalMy buttonText={'Delete'} record={record} fields={this.state.columnsLabels} method={'delete'} tableName={this.state.dataType} parentCallbackDatatable={this.parentCallbackDatatable}/></td>)
        return fieldList;
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

    changePage = (e) => {
        if ((parseInt(e.target.value) >= 1) && (parseInt(e.target.value) != NaN)) {
            this.setState({ currentPage: e.target.value }, this.divideIntoPages);
            this.setState({ pageToShow: e.target.value });
        } else {
            this.setState({ pageToShow: e.target.value });
        }
    }

    searchForRecords = (e) => {
        const searchValue = e.target.value;
        let records = [];
        this.state.records.map((record) => {
            let stringRecord = "";
            for (let field in record) {
                stringRecord += record[field].valueOf();
            }
            if (stringRecord.search(new RegExp(searchValue, "i")) != -1) {
                records.push(record);
            }
        });
        this.setState({ recordsFiltered: records }, this.divideIntoPages);
    }

    render() {
        this.linesRender();
        return (
            <div className="datatableWrapper">
                <div className="datatableActionBar">
                    <div className="actionBarInput">
                        <input type="text" className="form-control" placeholder="Search for record" aria-label="Search for record" onChange={this.searchForRecords}></input>
                    </div>
                    <div className="recordCounter">
                        Number of records: {this.state.recordsFiltered.length}
                    </div>
                    <div className="datatablePagination">
                        <button className='chevronButton' onClick={this.previousPage}><BsChevronLeft /></button>
                        <div className="pageNumbers">
                            <input type="text" className="form-control pageInput" onChange={this.changePage} value={this.state.pageToShow} />
                            /
                            {this.state.numberOfPages}
                        </div>
                        <button className='chevronButton' onClick={this.nextPage}><BsChevronRight /></button>
                    </div>
                    <div className="createButtonInActionBar">
                        {this.state.readyToShow &&
                            <ModalMy buttonText={'Create'} record={this.state.columnsLabels} fields={this.state.columnsLabels} method={'create'} tableName={this.state.dataType} parentCallbackDatatable={this.parentCallbackDatatable} />
                        }
                    </div>
                </div>
                <div className="table">
                    <table className="table table-striped data-resizable">
                        <thead>
                            <tr>
                                {this.state.columnsLabels.map((app) => (
                                    <th scope="col">
                                        {app.columnName}
                                    </th>
                                ))}
                            </tr>
                        </thead>
                        {this.state.readyToShow && <tbody>
                            {this.state.recordsToShow.map((record) => (
                                <tr key={record.id}>
                                    {this.linesRender(record)}
                                </tr>
                            ))}
                        </tbody>
                        }
                    </table>
                </div>
            </div>
        )
    }

}

export const Datatable = withRouter(DatatableComponent)