const alt = require('../alt');

class UserActions {

  login(userInfo) {
    this.dispatch(userInfo);
  }
  register(userInfo) {
    this.dispatch(userInfo);
  }

  registerFailed(errorMessage) {
    this.dispatch(errorMessage);
  }
}

module.exports = alt.createActions(UserActions);
