import {LOGIN, LOGIN_TOKEN, LOGOUT, SET_TABLE, SHOW_ERROR, SHOW_SUCCESS} from "../actions/app";
import {AppToaster} from "../../components/app/toaster";

const initialState = {
    username: null,
    token: null,
    table: undefined
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
            localStorage.setItem("token", null);
            return {...state, username: null, token: null};
        case SET_TABLE:
            return {...state, table: action.table};
        case SHOW_ERROR:
            AppToaster.show({message: action.message, intent: "danger"});
            return state;
        case SHOW_SUCCESS:
            AppToaster.show({message: action.message, intent: "success"});
            return state;
        default:
            return state;
    }
}