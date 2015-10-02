const alt = require('../alt');

class UserActions {

  // TODO: Login

  register(userInfo) {
    this.dispatch(userInfo);
  }

  registerFailed(errorMessage) {
    this.dispatch(errorMessage);
  }
}

module.exports = alt.createActions(UserActions);
