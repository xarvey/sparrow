const alt = require('../alt');
const UserActions = require('../actions/UserActions');
const $ = require('jquery');

class UserStore {
  constructor() {
    this.errorMessage = null;
    this.endPointURL = 'http://128.211.242.49:9000';
    this.registered = false;

    this.bindListeners({
      handleRegister: UserActions.REGISTER,
      handleRegisterFailed: UserActions.REGISTER_FAILED
    });
  }

  handleRegister(userInfo) {
    $.ajax({
      url: this.endPointURL + '/users',
      dataType: 'json',
      type: 'POST',
      data: userInfo,
      success: function(data) {
        this.setState({ registered: data });
      }.bind(this),
      error: function(xhr, status, err) {
        console.error(this.endPointURL, status, err.toString());
      }.bind(this)
    });
  }

  handleRegisterFailed(errorMessage) {
    this.errorMessage = errorMessage;
  }
}

module.exports = alt.createStore(UserStore, 'UserStore');
