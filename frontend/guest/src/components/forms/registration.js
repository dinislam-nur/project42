import React, {useCallback, useState} from "react";
import "./registration.css";
import Form from "reactstrap/es/Form";
import FormGroup from "reactstrap/es/FormGroup";
import Label from "reactstrap/es/Label";
import Input from "reactstrap/es/Input";
import ButtonGroup from "reactstrap/es/ButtonGroup";
import {useHistory} from 'react-router-dom';
import Button from "reactstrap/es/Button";
import Alert from "reactstrap/es/Alert";
import Card from "reactstrap/es/Card";
import CardBody from "reactstrap/es/CardBody";
import CardHeader from "reactstrap/es/CardHeader";


const RegistrationForm = (props) => {
    const [state, setState] = useState({isPasswordEquals: false, password: ""});
    const history = useHistory();
    const handleOnCancelClick = useCallback(() => history.push('/' + props.tableId + '/login'), [history, props.tableId]);
    const handlePassCheck = (e) => {
        if (e.target.value === state.password) {
            setState({...state, isPasswordEquals: true});
            props.onPasswordChange(e);
        } else {
            setState({...state, isPasswordEquals: false});
        }
    };
    return (
        <Card>
            <CardHeader>Регистрация</CardHeader>
            <CardBody>
                <Form>
                    <FormGroup>
                        <Label for="login">Логин</Label>
                        <Input type="text" id="login" onChange={props.onLoginChange}/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="password">Пароль</Label>
                        <Input type="password" id="password"
                               onChange={(e) => setState({...state, password: e.target.value})}/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="passwordConfirm">Подтвердите пароль</Label>
                        <Input type="password" id="passwordConfirm" onChange={handlePassCheck}/>
                    </FormGroup>
                    <ButtonGroup>
                        <Button color={"primary"} active={!state.isPasswordEquals}
                                onClick={props.onConfirm}>Зарегистрироваться</Button>
                        <Button onClick={handleOnCancelClick}>Отмена</Button>
                    </ButtonGroup>
                    {state.isPasswordEquals ? null :
                        <Alert color="danger" className={"registration_alert"}>
                            Пароли не совпадают
                        </Alert>
                    }
                </Form>
            </CardBody>
        </Card>
    )
}

export default RegistrationForm;