import React from "react";
import Dish from "../menu/dish";
import {addDishToOrder, changeDishes} from "../../store/actions/app";
import {connect} from "react-redux";
import {MenuFooter} from "../menu/footer";
import {Loader} from "../app/loader";


class Menu extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            page: 0
        }
    }

    async componentDidMount() {
        if (this.props.page === null) {
            await this.props.loadPage("SOUPS", 0);
        }
    }

    addHandler = (dish) => {
        this.props.addDish(dish);
    };

    prevPageHandler = () => {
        this.props.loadPage(this.props.category, this.state.page - 1);
        this.setState({...this.state, page: this.state.page - 1});
    }

    nextPageHandler = () => {
        this.props.loadPage(this.props.category, this.state.page + 1);
        this.setState({...this.state, page: this.state.page + 1});
    }

    render() {

        let listDishes = null;
        if (this.props.page !== null) {
            listDishes = this.props.page.content.map((dish) => {
                return <Dish key={dish.id} dish={dish} onAdd={this.addHandler}/>;
            });
        }
        return (
            <div style={{paddingTop: "60px"}}>
                {listDishes}
                {this.props.page === null? <Loader/> : <MenuFooter page={this.props.page}
                                                              onNextPage={this.nextPageHandler}
                                                              onPrevPage={this.prevPageHandler}
                />}
            </div>
        )
    }
}


const mapStateToProps = state => (
    {
        page: state.app.dishPage,
        category: state.app.category,
        loaded: state.app.loaded
    }
)


const mapDispatchToProps = dispatch => ({
    addDish: dish => dispatch(addDishToOrder(dish)),
    loadPage: (category, page) => dispatch(changeDishes(category, page))
});

export default connect(mapStateToProps, mapDispatchToProps)(Menu);
