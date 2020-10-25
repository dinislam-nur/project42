import React from "react";
import {
    Switch,
    Route
} from "react-router-dom";
import {connect} from "react-redux";
import {Redirect} from "react-router-dom";
import LoginPage from "./components/pages/login";
import {FocusStyleManager} from "@blueprintjs/core";
import Header from "./components/app/header";
import {loginTokenAction} from "./store/actions/app";
import MainPage from "./components/pages/main";
import {Loader} from "./components/app/loader";


class Routes extends React.Component {
    constructor(props) {
        console.log(props);
        super(props);
        FocusStyleManager.onlyShowFocusOnTabs();
    }

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
            <Route exact path={'/'}>
                <Header/>
                <MainPage/>
            </Route>
            <Route exact path={'/settings'}>
                <Header/>
                <h1>Settings</h1>
            </Route>
            <Redirect to={'/'}/>
        </Switch>
    );


    render() {
        const publicRoute = (
            <Switch>
                <Route exact path={'/login'}>
                    {console.log(this.props)}
                    {this.props.loaded ? <LoginPage/> : <Loader/>}
                </Route>
                <Redirect to={'/login'}/>
            </Switch>
        );
        return (
            <>
                {this.props.token ? this.privateRoute : publicRoute}
            </>
        )
    }
}


const mapStateToProps = state => ({token: state.app.token, loaded: state.app.loaded});

const mapDispatchToProps = dispatch => ({
    loginToken: (token) => dispatch(loginTokenAction(token))
});

export default connect(mapStateToProps, mapDispatchToProps)(Routes);