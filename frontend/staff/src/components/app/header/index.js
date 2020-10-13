import React from "react";
import {Button, Navbar} from "@blueprintjs/core";
import {logoutAction} from "../../../store/actions/app";
import {connect} from "react-redux";

const Header = ({
                    onHomeClick,
                    onOrdersClick,
                    onSettingsClick,
                    logout
                }) => {
    return (
        <Navbar>
            <Navbar.Group align={"left"}>
                <Navbar.Heading>Staff</Navbar.Heading>
                <Navbar.Divider/>
                <Button id={"home"} icon="home" minimal={true} onClick={onHomeClick}/>
                {/*<Button id={"orders"} icon="list-columns" minimal={true}/>*/}
            </Navbar.Group>
            <Navbar.Group align={"right"}>
                <Button id={"settings"} icon="cog" minimal={true} onClick={onSettingsClick}/>
                <Navbar.Divider/>
                <Button id={"exit"} icon="log-out" minimal={true} onClick={() => logout()}/>
            </Navbar.Group>

        </Navbar>
    );
}


const mapDispatchToProps = dispatch => ({
    logout: () => dispatch(logoutAction())
});

export default connect(null, mapDispatchToProps)(Header);