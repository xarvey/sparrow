'use strict';

import APIUtils from './APIUtils';

const AuthAPI = {

  checkLoginStatus() {
    return APIUtils.get('auth/check');
  },

  login(user) {
    return APIUtils.post('auth/login', user);
  },

  logout() {
    return APIUtils.post('auth/logout');
  },

  register(user) {
    return APIUtils.post('auth/register', user);
  },

};

export default AuthAPI;
