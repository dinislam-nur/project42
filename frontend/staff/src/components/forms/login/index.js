import React, {useState} from "react";
import {Button, ButtonGroup, FormGroup, InputGroup} from "@blueprintjs/core";


const LoginForm = ({onLogin, onLoginChange, onPasswordChange}) => {

    const [state, setState] = useState({isPasswordShow: false});

    const lockClickHandler = e => setState({isPasswordShow: !state.isPasswordShow});

    const lock = <Button icon={state.isPasswordShow ? "unlock" : "lock"} onClick={lockClickHandler}/>;

    return (
        <FormGroup>
            <InputGroup id={"login"} name={"Логин"} leftIcon={"user"} onChange={onLoginChange}/>
            <InputGroup id={"password"} name={"Пароль"} type={state.isPasswordShow ? "text" : "password"}
                        leftElement={lock} onChange={onPasswordChange}/>
            <ButtonGroup>
                <Button id={"login"} placeholder={"Войти"} onClick={onLogin} intent={"success"}/>
            </ButtonGroup>
        </FormGroup>
    )
}


export default LoginForm;