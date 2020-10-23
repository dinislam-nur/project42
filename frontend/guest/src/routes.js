import React from "react";
import {Redirect, Route, Switch, useHistory} from "react-router-dom";
import LoginPage from "./components/pages/login";
import RegistrationPage from "./components/pages/registration";
import {connect} from "react-redux";
import {changeDishes, fetchTable, loginTokenAction, setTableAction} from "./store/actions/app";
import Menu from "./components/pages/menu";
import OrdersPage from "./components/pages/orders";
import Header from "./components/app/header";
import {FocusStyleManager} from "@blueprintjs/core";

const Routes = (props) => {

    const tokenCheck = async () => {
        if (!props.token) {
            const token = localStorage.getItem("token");
            if (token !== null && token !== 'null') {
                await props.loginToken(token);
            }
        }
        return await props.token !== null;
    }
    FocusStyleManager.onlyShowFocusOnTabs();
    const history = useHistory();

    const handleCategoryChange = async category => {
        await props.changeDishes(category);
        history.push('/menu')
    };

    return (
        <>
            {tokenCheck() ?
                <Switch>
                    <Route exact path={'/menu'}>
                        <Header setCategory={handleCategoryChange} name={"Меню"}/>
                        <Menu dishes={props.dishes}/>
                    </Route>
                    <Route exact path={'/orders'}>
                        <Header setCategory={handleCategoryChange} name={"Заказ"}/>
                        <OrdersPage/>
                    </Route>
                    <Redirect to={'/menu'}/>
                </Switch>
                :
                <Switch>
                    <Route path={'/:id/login'} component={ConnectedLoginWrapper}/>
                    <Route exact path={'/registration'}>
                        <RegistrationPage/>
                    </Route>
                    <Route exact path={'/404'}>
                        <h1>Пожалуйста, отсканируйте код на вашем столе</h1>
                    </Route>
                    <Redirect to={'/404'}/>
                </Switch>
            }
        </>
    );
}

class LoginWrapper extends React.Component {
    constructor(props) {
        super(props);
        this.id = props.match.params.id;
        console.log(this.id);
    }

    async componentDidMount() {
        await this.props.fetchTable(this.id);
    }

    render() {
        return <>
            {this.props.table ?
                <LoginPage table={this.props.table}/>
                : null
            }
        </>
    }
}

const mapStateToProps = state => ({token: state.app.token, table: state.app.table, dishes: state.app.dishes});

const mapDispatchToProps = dispatch => ({
    loginToken: (token) => dispatch(loginTokenAction(token)),
    fetchTable: (id) => dispatch(fetchTable(id)),
    setTable: (table) => dispatch(setTableAction(table)),
    changeDishes: category => dispatch(changeDishes(category))
});

export default connect(mapStateToProps, mapDispatchToProps)(Routes);


const ConnectedLoginWrapper = connect(mapStateToProps, mapDispatchToProps)(LoginWrapper);
