import React from "react";
import {Link} from "react-router-dom";
import Button from "reactstrap/es/Button";
import {connect} from "react-redux";
import {loginTokenAction, logoutAction} from "../../store/actions/app";


class HomePage extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return(
            <div>
                <h1>Ваш столик #{this.props.table}</h1>
                <Link to={"/login"}>
                    <Button color={"warning"}>Войти как пользователь</Button>
                </Link>
                <Link to={"/menu"}>
                    <Button onClick={this.props.loginToken}>Войти</Button>
                </Link>

            </div>
        )
    }
}
const mapStateToProps = state => ({table: state.app.table});

const mapDispatchToProps = dispatch => ({
    loginToken: () => dispatch(loginTokenAction("1234"))
});

export default connect(mapStateToProps, mapDispatchToProps)(HomePage);
