import React from "react";
import {connect} from "react-redux";
import {closeTable, fetchOpenTables} from "../../store/actions/app";
import CardGroup from "reactstrap/es/CardGroup";
import Card from "reactstrap/es/Card";
import CardTitle from "reactstrap/es/CardTitle";
import CardColumns from "reactstrap/es/CardColumns";
import {Loader} from "../app/loader";
import CardBody from "reactstrap/es/CardBody";
import {Button} from "@blueprintjs/core";


class TablePage extends React.Component {
    constructor(props) {
        super(props);
    }

    async componentDidMount() {
        await this.props.fetchTables();
    }


    render() {
        let list = null;
        let tableColumns = (
            <Card>
                <CardTitle><b>Открытых столов нет <span role="img" aria-label="donut">😔</span></b></CardTitle>
            </Card>
        );
        if (this.props.tables !== null) {
            list = this.props.tables.map(table => <Table table={table}
                                                                 onDelete={this.props.closeTable}/>)
            if (this.props.tables.length !== 0) {
                tableColumns = (<CardColumns style={{width: "80%"}} className={"orders_centre"}>{list}</CardColumns>);
            }
        }
        return (
            <div className={"layout"}>
                <h1>Открытые столы:</h1>
                {this.props.loaded ?
                    tableColumns : <Loader/>}
            </div>
        )
    }
}

const Table = props => {
    const deleteHandler = () => props.onDelete(props.table)
    return (
        <Card>
            <CardTitle><b>Стол №{props.table.number}</b></CardTitle>
            <CardBody><Button intent={"danger"} onClick={deleteHandler}>Закрыть стол</Button></CardBody>
        </Card>
    )
}

const mapStateToProps = state => {
    return {
        loaded: state.app.loaded,
        tables: state.app.tables
    }
}

const mapDispatchToProps = dispatch => ({
    fetchTables: () => dispatch(fetchOpenTables()),
    closeTable: table => dispatch(closeTable(table))
});

export default connect(mapStateToProps, mapDispatchToProps)(TablePage);