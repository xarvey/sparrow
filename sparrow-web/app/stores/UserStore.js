const alt = require('../alt');
const UserActions = require('../actions/UserActions');
const request = require('superagent');

class UserStore {
  constructor() {
    this.errorMessage = null;
    this.endPointURL = 'http://128.211.242.49:9000';
    this.registered = false;
    this.logined = false;

    this.bindListeners({
      handleRegister: UserActions.register,
      handleLogin: UserActions.login,
      handleRegisterFailed: UserActions.registerFailed
    });
  }

  handleRegister(userInfo) {
    console.log('RECEIVED?');
    request
      .post(this.endPointURL + '/users')
      .set('Content-Type', 'application/json')
      .send(userInfo)
      .end((err, res) => {
        if (err) {
          console.error('register error!');
          return;
        }
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
