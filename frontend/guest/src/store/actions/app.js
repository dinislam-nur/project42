import {pasta, soup} from "../../data/data";

export const LOGIN = "APP/LOGIN";
export const LOGIN_TOKEN = "APP/LOGIN_TOKEN";
export const LOGOUT = "APP/LOGOUT";
export const SET_TABLE = "APP/SET_TABLE";
export const SHOW_ERROR = "APP/SHOW_ERROR";
export const SHOW_SUCCESS = "APP/SHOW_SUCCESS";
export const ADD_DISH_TO_ORDER = "APP/ADD_DISH_TO_ORDER";
export const REMOVE_DISH_FROM_ORDER = "APP/REMOVE_DISH_FROM_ORDER";
export const CHANGE_DISHES = "APP/CHANGE_DISHES";
export const SHOW_LOADER = "APP/SHOW_LOADER";
export const HIDE_LOADER = "APP/HIDE_LOADER";

export const registerAction = (login, password, history) => {
    return async (dispatch) => {
        const response = await fetch('http://localhost:8181/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
            body: JSON.stringify({login, password})
        });
        if (response.ok) {
            dispatch(showSuccess("Пользователь успешно создан"));
            history.push('/login');
        } else {
            const data = response.json();
            if (data.message) {
                dispatch(showError(data.message));
            } else if (data.login) {
                dispatch(showError(data.login))
            } else if (data.password) {
                dispatch(showError(data.password))
            } else {
                dispatch(showError("Произошла ошибка. Попробуйте еще раз."))
            }
        }
    }
}

export const loginAction = (login, password, tableId) => {
    return async (dispatch) => {
        const response = await fetch('http://localhost:8181/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charset=utf-8',
                'TABLE_ID': tableId
            },
            body: JSON.stringify({login, password})
        });

        const data = await response.json();
        console.log(data);
        if (response.ok) {
            console.log(data);
            dispatch(setCredentials(data.user.login, data.token));
        } else {
            showError(data.message);
        }
    }
}

export const loginTokenAction = (token) => {
    return async (dispatch) => {
        const response = await fetch('http://localhost:8181/login_token', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charset=utf-8',
                'JSESSIONID': token
            }
        });
        if (response.ok) {
            const data = await response.json();
            dispatch(setCredentials(data.user.login, data.token));
        } else {
            dispatch(logoutAction());
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
        } else {
            dispatch(showError("Wrong id"));
        }
    }
}

export const logout = () => {
    return async (dispatch) => {
        const response = await fetch('http://localhost:8181/logout', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charset=utf-8',
                'JSESSIONID': localStorage.getItem('token')
            },
        });
    }
}

const logoutAction = () => ({
    type: LOGOUT
})

export const setTableAction = table => ({
    type: SET_TABLE,
    table
})

const setCredentials = (login, token) => {
    return {
        type: LOGIN,
        username: login,
        token
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

export const changeDishes = (category) => {
    return async (dispatch) => {
        switch (category) {
            case "SOUP":
                dispatch({
                    type: CHANGE_DISHES,
                    dishes: soup
                });
                break;
            case "PASTA":
                dispatch({
                    type: CHANGE_DISHES,
                    dishes: pasta
                });
                break;
        }
    }
}

export const showError = message => ({type: SHOW_ERROR, message});

export const showSuccess = message => ({type: SHOW_SUCCESS, message});