import React, {useCallback} from "react";
import {
    Switch,
    Route
} from "react-router-dom";
import {connect, useDispatch} from "react-redux";
import {Redirect} from "react-router-dom";
import LoginPage from "./components/pages/login";
import { FocusStyleManager } from "@blueprintjs/core";
import Header from "./components/app/header";
import {loginTokenAction} from "./store/actions/app";
import {useHistory} from 'react-router-dom';
import MainPage from "./components/pages/main";



const Routes = ({token, loginToken}) => {

    FocusStyleManager.onlyShowFocusOnTabs();
    const tokenCheck = () => {
        if (!token) {
            const token = localStorage.getItem("token");
            if (token) {
                loginToken(token);
            }
        }
        return token === "1234";
    }

    const history = useHistory();
    const handleSettingsClick = useCallback(() => history.push('/settings'), [history]);
    const handleHomeClick = useCallback(() => history.push('/'), [history]);

    const publicRoute = (
        <Switch>
            <Route exact path={'/login'}>
                <LoginPage/>
            </Route>
            <Redirect to={'/login'}/>
        </Switch>
    );

    const privateRoute = (
        <div>
            <Header onSettingsClick={handleSettingsClick} onHomeClick={handleHomeClick}/>
            <Switch>
                <Route exact path={'/'}>
                    <MainPage/>
                </Route>
                <Route exact path={'/settings'}>
                    <h1>Settings</h1>
                </Route>
                <Redirect to={'/'}/>
            </Switch>
        </div>
    )
    return (
        <>
        {tokenCheck()? privateRoute : publicRoute}
        </>
    );
}

const mapStateToProps = state => ({token: state.app.token});

const mapDispatchToProps = dispatch => ({
    loginToken: (token) => dispatch(loginTokenAction(token))
});

export default connect(mapStateToProps, mapDispatchToProps)(Routes);