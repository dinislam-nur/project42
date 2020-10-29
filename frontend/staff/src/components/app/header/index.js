import React from "react";
import {Button, Navbar} from "@blueprintjs/core";
import {logout} from "../../../store/actions/app";
import {connect} from "react-redux";

class Header extends React.Component {

    constructor(props) {
        console.log(props)
        super(props);
    }

    homeClick = () => this.props.history.push('/');

    render() {
        return (
            <Navbar>
                <Navbar.Group align={"left"}>
                    <Navbar.Heading>Staff</Navbar.Heading>
                    <Navbar.Divider/>
                    <Button id={"home"} icon="home" minimal={true} onClick={this.homeClick}/>
                    {/*<Button id={"orders"} icon="list-columns" minimal={true}/>*/}
                </Navbar.Group>
                <Navbar.Group align={"right"}>
                    <Button id={"settings"} icon="cog" minimal={true}
                            onClick={() => this.props.history.push('/settings')}/>
                    <Navbar.Divider/>
                    <Button id={"exit"} icon="log-out" minimal={true} onClick={() => this.props.logout()}/>
                </Navbar.Group>

            </Navbar>
        );
    }
}


const mapDispatchToProps = dispatch => ({
    logout: () => dispatch(logout())
});

export default connect(null, mapDispatchToProps)(Header);