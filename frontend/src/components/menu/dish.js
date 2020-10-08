import React, {useState} from "react";
import Card from "reactstrap/es/Card";
import CardImg from "reactstrap/es/CardImg";
import CardBody from "reactstrap/es/CardBody";
import CardTitle from "reactstrap/es/CardTitle";
import CardSubtitle from "reactstrap/es/CardSubtitle";
import CardText from "reactstrap/es/CardText";
import Button from "reactstrap/es/Button";
import Quantity from "../util/quantity";


const Dish = (props) => {

    const [quantity, setQuantity] = useState(1);
    const quantityChangeHandler = (quantity) => setQuantity(quantity)
    const addButtonHandler = () => props.onAdd({...props.dish, quantity});

    return (
        <div className={'dish_card'}>
            <Card>
                <CardImg top className={'dish_pic'} src={props.dish.picture} alt="Card image cap"/>
                <CardBody className={'dish_card_body '}>
                    <CardTitle><b>{props.dish.name}</b></CardTitle>
                    <CardSubtitle>{props.dish.price}₽</CardSubtitle>
                    <CardText>{props.dish.composition}</CardText>
                    <div>
                        <Quantity onQuantityChange={quantityChangeHandler}/>
                        <Button onClick={addButtonHandler}>Добавить</Button>
                    </div>
                </CardBody>
            </Card>
        </div>
    )
}

export default Dish;