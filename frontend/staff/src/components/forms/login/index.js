import React, {useState} from "react";
import {Button, ButtonGroup, FormGroup, InputGroup} from "@blueprintjs/core";


const LoginForm = ({onLogin, onLoginChange, onPasswordChange}) => {

    const [state, setState] = useState({isPasswordShow: false});

    const lockClickHandler = e => setState({isPasswordShow: !state.isPasswordShow});

    const lock = <Button icon={state.isPasswordShow ? "unlock" : "lock"} onMouseDown={lockClickHandler}
                         onMouseUp={lockClickHandler} minimal={true} tabIndex={-1}/>;

    return (
        <div className={"login-form"}>
            <FormGroup label={"Логин"}>
                <InputGroup id={"login"} title={"Логин"} leftIcon={"user"} onChange={onLoginChange}/>
            </FormGroup>
            <FormGroup label={"Пароль"}>
                <InputGroup id={"password"} title={"Пароль"} type={state.isPasswordShow ? "text" : "password"}
                            leftElement={lock} onChange={onPasswordChange}/>
            </FormGroup>
            <ButtonGroup>
                <Button id={"login"} text={"Войти"} onClick={onLogin} intent={"success"} />
            </ButtonGroup>
        </div>
    )
}


export default LoginForm;