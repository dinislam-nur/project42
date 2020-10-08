import React from "react";
import LoginForm from "../forms/login";

const initState = {
    login: "",
    password: ""
}

class LoginPage extends React.Component {
    constructor(props) {
        super(props, initState);
    }

    loginChangeHandler = (e) => {
        this.setState({...this.state, login: e.target.value});
    }

    passwordChangeHandler = (e) => {
        this.setState({...this.state, password: e.target.value});
    }

    render() {
        return (
            <div>
                <LoginForm onLoginChange={this.loginChangeHandler} onPasswordChange={this.passwordChangeHandler}/>
            </div>
        )
    }
}

export default LoginPage;