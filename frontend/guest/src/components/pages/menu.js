import React from "react";
import Dish from "../menu/dish";
import {addDishToOrder} from "../../store/actions/app";
import {connect} from "react-redux";

class Menu extends React.Component {
    constructor(props) {
        super(props);
    }

    addHandler = (dish) => {
        this.props.addDish(dish);
    };

    render() {
        const listDishes = this.props.dishes.map((dish) => {
            console.log(dish)
            return <Dish key={dish.id} dish={dish} onAdd={this.addHandler}/>;
        });
        return (
            <div style={{paddingTop: "60px"}}>
                {listDishes} {console.log("props: " + this.props)}
            </div>
        )
    }
}


const mapStateToProps = state => (
    {}
)


const mapDispatchToProps = dispatch => ({
    addDish: dish => dispatch(addDishToOrder(dish))
});

export default connect(mapStateToProps, mapDispatchToProps)(Menu);
