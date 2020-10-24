import React from "react";
import {Button, ButtonGroup} from "@blueprintjs/core";


export const MenuFooter = props => {
    return (
        <div style={{textAlign: "center"}}>
            <ButtonGroup style={{display: "inline-block"}}>
                <Button icon={'chevron-left'} onClick={props.onPrevPage} disabled={props.page.first}/>
                <Button minimal active={false}>{props.page.number + 1}</Button>
                <Button icon={'chevron-right'} onClick={props.onNextPage} disabled={props.page.last}/>
            </ButtonGroup>
        </div>
    )
}
