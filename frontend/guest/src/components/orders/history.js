import React, {useState} from "react";
import Card from "reactstrap/es/Card";
import CardBody from "reactstrap/es/CardBody";
import CardTitle from "reactstrap/es/CardTitle";
import CardSubtitle from "reactstrap/es/CardSubtitle";
import {Collapse} from "bootstrap/js/src";
import {addDishToOrder, changeDishes} from "../../store/actions/app";
import {connect} from "react-redux";


class OrdersHistory extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        const orders = props.orders.map(order => <Order order={order}/>);
        return (
            <div>
                {orders}
            </div>
        )
    }

}

const Order = (props) => {
    const [open, setOpen] = useState(false);
    const list = props.order.foods.map((dish) => (
        <CardSubtitle><b>{dish.name}</b>: {dish.price}₽</CardSubtitle>
    ));
    return (
        <div className={'dish_card'}>
            <Card>
                <CardBody>
                    <CardTitle onClick={() => setOpen(!open)}><b>Заказ №{props.order.id}</b></CardTitle>
                    <Collapse isOpen={open}>
                        {list}
                    </Collapse>
                </CardBody>
            </Card>
        </div>
    )
}


const mapStateToProps = state => (
    {
        page: state.app.dishPage,
        category: state.app.category,
        orders: state.app.ordersHistory
    }
)


const mapDispatchToProps = dispatch => ({
    addDish: dish => dispatch(addDishToOrder(dish)),
    loadPage: (category, page) => dispatch(changeDishes(category, page))
});

export default connect(mapStateToProps, mapDispatchToProps)(OrdersHistory);