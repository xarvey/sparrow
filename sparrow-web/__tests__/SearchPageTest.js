// __tests__/Header-test.js

jest.dontMock('../app/js/pages/SearchPage.js');

import React from 'react/addons';
const SearchPage = require('../app/js/pages/SearchPage.js');
var TestUtils = React.addons.TestUtils;

describe('NotFoundPage', function() {

  it('should return a search page when called', function() {

    // Render a header with label in the document

    expect(SearchPage).not.toBe(null);

  });
});
