import React from "react";
import {Callout} from "@blueprintjs/core";

const Order = ({order}) => {

    const intent = order => {
        switch (order.status) {
            case "DONE":
                return "success";
            case "WAITING":
                return "warning";
            case "PREPARING":
                return "primary";
            default:
                return "primary";
        }
    }

    return (
        <div className={"order-callout"}>
            <Callout title={"Заказ №" + order.number} intent={intent(order)}>
                <b>Стол №{order.table}</b>
            </Callout>
        </div>
    )
}

export default Order;