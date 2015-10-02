import React from 'react';
import { Route } from 'react-router';
import { generateRoute } from 'utils/localized-routes';
import LendHeader from './components/LendHeader';
import BorrowHeader from './components/BorrowHeader';
import Header from './components/header';

export default (
  <Route component={ require('./components/app') }>
    { generateRoute({
      paths: ['/', '/borrow'],
      component: { header: BorrowHeader, content: require('./pages/BorrowListPage') }
    }) }
    { generateRoute({
      paths: ['/lend'],
      component: { header: LendHeader, content: require('./pages/LendListPage') }
    }) }
    { generateRoute({
      paths: ['/login'],
      component: { header: Header, content: require('./pages/LoginPage') }
    }) }
    { generateRoute({
      paths: ['/register'],
      component: { header: Header, content: require('./pages/RegisterPage') }
    }) }
    { generateRoute({
      paths: ['/profile/:seed', '/profil/:seed'],
      component: require('./components/profile')
    }) }
    <Route path='*' component={ require('./pages/not-found') } />
  </Route>
);
