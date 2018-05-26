import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';
import QuerySelector from './components/QuerySelector';
import InputCity from './components/InputCity';
import InputBook from './components/InputBook';
import InputAuthor from './components/InputAuthor';
import InputGeo from './components/InputGeo';

class App extends Component {
  constructor(props) {
    super(props);

    this.state = {
      db: "mongo",
      query: "1"
    }
  }
  
  onDBChange = (newDB) => {
    this.setState({db: newDB});
  }

  onQueryChange = (newQuery) => {
    this.setState({query: newQuery});
  }

  getDb = () => {
    return this.state.db;
  }

  render() {
    return (
      <div className="App">
        <header className="App-header">
          <img src={logo} className="App-logo" alt="logo" />
          <h1 className="App-title">Gutenburg Books - Group 6 Project</h1>
        </header>
        <QuerySelector dbChange={this.onDBChange} queryChange={this.onQueryChange}></QuerySelector>
        {(this.state.query === "1") && <InputCity config={this.state} /> }
        {(this.state.query === "2") && <InputBook config={this.state} /> }
        {(this.state.query === "3") && <InputAuthor config={this.state} /> }
        {(this.state.query === "4") && <InputGeo config={this.state} /> }
      </div>
    );
  }
}

export default App;
