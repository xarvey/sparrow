import React from 'react';
import { Route } from 'react-router';
import { generateRoute } from 'utils/localized-routes';
import LendHeader from './components/LendHeader';
import BorrowHeader from './components/BorrowHeader';
import Header from './components/header';
import ListingDetails from './pages/ListingDetails';
import ProfilePage from './pages/ProfilePage';
import FinishPage from './pages/FinishPage';

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
      paths: ['/create'],
      component: { header: Header, content: require('./pages/ListingPage') }
    }) }
    { generateRoute({
      paths: ['/editListing/:id'],
      component: { header: Header, content: require('./pages/EditListing') }
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
      paths: ['/listing/:id'],
      component: { header: Header, content: require('./pages/ListingDetails') }
    }) }
    { generateRoute({
      paths: ['/user/:id'],
      component: { header: Header, content: require('./pages/ProfilePage') }
    }) }
    { generateRoute({
      paths: ['/finish/:id'],
      component: { header: Header, content: require('./pages/FinishPage') }
    }) }
    { generateRoute({
      paths: ['/edit'],
      component: { header: Header, content: require('./pages/EditUser') }
    }) }
    { generateRoute({
      paths: ['/profile/:seed', '/profil/:seed'],
      component: require('./components/profile')
    }) }
    <Route path='*' component={ require('./pages/not-found') } />
  </Route>
);
