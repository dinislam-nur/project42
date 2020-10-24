import React from "react";
import {Button, ButtonGroup} from "@blueprintjs/core";


export const MenuFooter = props => {
    return (
        <div style={{width: '100%'}}>
            <ButtonGroup>
                {props.page.first ? null : <Button icon={'chevron-left'} onClick={props.onPrevPage}/>}
                <Button minimal active={false}>{props.page.number + 1}</Button>
                {props.page.last ? null : <Button icon={'chevron-right'} onClick={props.onNextPage}/>}
            </ButtonGroup>
        </div>
    )
}
