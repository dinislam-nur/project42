export const LOGIN = "APP/LOGIN";
export const LOGIN_TOKEN = "APP/LOGIN_TOKEN";
export const LOGOUT = "APP/LOGOUT";
export const SET_TABLE = "APP/SET_TABLE";
export const SET_SESSION = "APP/SET_SESSION";
export const SET_ORDERS_HISTORY = "APP/SET_ORDERS_HISTORY";
export const CLEAR_ORDER = "APP/CLEAR_ORDER";
export const SHOW_ERROR = "APP/SHOW_ERROR";
export const SHOW_SUCCESS = "APP/SHOW_SUCCESS";
export const ADD_DISH_TO_ORDER = "APP/ADD_DISH_TO_ORDER";
export const REMOVE_DISH_FROM_ORDER = "APP/REMOVE_DISH_FROM_ORDER";
export const CHANGE_DISH_PAGE = "APP/CHANGE_DISH_PAGE";
export const CHANGE_CATEGORY = "APP/CHANGE_CATEGORY";
export const SHOW_LOADER = "APP/SHOW_LOADER";
export const HIDE_LOADER = "APP/HIDE_LOADER";
export const LOADED = "APP/LOADED";

const host = "http://limitless-escarpment-67687.herokuapp.com/";


export const registerAction = (login, password, history, tableId) => {
    return async (dispatch) => {
        dispatch(showLoader());
        const response = await fetch('http://localhost:8181/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
            body: JSON.stringify({login, password})
        });
        if (response.ok) {
            dispatch(showSuccess("Пользователь успешно создан"));
            history.push('/' + tableId + '/login');
            dispatch(hideLoader());
        } else {
            const data = await response.text();
            dispatch(hideLoader());
            dispatch(showError(data))
        }
    }
}

export const loginAction = (login, password, tableId) => {
    return async (dispatch) => {
        dispatch(showLoader());
        const response = await fetch('http://localhost:8181/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charset=utf-8',
                'TABLE_ID': tableId
            },
            body: JSON.stringify({login, password})
        });

        const data = await response.json();
        if (response.ok) {
            dispatch(setCredentials(data.user.login, data.token, tableId));
            dispatch({
                type: SET_SESSION,
                session: data
            });
            dispatch(hideLoader());
        } else {
            showError(data.message);
            dispatch(hideLoader());
        }
    }
}

export const anonLogin = (tableId) => {
    return async (dispatch) => {
        dispatch(showLoader());
        const response = await fetch('http://localhost:8181/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charset=utf-8',
                'TABLE_ID': tableId
            }
        });

        const data = await response.json();
        if (response.ok) {
            dispatch(setCredentials(data.user.login, data.token));
            dispatch({
                type: SET_SESSION,
                session: data
            });
            dispatch(hideLoader());
        } else {
            showError(data.message);
            dispatch(hideLoader());
        }
    }
}

export const loginTokenAction = (token) => {
    return async (dispatch) => {
        dispatch(showLoader());
        const response = await fetch('http://localhost:8181/session', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json;charset=utf-8',
                'Authorization': token
            }
        });
        if (response.ok) {
            const data = await response.json();
            dispatch({
                type: LOADED
            });
            dispatch({
                type: SET_SESSION,
                session: data
            });
            dispatch(setCredentials(data.user.login, data.token, data.table.id));
            dispatch(hideLoader());
        } else {
            dispatch({
                type: LOADED
            });
            dispatch(logoutAction());
            dispatch(hideLoader());
        }
    }
}

export const fetchTable = (id) => {
    return async (dispatch) => {
        const response = await fetch('http://localhost:8181/tables/' + id, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            }
        });

        if (response.ok) {
            dispatch(setTableAction(await response.json()));
            dispatch(hideLoader());
        } else {
            dispatch(showError("Wrong id"));
            dispatch(hideLoader());
        }
    }
}

export const logout = () => {
    return async (dispatch) => {
        dispatch(showLoader());
        const response = await fetch('http://localhost:8181/logout', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charset=utf-8',
                'Authorization': localStorage.getItem('token')
            },
        });
        dispatch(logoutAction());
        dispatch(hideLoader());
    }
}

export const fetchOrdersHistory = (pageNumber) => {
    return async (dispatch) => {
        dispatch(showLoader());
        const response = await fetch('http://localhost:8181/orders?size=5&page=' + pageNumber, {
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

const logoutAction = () => ({
    type: LOGOUT
})

export const setTableAction = table => ({
    type: SET_TABLE,
    table
})

const setCredentials = (login, token, tableId) => {
    return {
        type: LOGIN,
        username: login,
        token,
        tableId
    }
}

export const addDishToOrder = (dish) => {
    return {
        type: ADD_DISH_TO_ORDER,
        dish: {
            id: dish.id,
            name: dish.name,
            category: dish.category,
            price: dish.price
        }
    }
}

export const removeDishFromOrder = (dish) => {
    return {
        type: REMOVE_DISH_FROM_ORDER,
        dish
    }
}

export const changeDishes = (category, page) => {
    return async (dispatch) => {
        dispatch({
            type: CHANGE_DISH_PAGE,
            dishPage: null
        });
        dispatch(showLoader());
        const response = await fetch('http://localhost:8181/foods/' + category + '?size=3&page=' + page, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json;charset=utf-8',
                'Authorization': localStorage.getItem('token')
            }
        });
        if (response.ok) {
            dispatch({
                type: CHANGE_DISH_PAGE,
                dishPage: await response.json()
            });
            dispatch(hideLoader());
        } else {
            dispatch(hideLoader());
            showError("Что-то пошло не так");
        }
    }
}

export const changeCategory = (category, history) => {
    return async (dispatch) => {
        await dispatch(changeDishes(category, 0));
        history.push('/menu');
        dispatch({
            type: CHANGE_CATEGORY,
            category
        })
    }
}

export const uploadOrder = (tableId, order) => {
    return async (dispatch) => {
        dispatch(showLoader());
        const body = {
            foodsId: order,
            tableId
        };

        const response = await fetch('http://localhost:8181/orders', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charset=utf-8',
                'Authorization': localStorage.getItem('token')
            },
            body: JSON.stringify(body)
        });

        if (response.ok) {
            dispatch(showSuccess("Заказ успешно отправлен"))
            dispatch(clearOrder());
            dispatch(hideLoader());
        } else {
            dispatch(hideLoader());
            showError("Ошибка при добавлении заказа");
        }
    }
}

const clearOrder = () => {
    return {
        type: CLEAR_ORDER
    }
}

const setOrdersHistory = orders => ({type: SET_ORDERS_HISTORY, orders});

export const showError = message => ({type: SHOW_ERROR, message});

export const showSuccess = message => ({type: SHOW_SUCCESS, message});

const showLoader = () => ({type: SHOW_LOADER});
const hideLoader = () => ({type: HIDE_LOADER});