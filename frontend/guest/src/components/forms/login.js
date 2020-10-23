import React, {useCallback} from "react";
import Form from "reactstrap/es/Form";
import FormGroup from "reactstrap/es/FormGroup";
import Label from "reactstrap/es/Label";
import Input from "reactstrap/es/Input";
import ButtonGroup from "reactstrap/es/ButtonGroup";
import {useHistory} from "react-router-dom";
import Button from "reactstrap/es/Button";


const LoginForm = (props) => {
    const history = useHistory();
    const handleOnRegistrationClick = useCallback(() => history.push('/registration'), [history]);
    return (
        <Form>
            <FormGroup>
                <Label for="login">Логин</Label>
                <Input type="text" name="login" id="login" onChange={props.onLoginChange}/>
            </FormGroup>
            <FormGroup>
                <Label for="password">Пароль</Label>
                <Input type="password" name="password" id="password"
                       onChange={props.onPasswordChange}/>
            </FormGroup>
            <ButtonGroup>
                <Button color={"primary"} onClick={props.onLogin}>Войти</Button>
                <Button onClick={handleOnRegistrationClick}>Регистрация</Button>
            </ButtonGroup>
        </Form>
    )
}

export default LoginForm;