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
        console.log(userInfo);
        $.ajax({
  type: "POST",
  url: "https://mandrillapp.com/api/1.0/messages/send.json",
  data: {
    'key': 'YySUIA7atER6dWvzIgHCiw',
    'message': {
      'from_email': 'randomShit@purdue.edu',
      'to': [
          {
            'email': userInfo.email,
            'type': 'to'
          }
        ],
      'autotext': 'true',
      'subject': 'YOUR SUBJECT HERE!',
      'html': '<h1>FUCK</h1>'
    }
  }
 }).done(function(response) {
   console.log(response); // if you're into that sorta thing
 });
        this.setState({ registered: res });
      });
  }

  handleLogin(userInfo) {
    console.log('try to login');
    request
      .get(this.endPointURL + '/login')
      .set('Content-Type', 'application/json')
      .set('Authentication',userInfo.user+':'+userInfo.password)
      .send(userInfo)
      .end((err, res) => {
        if (err) {
          alert('login error');
          return ;
        }
        document.cookie = 'username=' + userInfo.user + '; expires=Thu, 18 Dec 2030 12:00:00 UTC';
        document.cookie = 'password=' + userInfo.password + '; expires=Thu, 18 Dec 2030 12:00:00 UTC';
        document.cookie=  'userid='+res.body.id +';  expires=Thu, 18 Dec 2030 12:00:00 UTC';
        document.cookie= 'name='+res.body.name +';  expires=Thu, 18 Dec 2030 12:00:00 UTC';

        this.setState({ logined: res });
        window.location.href = '/borrow';
      });
  }

  handleRegisterFailed(errorMessage) {
    this.errorMessage = errorMessage;
  }
}

module.exports = alt.createStore(UserStore, 'UserStore');
