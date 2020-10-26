import React from "react";
import {addDishToOrder, removeDishFromOrder, uploadOrder} from "../../store/actions/app";
import {connect} from "react-redux";

import Card from "reactstrap/es/Card";
import CardBody from "reactstrap/es/CardBody";
import CardTitle from "reactstrap/es/CardTitle";
import CardSubtitle from "reactstrap/es/CardSubtitle";
import {OrdersFooter} from "../orders/footer";
import {Button, ButtonGroup} from "@blueprintjs/core";
import {Loader} from "../app/loader";
import OrdersHistory from "../orders/history";

class OrdersPage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            currentOrder: true
        }
    }

    onConfirmClickHandler = () => {
        const foodsId = this.props.order.foods.map(food => food.id);
        this.props.uploadOrder(this.props.session.table.id, foodsId);
    }

    render() {
        const order = this.props.order.foods.map(dish => (
            <OrdersDish dish={dish} onDelete={this.props.removeDish}/>));
        const currentOrderBody = (<>
            {order.length === 0 ? <EmptyOrder/> : order}
            <OrdersFooter total={this.props.order.total} onConfirm={this.onConfirmClickHandler}/>
        </>)

        return (
            <>

                <div style={{paddingTop: "60px"}}>
                    <div className={"orders_page_buttongroup"}>
                        <ButtonGroup className={"orders_page_buttongroup"}>
                            <Button active={this.state.currentOrder}
                                    onClick={() => this.setState({
                                        ...this.state,
                                        currentOrder: true
                                    })}>Заказ</Button>
                            <Button active={!this.state.currentOrder}
                                    onClick={() => this.setState({
                                        ...this.state,
                                        currentOrder: false
                                    })}>История</Button>
                        </ButtonGroup>
                    </div>
                    {
                        this.state.currentOrder ?
                            this.props.loaded ?
                                currentOrderBody :
                                <Loader style={{marginTop: "160px", zIndex: "1000"}}/>
                            :
                            (this.props.loaded ? <OrdersHistory/> : <Loader/>)
                    }
                </div>
            </>
        )
    }
}

const OrdersDish = (props) => {
    const onClickHandler = () => props.onDelete(props.dish);
    return (
        <div className={'order_dish_card'}>
            <Card>
                <CardBody>
                    <div className={'orders_page_card_body '}>
                        <CardTitle><b>{props.dish.name}</b></CardTitle>
                        <CardSubtitle>{props.dish.price}₽</CardSubtitle>
                    </div>
                    <Button className={'orders_page_card_button '} close size={"sm"}
                            onClick={onClickHandler}>Удалить</Button>
                </CardBody>
            </Card>
        </div>
    )
}

const EmptyOrder = (props) => {
    return (
        <Card>
            <CardTitle className={"empty_order"}><b>Заказ пустой 😔</b></CardTitle>
        </Card>
    )
}

const mapStateToProps = state => {
    return {
        order: state.app.order,
        session: state.app.session,
        loaded: state.app.loaded
    }
}

const mapDispatchToProps = dispatch => ({
    addDish: dish => dispatch(addDishToOrder(dish)),
    removeDish: dish => dispatch(removeDishFromOrder(dish)),
    uploadOrder: (tableId, order) => dispatch(uploadOrder(tableId, order))
});

export default connect(mapStateToProps, mapDispatchToProps)(OrdersPage);