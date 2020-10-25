export const LOGIN = "APP/LOGIN";
export const LOGIN_TOKEN = "APP/LOGIN_TOKEN";
export const LOGOUT = "APP/LOGOUT";
export const SHOW_ERROR = "APP/SHOW_ERROR";
export const SHOW_LOADER = "APP/SHOW_LOADER";
export const HIDE_LOADER = "APP/HIDE_LOADER";
export const SET_SESSION = "APP/SET_SESSION";

export const loginAction = (login, password) => {
    return async (dispatch) => {
        const response = await fetch('http://localhost:8181/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
            body: JSON.stringify({login, password})
        });
        dispatch(showLoader());

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
            showError(data.message);
            dispatch(hideLoader());
        }
    }
}

export const loginTokenAction =  (token) => {
    return async (dispatch) => {
        const response = await fetch('http://localhost:8181/session', {
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

export const showError = message => ({type: SHOW_ERROR, message});

const setCredentials = (login, token) => ({
    type: LOGIN,
    username:login,
    token
})

const showLoader = () => ({type: SHOW_LOADER});
const hideLoader = () => ({type: HIDE_LOADER});