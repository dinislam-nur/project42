import React from "react";
import {Divider} from "@blueprintjs/core";
import Col from "reactstrap/es/Col";
import Row from "reactstrap/es/Row";
import Container from "reactstrap/es/Container";
import Order from "../../app/order";

const orders = [
    {
        id: "asdafdsafsadf",
        number: 122,
        status: "DONE",
        table: 42
    },
    {
        id: "asdfsafdsafsfsdf",
        number: 123,
        status: "WAITING",
        table: 1
    },
    {
        id: "asdfsasfsdf",
        number: 124,
        status: "WAITING",
        table: 1
    },
    {
        id: "asdfasfsdf",
        number: 125,
        status: "PREPARING",
        table: 3
    },
]


class MainPage extends React.Component {
    constructor(props) {
        super(props);
    }

    mappedOrders = Object.values(orders.reduce((r, a) => {
        r[a.status] = r[a.status] || [];
        r[a.status].push(a);
        return r;
    }, Object.create(null)));

    createCallout = (order)=> <Order order={order}/>;

    calculateRow = (array) => array.map((order) => (
        <Row md="5">
            {this.calculateCol(order)}
        </Row>));

    calculateCol = (order) => (
        <Col>
            {this.createCallout(order)}
        </Col>
    )

    elements = this.mappedOrders.map((array) => <>{this.calculateRow(array)}<Divider/></>);

    render() {
        return <Container>
            {this.elements}
        </Container>;
    }
}

export default MainPage;