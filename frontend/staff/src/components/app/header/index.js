import React from "react";
import {Button, Navbar} from "@blueprintjs/core";
import {logout} from "../../../store/actions/app";
import {connect} from "react-redux";
import { useHistory } from "react-router-dom";

const Header = props => {

    const history = useHistory()

    const homeClick = () => history.push('/');

        return (
            <Navbar>
                <Navbar.Group align={"left"}>
                    <Navbar.Heading>Staff</Navbar.Heading>
                    <Navbar.Divider/>
                    <Button id={"home"} icon="home" minimal={true} onClick={homeClick}/>
                    <Button id={"waiters"} icon="tick-circle" minimal={true} onClick={()=>history.push('/waiters')}/>
                </Navbar.Group>
                <Navbar.Group align={"right"}>
                    <Navbar.Divider/>
                    <Button id={"exit"} icon="log-out" minimal={true} onClick={() => this.props.logout()}/>
                </Navbar.Group>

            </Navbar>
        );

}


const mapDispatchToProps = dispatch => ({
    logout: () => dispatch(logout())
});

export default connect(null, mapDispatchToProps)(Header);