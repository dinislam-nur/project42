import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import {BrowserRouter} from "react-router-dom";
import Routes from "./routes";
import 'bootstrap/dist/css/bootstrap.css';

ReactDOM.render(
    <React.StrictMode>
        <BrowserRouter>
            <Routes/>
        </BrowserRouter>
    </React.StrictMode>,
    document.getElementById('root')
);