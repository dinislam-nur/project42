import React from "react";
import {connect} from "react-redux";
import {OrdersConfirmGroup, OrdersPreparingGroup} from "../../app/order/group/confirm";
import {Loader} from "../../app/loader";


class OrdersPage extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div>
                <OrdersConfirmGroup status={'USER_CONFIRMED'}/>
                <OrdersPreparingGroup status={'PREPARING'}/>
            </div>
        )
    }

}

const mapStateToProps = state => {
    return {
        orders: state.app.orders,
        loaded: state.app.loaded
    }
}

const mapDispatchToProps = dispatch => ({});

export default connect(mapStateToProps, mapDispatchToProps)(OrdersPage);