import rootReducer from "./reducers";
import {applyMiddleware, createStore} from "redux";
import { composeWithDevTools } from 'redux-devtools-extension';
import thunk from "redux-thunk";

export default createStore(rootReducer, composeWithDevTools(applyMiddleware(thunk)));
