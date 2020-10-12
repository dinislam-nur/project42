import React from "react";
import LoginForm from "../../forms/login";

const initState = {
    username:"",
    password:""
}

class LoginPage extends React.Component {
    constructor(props) {
        super(props);
        this.state = initState;
    }

    loginChangeHandler = e => this.setState({...this.state, username: e.target.value});

    passwordChangeHandler = e => this.setState({...this.state, password: e.target.value});

    loginHandler = () => console.log(this.state);

    render() {
        return (
            <div>
                <LoginForm onLoginChange={this.loginChangeHandler} onPasswordChange={this.passwordChangeHandler}
                           onLogin={this.loginHandler}/>
            </div>
        )
    }
}


export default LoginPage;