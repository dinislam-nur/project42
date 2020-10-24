import {
    ADD_DISH_TO_ORDER, CHANGE_CATEGORY,
    CHANGE_DISH_PAGE, HIDE_LOADER, LOADED,
    LOGIN,
    LOGIN_TOKEN,
    LOGOUT,
    REMOVE_DISH_FROM_ORDER, SET_ORDERS_HISTORY, SET_SESSION,
    SET_TABLE,
    SHOW_ERROR, SHOW_LOADER,
    SHOW_SUCCESS
} from "../actions/app";
import {AppToaster} from "../../components/app/toaster";

const initialState = {
    username: null,
    token: null,
    table: undefined,
    dishPage: null,
    category: "SOUPS",
    ordersHistory: [],
    session: {},
    loaded: false,
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
            localStorage.removeItem("order");
            return {...state, username: null, token: null};
        case SET_TABLE:
            return {...state, table: action.table};
        case SHOW_ERROR:
            AppToaster.show({message: action.message, intent: "danger"});
            return state;
        case SHOW_SUCCESS:
            AppToaster.show({message: action.message, intent: "success"});
            return state;
        case SHOW_LOADER:
            return {...state, loaded: false};
        case HIDE_LOADER:
            return {...state, loaded: true};
        case CHANGE_CATEGORY:
            return {...state, category: action.category};
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
        case CHANGE_DISH_PAGE:
            return {...state, dishPage: action.dishPage};
        case SET_SESSION:
            return {...state, session: action.session};
        case SET_ORDERS_HISTORY:
            return {...state, ordersHistory: action.orders};
        case LOADED:
            return {...state, loaded: true};
        default:
            return state;
    }
}