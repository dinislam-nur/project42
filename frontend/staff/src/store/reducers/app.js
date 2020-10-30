import {
    HIDE_LOADER,
    LOGIN,
    LOGIN_TOKEN,
    LOGOUT,
    SET_CONFIRM_ORDERS,
    SET_PREPARING_ORDERS,
    SET_WAITERS_ORDERS,
    SET_SESSION,
    SHOW_ERROR,
    SHOW_LOADER, SET_TABLES
} from "../actions/app";
import {AppToaster} from "../../components/app/toaster";

const initialState = {
    username: "",
    token: "",
    confirmOrders: null,
    preparingOrders: null,
    waitersOrders: null,
    session: null,
    loaded: true,
    tables: null
};

export default (state = initialState, action) => {
    switch (action.type) {
        case LOGIN:
            localStorage.setItem("token", action.token);
            return {...state, username: action.username, token: action.token};
        case LOGIN_TOKEN:
            return {...state, token: action.token};
        case LOGOUT:
            localStorage.removeItem("token");
            return {...state, username: null, token: null};
        case SHOW_ERROR:
            AppToaster.show({message: action.message, intent: "danger"});
            return state;
        case SET_SESSION:
            return {...state, session: action.session};
        case SHOW_LOADER:
            return {...state, loaded: false};
        case HIDE_LOADER:
            return {...state, loaded: true};
        case SET_CONFIRM_ORDERS:
            return {...state, confirmOrders: action.orders};
        case SET_PREPARING_ORDERS:
            return {...state, preparingOrders: action.orders};
        case SET_WAITERS_ORDERS:
            return {...state, waitersOrders: action.orders};
        case SET_TABLES:
            return {...state, tables: action.tables};
        default:
            return state;
    }
}