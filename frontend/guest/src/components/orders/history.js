import React, {useState} from "react";
import Card from "reactstrap/es/Card";
import CardBody from "reactstrap/es/CardBody";
import CardTitle from "reactstrap/es/CardTitle";
import CardSubtitle from "reactstrap/es/CardSubtitle";
import {addDishToOrder, fetchOrdersHistory} from "../../store/actions/app";
import {connect} from "react-redux";
import Collapse from "reactstrap/es/Collapse";
import {Button, ButtonGroup} from "@blueprintjs/core";
import CardHeader from "reactstrap/es/CardHeader";


class OrdersHistory extends React.Component {
    constructor(props) {
        super(props);
    }

    async componentDidMount() {
        if (this.props.page === null) {
            await this.props.loadHistoryPage(0);
        }
    }

    prevPageHandler = () => {
        this.props.loadHistoryPage(this.props.page.pageable.pageNumber - 1);
    }

    nextPageHandler = () => {
        this.props.loadHistoryPage(this.props.page.pageable.pageNumber + 1);
    }

    render() {
        let list = null;
        if (this.props.page !== null) {
            list = this.props.page.content.map(order => <Order order={order}/>)
        }
        return (
            <>
                {
                    this.props.page !== null ?
                        <div>
                            {list}
                            <HistoryFooter onPrevPage={this.prevPageHandler}
                                           onNextPage={this.nextPageHandler}
                                           page={this.props.page}/>
                        </div> : null
                }

            </>
        )
    }

}

const Order = (props) => {
    const [open, setOpen] = useState(false);
    const list = props.order.foods.map((dish) => (
        <CardSubtitle style={{marginTop: "5px"}}><b>{dish.name}</b>: {dish.price}₽</CardSubtitle>
    ));
    let color = "secondary";
    let statusText = "";
    switch (props.order.status) {
        case "USER_CONFIRMED":
            statusText = "Статус: Ожидание подтверждения с кухни";
            color = "warning"; break;
        case "CANCELED":
            statusText = "Статус: Ожидание подтверждения с кухни";
            color = "danger"; break;
        case "PREPARING":
            statusText = "Статус: Готовится";
            color = "primary"; break;
        case "DONE":
            statusText = "Статус: Готов"
            color = "success"; break;
        case "DELIVERED":
            statusText = "Статус: Отдан гостю"
            color = "secondary"; break;
    }
    return (
        <div className={'history_card'}>
            <Card onClick={() => setOpen(!open)} >
                <CardHeader>
                    <CardTitle><b>Заказ №{props.order.id}</b></CardTitle>
                    <CardSubtitle>Сумма: {props.order.total}₽</CardSubtitle>
                    <CardSubtitle style={{marginTop: "5px"}}>{statusText}</CardSubtitle>
                </CardHeader>
                <Collapse isOpen={open}>
                    <CardBody>
                        {list}
                    </CardBody>
                </Collapse>

            </Card>
        </div>
    )
}

const HistoryFooter = props => {
    return (
        <div style={{textAlign: "center"}}>
            <ButtonGroup style={{display: "inline-block"}}>
                <Button icon={'chevron-left'} onClick={props.onPrevPage} disabled={props.page.first}/>
                <Button minimal active={false}>{props.page.number + 1}</Button>
                <Button icon={'chevron-right'} onClick={props.onNextPage} disabled={props.page.last}/>
            </ButtonGroup>
        </div>
    )
}

const mapStateToProps = state => (
    {
        category: state.app.category,
        page: state.app.ordersHistory,
        loaded: state.app.loaded,
    }
)


const mapDispatchToProps = dispatch => ({
    addDish: dish => dispatch(addDishToOrder(dish)),
    loadHistoryPage: page => dispatch(fetchOrdersHistory(page))
});

export default connect(mapStateToProps, mapDispatchToProps)(OrdersHistory);