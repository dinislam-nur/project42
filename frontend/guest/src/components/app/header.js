import React, {useState} from "react";
import Navbar from "reactstrap/es/Navbar";
import NavbarBrand from "reactstrap/es/NavbarBrand";
import NavbarToggler from "reactstrap/es/NavbarToggler";
import Collapse from "reactstrap/es/Collapse";
import Nav from "reactstrap/es/Nav";
import NavItem from "reactstrap/es/NavItem";
import NavLink from "reactstrap/es/NavLink";
import {logoutAction} from "../../store/actions/app";
import {connect} from "react-redux";
import Button from "reactstrap/es/Button";


const Header = (props) => {
    const [collapsed, setCollapsed] = useState(true);

    const toggleNavbar = () => setCollapsed(!collapsed);

    const pastaClickHandler = () => {
        toggleNavbar();
        props.setCategory("PASTA");
    };
    const soupClickHandler = () => {
        toggleNavbar();
        props.setCategory("SOUP");
    };

    return (
        <div>
            <Navbar light fixed={"top"} color={"light"}>
                <NavbarBrand className="mr-auto">Меню</NavbarBrand>
                <NavbarToggler onClick={toggleNavbar} className="mr-2"/>
                <Collapse isOpen={!collapsed} navbar>
                    <Nav navbar>
                        <NavItem>
                            <NavLink onClick={pastaClickHandler}>Паста</NavLink>
                        </NavItem>
                        <NavItem>
                            <NavLink onClick={soupClickHandler}>Суп</NavLink>
                        </NavItem>
                    </Nav>
                    <Button onClick={props.logout}>Exit</Button>
                </Collapse>
            </Navbar>
        </div>
    );
}

const mapDispatchToProps = dispatch => ({
    logout: () => dispatch(logoutAction())
});

export default connect(null, mapDispatchToProps)(Header);