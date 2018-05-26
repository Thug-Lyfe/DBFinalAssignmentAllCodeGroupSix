import React from 'react';
import ReactDOM from 'react-dom';
import InputBook from './InputBook';

it('renders without crashing', () => {
  const div = document.createElement('div');
  ReactDOM.render(<InputBook />, div);
  ReactDOM.unmountComponentAtNode(div);
});
