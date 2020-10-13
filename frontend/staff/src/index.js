import React from 'react';
import ReactDOM from 'react-dom';
import './index.scss';
import 'normalize.css';
import {BrowserRouter} from "react-router-dom";
import Routes from "./routes";
import './index.scss';
import store from "./store/store";
import {Provider} from "react-redux";


ReactDOM.render(
    <Provider store={store}>
        <React.StrictMode>
            <BrowserRouter>
                <Routes/>
            </BrowserRouter>
        </React.StrictMode>
    </Provider>
    ,
    document.getElementById('root')
);