import React, { Component } from 'react';
import '../App.css';

import { withStyles } from '@material-ui/core/styles';
import FormControl from '@material-ui/core/FormControl';
import Paper from '@material-ui/core/Paper';
import Tabs from '@material-ui/core/Tabs';
import Tab from '@material-ui/core/Tab';
import Radio from '@material-ui/core/Radio';
import RadioGroup from '@material-ui/core/RadioGroup';
import FormControlLabel from '@material-ui/core/FormControlLabel';

const styles = theme => ({
    root: {
        marginTop: theme.spacing.unit * 2,
        display: 'block',
        flexWrap: 'wrap',
        width: '75%',
        margin: 'auto',
        padding: '10px',
        flexGrow: 1
    },
    formControl: {
        margin: theme.spacing.unit,
        minWidth: 120,
    },
    group: {
        margin: `${theme.spacing.unit}px 0`,
    },
});

class QuerySelector extends Component {
    constructor(props){
        super(props)

        this.state = {
            db: "mongo",
            query: "1"
        }
    }

    updateDB = (e, value) => {
        this.props.dbChange(value);
        this.setState({db: value});
    }

    updateQuery = (e) => {
        this.props.queryChange(e.target.value);
        this.setState({query: e.target.value});
    }


    render() {
        const { classes } = this.props
        return (
            <Paper className={classes.root}>
                <Tabs value={this.state.db} onChange={this.updateDB} centered>
                    <Tab id="mongo" value="mongo" label="MongoDB"/>
                    <Tab id="psql" value="psql" label="PostgreSQL"/>
                    <Tab id="neo4j" value="neo4j" label="Neo4J"/>
                </Tabs>
                <form className={classes.root} autoComplete="off">
                
                <FormControl className={classes.formControl}>
                    <RadioGroup
                        aria-label="query"
                        name="query"
                        className={classes.group}
                        value={this.state.query}
                        onChange={this.updateQuery}
                    >
                        <FormControlLabel id="1" value="1" control={<Radio color="primary" />} label="1. Find books that mention city" />
                        <FormControlLabel id="2" value="2" control={<Radio color="primary" />} label="2. Plot cities mentioned by a book" />
                        <FormControlLabel id="3" value="3" control={<Radio color="primary" />} label="3. Plot cities mentioned by books written by an author" />
                        <FormControlLabel id="4" value="4" control={<Radio color="primary" />} label="4. List all books that mention cities in the vicinity of a geolocation" />
                    </RadioGroup>
                </FormControl>
            </form>
            </Paper>
            
            
        );
      }
}

export default withStyles(styles)(QuerySelector);
