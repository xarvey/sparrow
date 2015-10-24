const alt = require('../alt');
const UserActions = require('../actions/UserActions');
const request = require('superagent');
const $ = require('jquery');

class UserStore {
  constructor() {
    this.errorMessage = null;
    this.endPointURL = 'http://vohras.tk:9000';
    this.registered = false;
    this.logined = false;

    this.bindListeners({
      handleRegister: UserActions.register,
      handleLogin: UserActions.login,
      handleRegisterFailed: UserActions.registerFailed
    });
  }

  handleRegister(userInfo) {
    request
      .post(this.endPointURL + '/users')
      .send(userInfo)
      .end((err, res) => {
        if (err) {
          console.error('register error!');
          return;
        }
        console.log(res);
        this.setState({ registered: res });
      });
  }

  handleLogin(userInfo) {
    console.log('try to login');
    request
      .post(this.endPointURL + '/noLogInYet')
      .set('Content-Type', 'application/json')
      .send(userInfo)
      .end((err, res) => {
        if (err) {
          console.error('login error!');
          return;
        }
        document.cookie = "username=" + userInfo.user + '; expires=Thu, 18 Dec 2020 12:00:00 UTC';
        this.setState({ logined: res });
      });
  }

  handleRegisterFailed(errorMessage) {
    this.errorMessage = errorMessage;
  }
}

module.exports = alt.createStore(UserStore, 'UserStore');
