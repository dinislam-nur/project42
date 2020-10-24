import React from "react";
import RegistrationForm from "../forms/registration";
import {registerAction} from "../../store/actions/app";
import {connect} from "react-redux";
import {withRouter} from "react-router";

const initState = {
    login: "",
    password: ""
}

class RegistrationPage extends React.Component {
    constructor(props) {
        super(props);
        this.state = initState;
    }

    loginChangeHandler = e => this.setState({...this.state, login: e.target.value});
    passwordChangeHandler = e => this.setState({...this.state, password: e.target.value});

    confirmHandler = () => this.props.register(this.state.login, this.state.password, this.props.history, this.props.table.id);

    render() {
        if (this.props.table === null) {
            this.props.history.push('/404');
            return null;
        } else {
            return (
                <div>
                    <RegistrationForm onConfirm={this.confirmHandler}
                                      onLoginChange={this.loginChangeHandler}
                                      onPasswordChange={this.passwordChangeHandler}
                                      tableId={this.props.table.id}
                    />
                </div>
            )
        }
    }
}

const mapStatePropsToProps = state => ({table: state.app.table});

const mapDispatchToProps = dispatch => {
    return {
        register: (login, password, history, tableId) => dispatch(registerAction(login, password, history, tableId))
    }
}

export default connect(mapStatePropsToProps, mapDispatchToProps)(withRouter(RegistrationPage));
