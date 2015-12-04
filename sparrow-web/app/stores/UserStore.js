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

   deleteAllCookies() {
      // defect 20
      /*var cookies = document.cookie.split(";");

      for (var i = 0; i < cookies.length; i++) {
      	var cookie = cookies[i];
      	var eqPos = cookie.indexOf("=");
      	var name = eqPos > -1 ? cookie.substr(0, eqPos) : cookie;
      	document.cookie = name + "=;expires=Thu, 01 Jan 1970 00:00:00 GMT";
      }*/
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
      'from_email': 'sparrow@purdue.edu',
      'to': [
          {
            'email': userInfo.email,
            'type': 'to'
          }
        ],
      'autotext': 'true',
      'subject': 'You have succefully registered an email account for sparrow',
      'html': ''
    }
  }
 }).done(function(response) {
   // defect #24
   //UserActions.login({user: userInfo.email, password: userInfo.password});
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
        this.deleteAllCookies();
        document.cookie = 'username=' + userInfo.user + '; expires=Thu, 18 Dec 2030 12:00:00 UTC';
        document.cookie = 'password=' + userInfo.password + '; expires=Thu, 18 Dec 2030 12:00:00 UTC';
        document.cookie=  'userid='+res.body.id +';  expires=Thu, 18 Dec 2030 12:00:00 UTC';
        document.cookie= 'name='+res.body.name +';  expires=Thu, 18 Dec 2030 12:00:00 UTC';
        document.cookie= 'zipcode='+res.body.zipCode +';  expires=Thu, 18 Dec 2030 12:00:00 UTC';

        this.setState({ logined: res });
        window.location.href = '/borrow';
      });
  }

  handleRegisterFailed(errorMessage) {
    this.errorMessage = errorMessage;
  }
}

module.exports = alt.createStore(UserStore, 'UserStore');
