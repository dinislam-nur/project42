import React, {useState} from "react";
import Navbar from "reactstrap/es/Navbar";
import NavbarBrand from "reactstrap/es/NavbarBrand";
import NavbarToggler from "reactstrap/es/NavbarToggler";
import Collapse from "reactstrap/es/Collapse";
import Nav from "reactstrap/es/Nav";
import NavItem from "reactstrap/es/NavItem";
import NavLink from "reactstrap/es/NavLink";
import {connect} from "react-redux";
import {changeCategory, logout} from "../../store/actions/app";
import {Button} from "@blueprintjs/core";
import Badge from "reactstrap/es/Badge";
import {useHistory} from "react-router";


const Header = (props) => {
    const [collapsed, setCollapsed] = useState(true);

    const toggleNavbar = () => setCollapsed(!collapsed);

    const history = useHistory();

    const dessertClickHandler = () => {
        toggleNavbar();
        props.changeCategory("DESSERTS", history);
    };

    const soupClickHandler = () => {
        toggleNavbar();
        props.changeCategory("SOUPS", history);
    };
    const saladClickHandler = () => {
        toggleNavbar();
        props.changeCategory("SALADS", history);
    };
    const hotClickHandler = () => {
        toggleNavbar();
        props.changeCategory("HOT_DISHES", history);
    };
    const drinkClickHandler = () => {
        toggleNavbar();
        props.changeCategory("DRINKS", history);
    };


    return (
        <div>
            <Navbar light fixed={"top"} color={"light"}>
                <NavbarBrand className="mr-auto">{props.name}</NavbarBrand>
                <Button icon={"shopping-cart"} style={{marginRight:"5px"}} minimal={true} onClick={()=>history.push('/orders')}>
                    <Badge color="secondary">{props.order.foods.length}</Badge>
                </Button>
                <NavbarToggler onClick={toggleNavbar} className="mr-2"/>
                <Collapse isOpen={!collapsed} navbar>
                    <Nav navbar>
                        <NavItem>
                            <NavLink onClick={saladClickHandler}>Cалаты</NavLink>
                        </NavItem>
                        <NavItem>
                            <NavLink onClick={soupClickHandler}>Суп</NavLink>
                        </NavItem>
                        <NavItem>
                            <NavLink onClick={hotClickHandler}>Горячие блюда</NavLink>
                        </NavItem>
                        <NavItem>
                            <NavLink onClick={drinkClickHandler}>Напитки</NavLink>
                        </NavItem>
                        <NavItem>
                            <NavLink onClick={dessertClickHandler}>Десерты</NavLink>
                        </NavItem>
                        <NavItem>
                            <NavLink onClick={props.logout}>Выйти</NavLink>
                        </NavItem>
                    </Nav>
                </Collapse>
            </Navbar>
        </div>
    );
}

const mapStateToProps = state => {
    return {
        order: state.app.order
    }
}

const mapDispatchToProps = dispatch => ({
    changeCategory: (category, history) => dispatch(changeCategory(category, history)),
    logout: () => dispatch(logout())
});

export default connect(mapStateToProps, mapDispatchToProps)(Header);