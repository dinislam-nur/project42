import React, {useState} from "react";
import {fetchConfirmOrders, updateOrder} from "../../../../store/actions/app";
import {connect} from "react-redux";
import CardColumns from "reactstrap/es/CardColumns";
import {Button, ButtonGroup, Dialog, Intent} from "@blueprintjs/core";
import {Loader} from "../../loader";
import CardSubtitle from "reactstrap/es/CardSubtitle";
import Card from "reactstrap/es/Card";
import CardHeader from "reactstrap/es/CardHeader";
import CardTitle from "reactstrap/es/CardTitle";

class OrdersConfirmGroup extends React.Component {
    constructor(props) {
        super(props);
    }

    async componentDidMount() {
        await this.props.fetchConfirmOrders(0);
    }

    prevPageHandler = () => {
        this.props.fetchConfirmOrders(this.props.orders.pageable.pageNumber - 1);
    }

    nextPageHandler = () => {
        this.props.fetchConfirmOrders(this.props.orders.pageable.pageNumber + 1);
    }

    render() {
        let list = null;
        let orderColumns = (
            <Card>
                <CardTitle><b>Заказов нет <span role="img" aria-label="donut">😔</span></b></CardTitle>
            </Card>
        );
        if (this.props.orders !== null) {
            list = this.props.orders.content.map(order => <Order order={order} onConfirm={this.props.updateOrder}/>)
            if (this.props.orders.content.length !== 0) {
                orderColumns = (<CardColumns style={{width: "80%"}} className={"orders_centre"}>{list}</CardColumns>);
            }
        }
        return (
            <div className={"layout"}>
                <h1>Ожидают подтверждения:</h1>
                {this.props.loaded ?
                    orderColumns : <Loader/>}
                {
                    this.props.orders === null || this.props.orders.content.length === 0 ?
                        null
                        :
                        <HistoryFooter onPrevPage={this.prevPageHandler}
                                       onNextPage={this.nextPageHandler}
                                       page={this.props.orders}
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

const mapStateToProps = state => {
    return {
        orders: state.app.confirmOrders,
        loaded: state.app.loaded
    }
}

const mapDispatchToProps = dispatch => ({
    fetchConfirmOrders: (page) => dispatch(fetchConfirmOrders(page)),
    updateOrder: order => dispatch(updateOrder(order))
});

export default connect(mapStateToProps, mapDispatchToProps)(OrdersConfirmGroup);


const Order = (props) => {

    const [open, setOpen] = useState(false);


    let intent;

    switch (props.order.status) {
        case "DONE":
            intent = "success";
            break;
        case "USER_CONFIRMED":
            intent = "warning";
            break;
        case "PREPARING":
            intent = "primary";
            break;
        default:
            intent = "primary";
            break;
    }

    const onConfirm = () => {
        const order = JSON.parse(JSON.stringify(props.order));
        order.status = 'PREPARING';
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
        <Card onClick={() => setOpen(true)} color={intent}>
            <CardHeader>
                <CardTitle><b>Заказ №{props.order.id}</b></CardTitle>
                <CardSubtitle><b>Стол №{props.order.id}</b></CardSubtitle>
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
                    <Button onClick={onConfirm} intent={Intent.SUCCESS}>Подтвердить</Button>
                    <Button onClick={onCancel} intent={Intent.DANGER}>Отмена</Button>
                </ButtonGroup>
            </Dialog>
        </Card>
    )
}
