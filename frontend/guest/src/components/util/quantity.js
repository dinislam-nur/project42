import React, {useState} from "react";
import ButtonGroup from "reactstrap/es/ButtonGroup";
import Button from "reactstrap/es/Button";


const Quantity = (props) => {
    const [quantity, setQuantity] = useState(1);
    const increase = () => {
        if ((quantity + 1) <= 10) {
            setQuantity(quantity + 1);
            props.onQuantityChange(quantity);
        }
    };

    const decrease = () => {
        if ((quantity - 1) >= 1) {
            setQuantity(quantity - 1);
            props.onQuantityChange(quantity);
        }
    };
    return (
        <div className={'quantity'}>
            <ButtonGroup className={'quantity'}>
                <Button outline color={'secondary'} onClick={decrease} size={'sm'}>-</Button>
                <b className={'quantity'}>{quantity}</b>
                <Button outline color={'secondary'} onClick={increase} size={'sm'}>+</Button>
            </ButtonGroup>
        </div>
    );
}

export default Quantity;