import {
    ADD_DISH_TO_ORDER,
    CHANGE_DISHES,
    LOGIN,
    LOGIN_TOKEN,
    LOGOUT,
    REMOVE_DISH_FROM_ORDER,
    SET_TABLE,
    SHOW_ERROR,
    SHOW_SUCCESS
} from "../actions/app";
import {AppToaster} from "../../components/app/toaster";
import {soup} from "../../data/data";

const initialState = {
    username: null,
    token: null,
    table: undefined,
    dishes: soup,
    order: localStorage.getItem("order") ? JSON.parse(localStorage.getItem("order")) :
        {
            total: 0,
            foods: []
        }
};

export default (state = initialState, action) => {
    switch (action.type) {
        case LOGIN:
            localStorage.setItem("token", action.token);
            console.log(action)
            return {...state, username: action.username, token: action.token};
        case LOGIN_TOKEN:
            return {...state, token: action.token};
        case LOGOUT:
            localStorage.removeItem("token");
            return {...state, username: null, token: null};
        case SET_TABLE:
            return {...state, table: action.table};
        case SHOW_ERROR:
            AppToaster.show({message: action.message, intent: "danger"});
            return state;
        case SHOW_SUCCESS:
            AppToaster.show({message: action.message, intent: "success"});
            return state;
        case ADD_DISH_TO_ORDER:
            const order = JSON.parse(localStorage.getItem("order"));
            const total = state.order.total + action.dish.price;
            if (order) {
                localStorage.setItem("order", JSON.stringify({
                    ...state.order,
                    total,
                    foods: [...state.order.foods, action.dish]
                }));
            } else {
                localStorage.setItem("order", JSON.stringify({total, foods: [action.dish]}));
            }
            return {...state, order: {total, foods: [...state.order.foods, action.dish]}};
        case REMOVE_DISH_FROM_ORDER:
            const ord = JSON.parse(localStorage.getItem("order"));
            for (let i = 0; i < ord.foods.length; i++) {
                if (ord.foods[i].id === action.dish.id) {
                    ord.foods.splice(i, 1);
                    break;
                }
            }
            let tot = 0;
            ord.foods.forEach(food => tot = tot + food.price);
            localStorage.setItem("order", JSON.stringify({
                ...state.order,
                total: tot,
                foods: ord.foods
            }));
            return {...state, order: {total: tot, foods: ord.foods}};
        case CHANGE_DISHES:
            return {...state, dishes: action.dishes};
        default:
            return state;
    }
}