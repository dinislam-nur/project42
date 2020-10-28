import React from "react";
import LoginForm from "../../forms/login";
import {connect} from "react-redux";
import {loginAction} from "../../../store/actions/app";
import {Loader} from "../../app/loader";

const initState = {
    username: "",
    password: ""
}

class LoginPage extends React.Component {
    constructor(props) {
        super(props);
        this.state = initState;
    }

    loginChangeHandler = e => this.setState({...this.state, username: e.target.value});

    passwordChangeHandler = e => this.setState({...this.state, password: e.target.value});

    onLoginSubmit = () => this.props.login(this.state.username, this.state.password)

    render() {
        return (
            <div className={"layout"}>
                {this.props.loaded ?
                    <LoginForm onLoginChange={this.loginChangeHandler} onPasswordChange={this.passwordChangeHandler}
                               onLogin={this.onLoginSubmit} type={"submit"}/> :
                    <Loader/>
                }
            </div>
        )
    }
}

const mapStateToProps = state => {
    return {
        orders: state.app.loaded
    }
}

const mapDispatchToProps = dispatch => {
    return {
        login: (login, password) => dispatch(loginAction(login, password))
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(LoginPage);