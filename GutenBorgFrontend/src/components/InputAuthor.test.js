import React from 'react';
import ReactDOM from 'react-dom';
import InputAuthor from './InputAuthor';

it('renders without crashing', () => {
  const div = document.createElement('div');
  ReactDOM.render(<InputAuthor />, div);
  ReactDOM.unmountComponentAtNode(div);
});
