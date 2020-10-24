import React from "react";
import {addDishToOrder, removeDishFromOrder} from "../../store/actions/app";
import {connect} from "react-redux";

import Card from "reactstrap/es/Card";
import CardBody from "reactstrap/es/CardBody";
import CardTitle from "reactstrap/es/CardTitle";
import CardSubtitle from "reactstrap/es/CardSubtitle";
import {OrdersFooter} from "../orders/footer";
import {Button, ButtonGroup} from "@blueprintjs/core";

class OrdersPage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            currentOrder: true
        }
    }
    
    render() {
        const order = this.props.order.foods.map(dish => (
            <OrdersDish dish={dish} onDelete={this.props.removeDish}/>));
        const currentOrderBody = (<>
            {order.length === 0 ? <EmptyOrder/> : order}
            <OrdersFooter total={this.props.order.total}/>
        </>)
        return (
            <>
                <div style={{paddingTop: "60px"}}>
                    <div className={"orders_page_buttongroup"}>
                        <ButtonGroup className={"orders_page_buttongroup"}>
                            <Button active={this.state.currentOrder}
                                    onClick={() => this.setState({...this.state, currentOrder: true})}>Заказ</Button>
                            <Button active={!this.state.currentOrder}
                                    onClick={() => this.setState({...this.state, currentOrder: false})}>История</Button>
                        </ButtonGroup>
                    </div>
                    {
                        this.state.currentOrder ?
                            currentOrderBody
                            :
                            null
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
        order: state.app.order
    }
}

const mapDispatchToProps = dispatch => ({
    addDish: dish => dispatch(addDishToOrder(dish)),
    removeDish: dish => dispatch(removeDishFromOrder(dish))
});

export default connect(mapStateToProps, mapDispatchToProps)(OrdersPage);