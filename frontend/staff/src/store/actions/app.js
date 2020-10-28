import {AppToaster} from "../../components/app/toaster";

export const LOGIN = "APP/LOGIN";
export const LOGIN_TOKEN = "APP/LOGIN_TOKEN";
export const LOGOUT = "APP/LOGOUT";
export const SHOW_ERROR = "APP/SHOW_ERROR";
export const SHOW_LOADER = "APP/SHOW_LOADER";
export const HIDE_LOADER = "APP/HIDE_LOADER";
export const SET_SESSION = "APP/SET_SESSION";
export const SET_ORDERS = "APP/SET_ORDERS";

const host = "https://project42db.herokuapp.com";

export const loginAction = (login, password) => {
    return async (dispatch) => {
        dispatch(showLoader());
        const response = await fetch(host + '/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
            body: JSON.stringify({login, password})
        });

        const data = await response.json();
        console.log(data);
        if (response.ok) {
            dispatch(setCredentials(data.user.login, data.token));
            dispatch({
                type: SET_SESSION,
                session: data
            });
            dispatch(hideLoader());
        } else {
            AppToaster.show({message: "Неправильный логин или пароль", intent: "danger"});
            dispatch(hideLoader());
        }
    }
}

export const loginTokenAction = (token) => {
    return async (dispatch) => {
        const response = await fetch(host + '/session', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json;charset=utf-8',
                'Authorization': token
            }
        });
        dispatch(showLoader());
        if (response.ok) {
            const data = await response.json();
            dispatch(hideLoader());
            dispatch(setCredentials(data.user.login, data.token));
            dispatch({
                type: SET_SESSION,
                session: data
            });
            dispatch(hideLoader());
        } else {
            dispatch(hideLoader());
            dispatch(logoutAction());
            dispatch(hideLoader());
        }
    }
}

export const logoutAction = () => ({
    type: LOGOUT
})

export const logout = () => {
    return async (dispatch) => {
        const response = await fetch(host + '/logout', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charset=utf-8',
                'Authorization': localStorage.getItem('token')
            },
        });
        dispatch(logoutAction());
    }
}

export const fetchOrdersForWaiters = () => {
    return async (dispatch) => {
        dispatch(showLoader());
        const response = await fetch(host + '/orders/for_waiters', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json;charset=utf-8',
                'Authorization': localStorage.getItem('token')
            },
        });
        if (response.ok) {
            dispatch(setOrdersHistory(await response.json()));
            dispatch(hideLoader());
        } else {
            dispatch(hideLoader());
            showError("Что-то пошло не так");
        }
    }
}

export const fetchConfirmOrders = (page) => {
    return async (dispatch) => {
        dispatch(showLoader());
        const response = await fetch(host + '/orders?size=9&page=' + page + '&status=USER_CONFIRMED', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json;charset=utf-8',
                'Authorization': localStorage.getItem('token')
            },
        });
        if (response.ok) {
            dispatch(setOrdersHistory(await response.json()));
            dispatch(hideLoader());
        } else {
            dispatch(hideLoader());
            showError("Что-то пошло не так");
        }
    }
}

export const updateOrder = order => {
    return async dispatch => {
        dispatch(showLoader());

        const response = await fetch(host + '/orders/' + order.id, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json;charset=utf-8',
                'Authorization': localStorage.getItem('token')
            },
            body: JSON.stringify(order)
        });
        if (await response.ok) {
            dispatch(fetchConfirmOrders(0));
            dispatch(hideLoader());
        } else {
            dispatch(hideLoader());
            showError("Что-то пошло не так");
        }
    }
}


const setOrdersHistory = (orders) => ({
    type: SET_ORDERS,
    orders
})

export const showError = message => ({type: SHOW_ERROR, message});

const setCredentials = (login, token) => ({
    type: LOGIN,
    username: login,
    token
})

const showLoader = () => ({type: SHOW_LOADER});
const hideLoader = () => ({type: HIDE_LOADER});