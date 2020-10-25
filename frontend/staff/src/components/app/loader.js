import React from "react";
import {Spinner} from "@blueprintjs/core";


export const Loader = props => {
    return (
        <div style={{textAlign: "center"}}>
            <div style={{display: "inline-block"}}>
                <Spinner intent={"none"}/>
            </div>
        </div>
    )
}