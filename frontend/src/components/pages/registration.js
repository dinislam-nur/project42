import React from "react";
import RegistrationForm from "../forms/registration";

const initState = {
    password: ""
}

class RegistrationPage extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div>
                <RegistrationForm onConfirm={() => console.log(this.state.password)}
                                  setPassword={(password) => this.setState({...this.state, password})}/>
            </div>
        )
    }
}

export default RegistrationPage;
