'use strict';

import React                       from 'react/addons';
import {Router, Route, IndexRoute} from 'react-router';
import CreateBrowserHistory        from 'react-router/node_modules/history/lib/createBrowserHistory';

import App                         from './App';
import HomePage                    from './pages/HomePage';
import SearchPage                  from './pages/SearchPage';
import LoginPage				           from './pages/LoginPage';
import RegisterPage				         from './pages/RegisterPage';
import ListingPage                 from './pages/ListingPage';
import NotFoundPage                from './pages/NotFoundPage';

export default (
  <Router history={CreateBrowserHistory()}>
    <Route path="/" component={App}>

      <IndexRoute component={HomePage} />

      <Route path="/" component={HomePage} />
      <Route path="/search" component={SearchPage} />
      <Route path="/login" component={LoginPage} />
      <Route path="/register" component={RegisterPage} />
      <Route path="/listing" component={ListingPage} />
      <Route path="*" component={NotFoundPage} />

    </Route>
  </Router>
);