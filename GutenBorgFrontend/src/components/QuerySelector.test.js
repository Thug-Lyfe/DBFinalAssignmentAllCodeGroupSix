import React from 'react';
import ReactDOM from 'react-dom';
import QuerySelector from './QuerySelector';

it('renders without crashing', () => {
  const div = document.createElement('div');
  ReactDOM.render(<QuerySelector />, div);
  ReactDOM.unmountComponentAtNode(div);
});
