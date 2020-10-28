import React, {useState} from "react";
import {fetchConfirmOrders, updateOrder} from "../../../../store/actions/app";
import {connect} from "react-redux";
import CardColumns from "reactstrap/es/CardColumns";
import {Button, ButtonGroup, Dialog} from "@blueprintjs/core";
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
        if (this.props.orders !== null) {
            list = this.props.orders.content.map(order => <Order order={order} onConfirm={this.props.updateOrder}/>)
        }
        return (
            <div className={"layout"}>
                {this.props.loaded?<CardColumns style={{width: "80%"}} className={"orders_centre"}>{list}</CardColumns>:<Loader/>}
                {
                    this.props.orders !== null ?
                        <HistoryFooter onPrevPage={this.prevPageHandler}
                                       onNextPage={this.nextPageHandler}
                                       page={this.props.orders}
                        />
                        : null
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
        orders: state.app.orders,
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

    const onConfirm = ()=>{
        props.order.status = 'PREPARING';
        props.onConfirm(props.order);
    }

    const list = props.order.foods.map((dish) => (
        <CardSubtitle style={{marginTop: "5px"}}><b>{dish.name}</b>: {dish.price}₽</CardSubtitle>
    ));
    return (
        <Card onClick={() => setOpen(true)} color={intent}>
            <CardHeader>
                <CardTitle><b>Заказ №{props.order.id}</b></CardTitle>
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
            </Dialog>
            <Button onClick={onConfirm}>Подтвердить</Button>
        </Card>
    )
}
