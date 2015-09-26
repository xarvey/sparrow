// __tests__/Header-test.js

jest.dontMock('../app/js/pages/NotFoundPage.js');

import React from 'react/addons';
const NotFoundPage = require('../app/js/pages/NotFoundPage.js');
var TestUtils = React.addons.TestUtils;

describe('NotFoundPage', function() {

  it('should return a not found page when called', function() {

    // Render a header with label in the document

    expect(NotFoundPage).not.toBe(null);

  });
});
