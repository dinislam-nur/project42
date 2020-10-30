import React, {useState} from "react";
import Card from "reactstrap/es/Card";
import CardTitle from "reactstrap/es/CardTitle";
import CardColumns from "reactstrap/es/CardColumns";
import {Loader} from "../app/loader";
import {Button, ButtonGroup, Dialog, Intent} from "@blueprintjs/core";
import CardSubtitle from "reactstrap/es/CardSubtitle";
import CardHeader from "reactstrap/es/CardHeader";
import {fetchOrders, updateOrder, updateWaitersOrder} from "../../store/actions/app";
import {connect} from "react-redux";

export const WaitersPage = props => {
    return (
        <div>
            <Connected/>
        </div>
    )
}


class WaitersGroup extends React.Component {
    constructor(props) {
        console.log(props)
        super(props);
    }

    async componentDidMount() {
        await this.props.fetchOrders('DONE', 0, 18);
    }

    prevPageHandler = () => {
        this.props.fetchOrders('DONE', this.props.waitersOrders.pageable.pageNumber - 1, 18);
    }

    nextPageHandler = () => {
        this.props.fetchOrders('DONE', this.props.waitersOrders.pageable.pageNumber + 1, 18);
    }

    render() {
        let list = null;
        let orderColumns = (
            <Card>
                <CardTitle><b>Заказов нет <span role="img" aria-label="donut">😔</span></b></CardTitle>
            </Card>
        );
        if (this.props.waitersOrders !== null) {
            list = this.props.waitersOrders.content.map(order => <Order order={order}
                                                                          onConfirm={this.props.updateOrder}/>)
            if (this.props.waitersOrders.content.length !== 0) {
                orderColumns = (<CardColumns style={{width: "80%"}} className={"orders_centre"}>{list}</CardColumns>);
            }
        }
        return (
            <div className={"layout"}>
                <h1>Готовые заказы:</h1>
                {this.props.loaded ?
                    orderColumns : <Loader/>}
                {
                    this.props.waitersOrders === null || this.props.waitersOrders.content.length === 0 ?
                        null
                        :
                        <HistoryFooter onPrevPage={this.prevPageHandler}
                                       onNextPage={this.nextPageHandler}
                                       page={this.props.waitersOrders}
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
        order.status = 'DELIVERED';
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
                    <Button onClick={onConfirm} intent={Intent.SUCCESS}>Заказ доставлен</Button>
                </ButtonGroup>
            </Dialog>
        </Card>
    )
}

const mapStateToProps = state => {
    return {
        waitersOrders: state.app.waitersOrders,
        loaded: state.app.loaded
    }
}

const mapDispatchToProps = dispatch => ({
    fetchOrders: (status, page, size) => dispatch(fetchOrders(status, page, size)),
    updateOrder: order => dispatch(updateWaitersOrder(order))
});

const Connected = connect(mapStateToProps, mapDispatchToProps)(WaitersGroup);
