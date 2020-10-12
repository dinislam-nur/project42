import React from "react";
import Dish from "../menu/dish";
import Header from "../app/header";


class Menu extends React.Component {
    constructor(props) {
        super(props);
        this.state = {category: "SOUP"};
    }

    addHandler = (dish) => console.log(dish);

    handle = category => this.setState({...this.state, category});

    render() {
        const listDishes = this.props.dishes.map((dish) => {
            if (dish.type === this.state.category) {
                return <Dish key={dish.id} dish={dish} onAdd={this.addHandler}/>;
            } else return null;
        });
        return (
            <>
                <Header setCategory={this.handle}/>
                <div style={{paddingTop: "60px"}}>
                    {listDishes}
                </div>
            </>
        )
    }
}

export default Menu;
