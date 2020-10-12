import React, {useState} from "react";
import {

    Switch,
    Route,
    useParams
} from "react-router-dom";
import HomePage from "./components/pages/home";
import LoginPage from "./components/pages/login";
import RegistrationPage from "./components/pages/registration";
import Menu from "./components/pages/menu";
import {dishes} from "./data/data";
import {connect, useDispatch} from "react-redux";
import {loginTokenAction, setTableAction} from "./store/actions/app";
import {Redirect} from "react-router-dom";

const Routes = (props) => {

    const tokenCheck = () => {
        if (!props.token) {
            const token = localStorage.getItem("token");
            if (token) {
                props.loginToken(token);
            }
        }
        return props.token === "1234";
    }

    const privateRoute = (
        <Switch>
            <Route exact path={'/menu'}>
                <Menu dishes={dishes}/>
            </Route>
            <Redirect to={'/menu'}/>
        </Switch>
    );

    const publicRoute = (
        <Switch>

            <Route exact path={'/login'}>
                <LoginPage/>
            </Route>
            <Route exact path={'/registration'}>
                <RegistrationPage/>
            </Route>
            <Redirect to={'/login'}/>
        </Switch>
    );
    return (
        <Switch>
            <Route path={'/tables/:id'}>
                <HomeWrapper/>
            </Route>
            {tokenCheck() ? privateRoute : publicRoute}
        </Switch>
    );
}

const HomeWrapper = () => {
    const {id} = useParams();
    const dispatch = useDispatch();
    dispatch(setTableAction(id));
    return <HomePage/>
}


const mapStateToProps = state => ({token: state.app.token, table: state.app.table});

const mapDispatchToProps = dispatch => ({
    loginToken: (token) => dispatch(loginTokenAction(token))
});

export default connect(mapStateToProps, mapDispatchToProps)(Routes);
