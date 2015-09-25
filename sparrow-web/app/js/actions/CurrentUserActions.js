'use strict';

import Reflux from 'reflux';

const CurrentUserActions = Reflux.createActions([

  'registerUser',
  'checkLoginStatus',
  'forcelogin',
  'login',
  'logout'

]);

export default CurrentUserActions;
