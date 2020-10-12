import React, {useCallback, useState} from "react";
import Form from "reactstrap/es/Form";
import FormGroup from "reactstrap/es/FormGroup";
import Label from "reactstrap/es/Label";
import Input from "reactstrap/es/Input";
import ButtonGroup from "reactstrap/es/ButtonGroup";
import {useHistory} from 'react-router-dom';
import Button from "reactstrap/es/Button";
import Alert from "reactstrap/es/Alert";


const RegistrationForm = (props) => {
    const [state, setState] = useState({isPasswordEquals: true, password: ""});
    const history = useHistory();
    const handleOnClick = useCallback(() => history.push('/'), [history]);
    const handlePassCheck = (e) => {
        if (e.target.value === state.password) {
            setState({...state, isPasswordEquals: true});
            props.setPassword(state.password);
        } else {
            setState({...state, isPasswordEquals: false});
        }
    };
    return (
        <Form>
            <FormGroup>
                <Label for="login">Логин</Label>
                <Input type="text" id="login" onChange={props.onLoginChange}/>
            </FormGroup>
            <FormGroup>
                <Label for="password">Пароль</Label>
                <Input type="password" id="password" onChange={(e) => {
                    setState({...state, password: e.target.value})
                }}/>
            </FormGroup>
            <FormGroup>
                <Label for="passwordConfirm">Подтвердите пароль</Label>
                <Input type="password" id="passwordConfirm" onChange={handlePassCheck}/>
            </FormGroup>
            <ButtonGroup>
                <Button color={"primary"} active={!state.isPasswordEquals} onClick={props.onConfirm}>Зарегистрироваться</Button>
                <Button onClick={handleOnClick}>Отмена</Button>
            </ButtonGroup>
            {state.isPasswordEquals ? null :
                <Alert color="danger">
                    Пароли не совпадают
                </Alert>
            }
        </Form>
    )
}

export default RegistrationForm;