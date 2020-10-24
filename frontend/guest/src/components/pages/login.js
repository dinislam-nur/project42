import React from "react";
import LoginForm from "../forms/login";
import {connect} from "react-redux";
import {loginAction} from "../../store/actions/app";
import {Loader} from "../app/loader";
import CardBody from "reactstrap/es/CardBody";
import Card from "reactstrap/es/Card";
import CardHeader from "reactstrap/es/CardHeader";

const initState = {
    login: "",
    password: ""
}

class LoginPage extends React.Component {
    constructor(props) {
        super(props);
        this.state = initState;
    }

    loginChangeHandler = e => {
        this.setState({...this.state, login: e.target.value});
    }

    passwordChangeHandler = e => {
        this.setState({...this.state, password: e.target.value});
    }

    onLoginClick = e => {
        e.preventDefault();
        this.props.login(this.state.login, this.state.password, this.props.table.id)
    };

    render() {
        return (
            <>
                {this.props.loaded ?
                    <div style={{textAlign: "center"}}>
                        <Card>
                            <CardHeader>Ваш стол #{this.props.table.number}</CardHeader>
                            <CardBody>
                                <LoginForm
                                    onLoginChange={this.loginChangeHandler}
                                    onPasswordChange={this.passwordChangeHandler}
                                    onLogin={this.onLoginClick}
                                />
                            </CardBody>
                        </Card>
                    </div>
                    :
                    <Loader/>
                }
            </>

        )
    }
}

const mapDispatchToProps = dispatch => {
    return {
        login: (login, password, tableId) => dispatch(loginAction(login, password, tableId)),
    }
}

export default connect(null, mapDispatchToProps)(LoginPage);