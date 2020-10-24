export const LOGIN = "APP/LOGIN";
export const LOGIN_TOKEN = "APP/LOGIN_TOKEN";
export const LOGOUT = "APP/LOGOUT";
export const SET_TABLE = "APP/SET_TABLE";
export const SET_SESSION = "APP/SET_SESSION";
export const SHOW_ERROR = "APP/SHOW_ERROR";
export const SHOW_SUCCESS = "APP/SHOW_SUCCESS";
export const ADD_DISH_TO_ORDER = "APP/ADD_DISH_TO_ORDER";
export const REMOVE_DISH_FROM_ORDER = "APP/REMOVE_DISH_FROM_ORDER";
export const CHANGE_DISH_PAGE = "APP/CHANGE_DISH_PAGE";
export const CHANGE_CATEGORY = "APP/CHANGE_CATEGORY";
export const SHOW_LOADER = "APP/SHOW_LOADER";
export const HIDE_LOADER = "APP/HIDE_LOADER";
export const LOADED = "APP/LOADED";
export const NOT_LOADED = "APP/NOT_LOADED";

export const registerAction = (login, password, history, tableId) => {
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
            history.push('/' + tableId + '/login');
        } else {
            const data = await response.text();

            dispatch(showError(data))

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
            dispatch({
                type: SET_SESSION,
                session: data
            })
        } else {
            showError(data.message);
        }
    }
}

export const loginTokenAction = (token) => {
    return async (dispatch) => {
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
            dispatch(setCredentials(data.user.login, data.token));
        } else {
            dispatch({
                type: LOADED
            });
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
                'Authorization': localStorage.getItem('token')
            },
        });
        dispatch(logoutAction());
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

export const changeDishes = (category, page) => {
    return async (dispatch) => {
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
        } else {
            showError("Что-то пошло не так");
        }
    }
}

export const changeCategory = category => ({
    type: CHANGE_CATEGORY,
    category
})

export const showError = message => ({type: SHOW_ERROR, message});

export const showSuccess = message => ({type: SHOW_SUCCESS, message});