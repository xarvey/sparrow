// __tests__/Header-test.js

jest.dontMock('../app/js/components/Header.js');

import React from 'react/addons';
const Header = require('../app/js/components/Header.js');
var TestUtils = React.addons.TestUtils;

describe('Header', function() {
  
  it('changes the active tab when clicked', function() {
    
    // Render a header with label in the document
    var header = TestUtils.renderIntoDocument(
      <Header />
    );

    // Verify that it's at 'borrow request' by default
    var activeTab = TestUtils.findRenderedDOMComponentWithClass(
      header, 
      'tab active'
    );
    expect(React.findDOMNode(activeTab).textContent).toEqual('Borrow Requests');

  });
});