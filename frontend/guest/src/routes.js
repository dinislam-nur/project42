import React, {useEffect, useState} from "react";
import {Redirect, Route, Switch, useHistory} from "react-router-dom";
import LoginPage from "./components/pages/login";
import RegistrationPage from "./components/pages/registration";
import {connect} from "react-redux";
import {changeDishes, fetchTable, loginTokenAction, setTableAction} from "./store/actions/app";
import Menu from "./components/pages/menu";
import OrdersPage from "./components/pages/orders";
import Header from "./components/app/header";
import {FocusStyleManager} from "@blueprintjs/core";
import {Loader} from "./components/app/loader";
import Alert from "reactstrap/es/Alert";

class Routes extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            loaded: false,
            authenticated: false
        }
        FocusStyleManager.onlyShowFocusOnTabs();
    }

    handleCategoryChange = async category => {
        await this.props.changeDishes(category);
        this.props.history.push('/menu')
    };

    async componentDidMount() {
        if (!this.props.token) {
            const token = localStorage.getItem("token");
            if (token !== null && token !== 'null') {
                await this.props.loginToken(token);
            }
        }
    }

    privateRoute = (
        <Switch>
            <Route exact path={'/menu'}>
                <Header setCategory={this.handleCategoryChange} name={"Меню"}/>
                <Menu dishes={this.props.dishes}/>
            </Route>
            <Route exact path={'/orders'}>
                <Header setCategory={this.handleCategoryChange} name={"Заказ"}/>
                <OrdersPage/>
            </Route>
            <Redirect to={'/menu'}/>
        </Switch>
    );


    render() {
        const publicRoute = (
            <Switch>
                <Route path={'/:id/login'} component={ConnectedLoginWrapper}/>
                <Route exact path={'/registration'}>
                    <RegistrationPage/>
                </Route>
                <Route exact path={'/404'}>
                    {this.props.loaded ?
                        <Alert color="danger">
                            Пожалуйста, отсканируйте код на вашем столе
                        </Alert> : <Loader/>}
                </Route>
                <Redirect to={'/404'}/>
            </Switch>
        );
        return (
            <>
                {this.props.token ? this.privateRoute : publicRoute}
            </>
        )
    }


}

class LoginWrapper extends React.Component {
    constructor(props) {
        super(props);
        this.id = props.match.params.id;
    }

    async componentDidMount() {
        await this.props.fetchTable(this.id);
    }

    render() {
        return <>
            {
                this.props.table ?
                    <LoginPage table={this.props.table} loaded={this.props.loaded}/>
                    : <Loader/>
            }
        </>
    }
}

const mapStateToProps = state => ({
    loaded: state.app.loaded,
    token: state.app.token,
    table: state.app.table,
    dishes: state.app.dishes,
});

const mapDispatchToProps = dispatch => ({
    loginToken: (token) => dispatch(loginTokenAction(token)),
    fetchTable: (id) => dispatch(fetchTable(id)),
    setTable: (table) => dispatch(setTableAction(table)),
    changeDishes: category => dispatch(changeDishes(category))
});

export default connect(mapStateToProps, mapDispatchToProps)(Routes);


const ConnectedLoginWrapper = connect(mapStateToProps, mapDispatchToProps)(LoginWrapper);
