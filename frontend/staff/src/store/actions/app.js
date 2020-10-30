import {AppToaster} from "../../components/app/toaster";

export const LOGIN = "APP/LOGIN";
export const LOGIN_TOKEN = "APP/LOGIN_TOKEN";
export const LOGOUT = "APP/LOGOUT";
export const SHOW_ERROR = "APP/SHOW_ERROR";
export const SHOW_LOADER = "APP/SHOW_LOADER";
export const HIDE_LOADER = "APP/HIDE_LOADER";
export const SET_SESSION = "APP/SET_SESSION";
export const SET_CONFIRM_ORDERS = "APP/SET_CONFIRM_ORDERS";
export const SET_PREPARING_ORDERS = "APP/SET_PREPARING_ORDERS";
export const SET_WAITERS_ORDERS = "APP/SET_WAITERS_ORDERS";
export const SET_TABLES = "APP/SET_TABLES";

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


export const fetchOrders = (status, page, size) => {
    return async (dispatch) => {
        dispatch(showLoader());
        const response = await fetch(host + '/orders?size=' + size + '&page=' + page + '&status=' + status, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json;charset=utf-8',
                'Authorization': localStorage.getItem('token')
            },
        });
        if (response.ok) {
            switch (status) {
                case "PREPARING":
                    dispatch(setPreparingOrders(await response.json()));
                    break;
                case "USER_CONFIRMED":
                    dispatch(setConfirmOrders(await response.json()));
                    break;
                case "DONE":
                    dispatch(
                        {
                            type: SET_WAITERS_ORDERS,
                            orders: await response.json()
                        }
                    );
                    break;
            }
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
        if (response.ok) {
            dispatch(fetchOrders('PREPARING', 0, 9));
            dispatch(fetchOrders('USER_CONFIRMED', 0, 9));
            dispatch(hideLoader());
        } else {
            dispatch(hideLoader());
            showError("Что-то пошло не так");
        }
    }
}

export const updateWaitersOrder = order => {
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
        if (response.ok) {
            dispatch(fetchOrders('DONE', 0, 9));
            dispatch(hideLoader());
        } else {
            dispatch(hideLoader());
            showError("Что-то пошло не так");
        }
    }
}

export const fetchOpenTables = () => {
    return async dispatch => {
        dispatch(showLoader());
        const response = await fetch(host + '/tables?status=RESERVED', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json;charset=utf-8',
                'Authorization': localStorage.getItem('token')
            }
        });
        if (response.ok) {
            dispatch(
                {
                    type: SET_TABLES,
                    tables: await response.json()
                }
            )
            dispatch(hideLoader());
        } else {
            dispatch(hideLoader());
            showError("Что-то пошло не так");
        }
    }
}

export const closeTable = table => {
    return async dispatch => {
        dispatch(showLoader());
        table.status = 'NOT_RESERVED';
        const response = await fetch(host + '/tables/' + table.id, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json;charset=utf-8',
                'Authorization': localStorage.getItem('token')
            },
            body: JSON.stringify(table)
        });
        if (response.ok) {
            dispatch(fetchOpenTables());
            dispatch(hideLoader());
        } else {
            dispatch(hideLoader());
            showError("Что-то пошло не так");
        }
    }
}


const setConfirmOrders = (orders) => ({
    type: SET_CONFIRM_ORDERS,
    orders
})
const setPreparingOrders = (orders) => ({
    type: SET_PREPARING_ORDERS,
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