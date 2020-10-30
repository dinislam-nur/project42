import React from "react";
import {Button, ButtonGroup, FormGroup, InputGroup} from "@blueprintjs/core";
import Card from "reactstrap/es/Card";
import CardBody from "reactstrap/es/CardBody";
import {connect} from "react-redux";

class LoginForm extends React.Component {
    constructor(props) {
        super(props);
        this.state = {isPasswordShow: false};
    }

    lockClickHandler = e => this.setState({isPasswordShow: !this.state.isPasswordShow});

    render() {

        const lock = <Button icon={this.state.isPasswordShow ? "unlock" : "lock"} onMouseDown={this.lockClickHandler}
                             onMouseUp={this.lockClickHandler} minimal={true} tabIndex={-1}/>;
        const button = <Button id={"login"} text={"Войти"} onClick={this.props.onLogin} intent={"success"}
                               loading={!this.props.loaded}/>;

        return (
            <div className={"centre"}>
                <Card>
                    <CardBody>
                        <FormGroup label={"Логин"}>
                            <InputGroup id={"login"} title={"Логин"} leftIcon={"user"}
                                        onChange={this.props.onLoginChange}/>
                        </FormGroup>
                        <FormGroup label={"Пароль"}>
                            <InputGroup id={"password"} title={"Пароль"}
                                        type={this.state.isPasswordShow ? "text" : "password"}
                                        leftElement={lock} onChange={this.props.onPasswordChange}/>
                        </FormGroup>
                        <ButtonGroup>
                            {button}
                        </ButtonGroup>
                    </CardBody>
                </Card>
            </div>
        )
    }
}

/*
const LoginForm = ({onLogin, onLoginChange, onPasswordChange}) => {

    const [state, setState] = useState({isPasswordShow: false});

    const lockClickHandler = e => setState({isPasswordShow: !state.isPasswordShow});

    const lock = <Button icon={state.isPasswordShow ? "unlock" : "lock"} onMouseDown={lockClickHandler}
                         onMouseUp={lockClickHandler} minimal={true} tabIndex={-1}/>;

    return (
        <div className={"centre"}>
            <Card>
                <CardBody>
                    <FormGroup label={"Логин"}>
                        <InputGroup id={"login"} title={"Логин"} leftIcon={"user"} onChange={onLoginChange}/>
                    </FormGroup>
                    <FormGroup label={"Пароль"}>
                        <InputGroup id={"password"} title={"Пароль"} type={state.isPasswordShow ? "text" : "password"}
                                    leftElement={lock} onChange={onPasswordChange}/>
                    </FormGroup>
                    <ButtonGroup>
                        {button}
                    </ButtonGroup>
                </CardBody>
            </Card>
        </div>
    )
}
*/
const mapStateToProps = state => ({loaded: state.app.loaded});

export default connect(mapStateToProps, null)(LoginForm);