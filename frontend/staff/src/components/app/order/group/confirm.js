import React, {useState} from "react";
import {fetchOrders, updateOrder} from "../../../../store/actions/app";
import {connect} from "react-redux";
import CardColumns from "reactstrap/es/CardColumns";
import {Button, ButtonGroup, Dialog, Intent} from "@blueprintjs/core";
import {Loader} from "../../loader";
import CardSubtitle from "reactstrap/es/CardSubtitle";
import Card from "reactstrap/es/Card";
import CardHeader from "reactstrap/es/CardHeader";
import CardTitle from "reactstrap/es/CardTitle";

class DisconnectedOrdersConfirmGroup extends React.Component {
    constructor(props) {
        super(props);
    }

    async componentDidMount() {
        await this.props.fetchOrders(this.props.status, 0, 9);
    }

    prevPageHandler = () => {
        this.props.fetchOrders(this.props.status, this.props.confirmOrders.pageable.pageNumber - 1, 9);
    }

    nextPageHandler = () => {
        this.props.fetchOrders(this.props.status, this.props.confirmOrders.pageable.pageNumber + 1, 9);
    }

    render() {
        let list = null;
        let orderColumns = (
            <Card>
                <CardTitle><b>Заказов нет <span role="img" aria-label="donut">😔</span></b></CardTitle>
            </Card>
        );
        if (this.props.confirmOrders !== null) {
            list = this.props.confirmOrders.content.map(order => <Order order={order}
                                                                        onConfirm={this.props.updateOrder}
                                                                        confirm={true}/>)
            if (this.props.confirmOrders.content.length !== 0) {
                orderColumns = (<CardColumns style={{width: "80%"}} className={"orders_centre"}>{list}</CardColumns>);
            }
        }
        return (
            <div className={"layout"}>
                <h1>Ожидают подтверждения:</h1>
                {this.props.loaded ?
                    orderColumns : <Loader/>}
                {
                    this.props.confirmOrders === null || this.props.confirmOrders.content.length === 0 ?
                        null
                        :
                        <HistoryFooter onPrevPage={this.prevPageHandler}
                                       onNextPage={this.nextPageHandler}
                                       page={this.props.confirmOrders}
                        />
                }
            </div>
        )
    }
}


class DisconnectedOrdersPreparingGroup extends React.Component {
    constructor(props) {
        super(props);
    }

    async componentDidMount() {
        await this.props.fetchOrders(this.props.status, 0, 9);
    }

    prevPageHandler = () => {
        this.props.fetchOrders(this.props.status, this.props.preparingOrders.pageable.pageNumber - 1, 9);
    }

    nextPageHandler = () => {
        this.props.fetchOrders(this.props.status, this.props.preparingOrders.pageable.pageNumber + 1, 9);
    }

    render() {
        let list = null;
        let orderColumns = (
            <Card>
                <CardTitle><b>Заказов нет <span role="img" aria-label="donut">😔</span></b></CardTitle>
            </Card>
        );
        if (this.props.preparingOrders !== null) {
            list = this.props.preparingOrders.content.map(order => <Order order={order}
                                                                          onConfirm={this.props.updateOrder}/>)
            if (this.props.preparingOrders.content.length !== 0) {
                orderColumns = (<CardColumns style={{width: "80%"}} className={"orders_centre"}>{list}</CardColumns>);
            }
        }
        return (
            <div className={"layout"}>
                <h1>Готовятся:</h1>
                {this.props.loaded ?
                    orderColumns : <Loader/>}
                {
                    this.props.preparingOrders === null || this.props.preparingOrders.content.length === 0 ?
                        null
                        :
                        <HistoryFooter onPrevPage={this.prevPageHandler}
                                       onNextPage={this.nextPageHandler}
                                       page={this.props.preparingOrders}
                        />
                }
            </div>
        )
    }
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

const Order = (props) => {

    const [open, setOpen] = useState(false);

    const onConfirm = () => {
        const order = JSON.parse(JSON.stringify(props.order));
        if (order.status === 'USER_CONFIRMED') {
            order.status = 'PREPARING';
        } else if (order.status === 'PREPARING') {
            order.status = 'DONE';
        }
        props.onConfirm(order);
    }

    const onCancel = () => {
        const order = JSON.parse(JSON.stringify(props.order));
        order.status = 'CANCELED';
        props.onConfirm(order);
    }

    const list = props.order.foods.map((dish) => (
        <CardSubtitle style={{marginTop: "5px"}}><b>{dish.name}</b>: {dish.price}₽</CardSubtitle>
    ));
    return (
        <Card onClick={() => setOpen(true)}>
            <CardHeader>
                <CardTitle><b>Заказ №{props.order.id}</b></CardTitle>
                <CardSubtitle><b>Стол №{props.order.tableNumber}</b></CardSubtitle>
            </CardHeader>
            <Dialog
                icon="info-sign"
                onClose={() => setOpen(false)}
                isCloseButtonShown={false}
                title={"Состав заказа #" + props.order.id}
                isOpen={open}
            >
                <Card style={{padding: "10px"}}>
                    {list}
                </Card>
                <ButtonGroup>
                    <Button onClick={onConfirm}
                            intent={Intent.SUCCESS}>{props.confirm ? 'Подтвердить заказ' : 'Заказ готов'}</Button>
                    <Button onClick={onCancel} intent={Intent.DANGER}>Отменить заказ</Button>
                </ButtonGroup>
            </Dialog>
        </Card>
    )
}


const mapStateToProps = state => {
    return {
        confirmOrders: state.app.confirmOrders,
        preparingOrders: state.app.preparingOrders,
        loaded: state.app.loaded
    }
}

const mapDispatchToProps = dispatch => ({
    fetchOrders: (status, page, size) => dispatch(fetchOrders(status, page, size)),
    updateOrder: order => dispatch(updateOrder(order))
});

export const OrdersConfirmGroup = connect(mapStateToProps, mapDispatchToProps)(DisconnectedOrdersConfirmGroup);
export const OrdersPreparingGroup = connect(mapStateToProps, mapDispatchToProps)(DisconnectedOrdersPreparingGroup);
