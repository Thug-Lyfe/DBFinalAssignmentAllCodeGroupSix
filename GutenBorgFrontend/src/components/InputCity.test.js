import React from 'react';
import ReactDOM from 'react-dom';
import InputCity from './InputCity';

it('renders without crashing', () => {
  const div = document.createElement('div');
  ReactDOM.render(<InputCity />, div);
  ReactDOM.unmountComponentAtNode(div);
});
