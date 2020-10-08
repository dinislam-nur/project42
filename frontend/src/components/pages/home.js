import React from "react";
import {Link, useParams} from "react-router-dom";
import Button from "reactstrap/es/Button";

export default class HomePage extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return(
            <div>
                <h1>Ваш столик #{this.props.table}</h1>
                <Link to={"/login"}>
                    <Button>Войти</Button>
                </Link>
            </div>
        )
    }
}