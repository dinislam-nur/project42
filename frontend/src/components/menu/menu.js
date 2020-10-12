import React from "react";
import Dish from "./dish";

const dishes = [
    {
        id: 1,
        name: "Борщ",
        picture: "http://beardychef.com/wp-content/uploads/2017/03/%D0%91%D0%B5%D0%B7-%D0%B8%D0%BC%D0%B5%D0%BD%D0%B8-1-400x300.jpg",
        price: 200,
        composition: "Ингредиенты борща"
    },
    {
        id: 2,
        name: "Щи",
        picture: "https://blog-food.ru/images/site/recipes/first-dishes/0280-schi/07.jpg",
        price: 150,
        composition: "Ингредиенты щей"
    },
    {
        id: 3,
        name: "Паста",
        picture: "https://eda.ru/img/eda/c620x415i/s2.eda.ru/StaticContent/Photos/150525210126/150601174518/p_O.jpg",
        price: 250,
        composition: "Ингредиенты пасты"
    }
]

class Menu extends React.Component {
    constructor(props) {
        super(props);
    }

    addHandler = (dish) => console.log(dish);

    render() {
        return <div>
            <Dish dish={dishes[0]} onAdd={this.addHandler}/>
            <Dish dish={dishes[1]} onAdd={this.addHandler}/>
            <Dish dish={dishes[2]} onAdd={this.addHandler}/>
        </div>
    }
}

export default Menu;