import React from "react";
import Card from "reactstrap/es/Card";
import CardBody from "reactstrap/es/CardBody";
import CardTitle from "reactstrap/es/CardTitle";
import CardSubtitle from "reactstrap/es/CardSubtitle";
import {Button} from "@blueprintjs/core";

export const OrdersFooter = (props) => {
    return (
        <Card>
            <CardBody className={'order_dish_card_body '}>
                <div style={{width: "70%", float: "left"}}>
                    <CardTitle><b>Сумма к оплате:</b></CardTitle>
                    <CardSubtitle>{props.total}₽</CardSubtitle>
                </div>
                <Button intent={"success"} style={{width: "30%", float: "right"}} onClick={props.onConfirm}>Подтвердить</Button>
            </CardBody>
        </Card>
    )
}




