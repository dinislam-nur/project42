export const LOGIN = "APP/LOGIN";
export const LOGIN_TOKEN = "APP/LOGIN_TOKEN";
export const LOGOUT = "APP/LOGOUT";
export const SHOW_ERROR = "APP/SHOW_ERROR";

export const loginAction = (login, password) => {
    if (login === "user" && password === "user"){
        return {
            type: LOGIN,
            username: login,
            token: "1234"
        }
    } else {
        return showError("Неверный логин или пароль.")
    }
}

export const loginTokenAction = (token) => ({
    type: LOGIN_TOKEN,
    token: token
})

export const logoutAction = () => ({
    type: LOGOUT
})

export const showError = message => ({type: SHOW_ERROR, message});