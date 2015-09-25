'use strict';

import React                       from 'react/addons';
import {Router, Route, IndexRoute, DefaultRoute} from 'react-router';
import CreateBrowserHistory        from 'react-router/node_modules/history/lib/createBrowserHistory';

import App                         from './App';
import BorrowListPage              from './pages/BorrowListPage';
import LendListPage              from './pages/LendListPage';
import SearchPage                  from './pages/SearchPage';
import LoginPage				   from './pages/LoginPage';
import RegisterPage				   from './pages/RegisterPage';
import NotFoundPage                from './pages/NotFoundPage';

export default (
  <Router history={CreateBrowserHistory()}>
    <Route path="/" component={App}>

      <IndexRoute component={BorrowListPage} />

      <Route path="/borrow" component={BorrowListPage} />
      <Route path="/lend" component={LendListPage} />
      <Route path="/search" component={SearchPage} />
      <Route path="/login" component={LoginPage} />
      <Route path="/register" component={RegisterPage} />
      <Route path="*" component={NotFoundPage} />

    </Route>
  </Router>
);