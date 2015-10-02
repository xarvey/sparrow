const alt = require('../alt');
const UserActions = require('../actions/UserActions');
const request = require('superagent');

class UserStore {
  constructor() {
    this.errorMessage = null;
    this.endPointURL = 'http://128.211.242.49:9000';
    this.registered = false;

    this.bindListeners({
      handleRegister: UserActions.register,
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

  handleRegisterFailed(errorMessage) {
    this.errorMessage = errorMessage;
  }
}

module.exports = alt.createStore(UserStore, 'UserStore');
