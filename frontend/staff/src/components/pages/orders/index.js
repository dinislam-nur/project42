import React from "react";
import {connect} from "react-redux";
import OrdersConfirmGroup from "../../app/order/group/confirm";


class OrdersPage extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return <OrdersConfirmGroup status={'USER_CONFIRMED'}/>
    }

}

const mapStateToProps = state => {
    return {
        orders: state.app.orders
    }
}

const mapDispatchToProps = dispatch => ({
});

export default connect(mapStateToProps, mapDispatchToProps)(OrdersPage);