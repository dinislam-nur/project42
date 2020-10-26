import React, {useState} from "react";
import Card from "reactstrap/es/Card";
import CardImg from "reactstrap/es/CardImg";
import CardBody from "reactstrap/es/CardBody";
import CardTitle from "reactstrap/es/CardTitle";
import CardSubtitle from "reactstrap/es/CardSubtitle";
import Button from "reactstrap/es/Button";

const Dish = (props) => {

    const [quantity, setQuantity] = useState(1);
    const addButtonHandler = () => props.onAdd({...props.dish, quantity});

    return (
        <div className={'card_layout'}>
            <Card>
                <CardImg top className={'dish_pic'} src={props.dish.picture} alt="Card image cap"/>
                <CardBody>
                    <CardTitle style={{alignContent:"left"}}><b>{props.dish.name}</b></CardTitle>
                    <CardSubtitle>{props.dish.price}₽</CardSubtitle>
                    <Button className={"centre"} style={{marginTop:"20px"}} onClick={addButtonHandler}>Добавить</Button>
                </CardBody>
            </Card>
        </div>
    )
}

export default Dish;