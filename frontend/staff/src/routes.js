import React from "react";
import {
    Switch,
    Route,
    useParams
} from "react-router-dom";
import {connect, useDispatch} from "react-redux";
import {Redirect} from "react-router-dom";
import LoginPage from "./components/pages/login";

const Routes = (props) => {

    // const tokenCheck = () => {
    //     if (!props.token) {
    //         const token = localStorage.getItem("token");
    //         if (token) {
    //             props.loginToken(token);
    //         }
    //     }
    //     return props.token === "1234";
    // }

    const publicRoute = (
        <Switch>
            <Route exact path={'/login'}>
                <LoginPage/>
            </Route>
        </Switch>
    );
    return (
        // <Switch>
        //     {/*{tokenCheck() ? privateRoute : publicRoute}*/}
        // </Switch>
        publicRoute
    );
}
//
// const mapStateToProps = state => ({token: state.app.token, table: state.app.table});
//
// const mapDispatchToProps = dispatch => ({
//     loginToken: (token) => dispatch(loginTokenAction(token))
// });
//
// export default connect(mapStateToProps, mapDispatchToProps)(Routes);


export default Routes;