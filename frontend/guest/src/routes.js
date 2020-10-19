import React, {useState} from "react";
import {
    Switch,
    Route,
    useParams
} from "react-router-dom";
import LoginPage from "./components/pages/login";
import RegistrationPage from "./components/pages/registration";
import {connect} from "react-redux";
import {fetchTable, loginTokenAction, setTableAction} from "./store/actions/app";
import {Redirect} from "react-router-dom";
import Menu from "./components/pages/menu";
import {dishes} from "./data/data";

const Routes = (props) => {

    const tokenCheck = async () => {
        if (!props.token) {
            const token = localStorage.getItem("token");
            if (token !== null && token !== 'null') {
                 await props.loginToken(token);
            }
        }
        return props.token !== null;
    }

    return (
        <>
            {tokenCheck() ?
                <Switch>
                    <Route exact path={'/menu'}>
                        <Menu dishes={dishes}/>
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

const mapStateToProps = state => ({token: state.app.token, table: state.app.table});

const mapDispatchToProps = dispatch => ({
    loginToken: (token) => dispatch(loginTokenAction(token)),
    fetchTable: (id) => dispatch(fetchTable(id)),
    setTable: (table) => dispatch(setTableAction(table))
});

export default connect(mapStateToProps, mapDispatchToProps)(Routes);


const ConnectedLoginWrapper = connect(mapStateToProps, mapDispatchToProps)(LoginWrapper);
