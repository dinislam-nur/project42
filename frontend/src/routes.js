import React, {useState} from "react";
import {
    BrowserRouter as Router,
    Switch,
    Route,
    Link,
    useParams
} from "react-router-dom";
import HomePage from "./components/pages/home";
import LoginPage from "./components/pages/login";
import RegistrationPage from "./components/pages/registration";
import Menu from "./components/menu/menu";

const Routes = () => {

    return (
        <Switch>
            <Route path={'/tables/:id'}>
                <HomeWrapper/>
            </Route>
            <Route exact path={'/login'}>
                <LoginPage/>
            </Route>
            <Route exact path={'/registration'}>
                <RegistrationPage/>
            </Route>
            <Route exact path={'/menu'}>
                <Menu/>
            </Route>
        </Switch>
    )
}

const HomeWrapper = () => {
    const {id} = useParams();
    return <HomePage table={id}/>
}

export default Routes;