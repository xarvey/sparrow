import React from 'react';
import { Link }        from 'react-router';
import DocumentTitle from 'react-document-title';

import UserActions from '../actions/UserActions';

const userStore = require('../stores/UserStore');
// const PasswordError = 'Password length invalid';
const request = require('superagent');
const RegisterPage = React.createClass({

  componentDidMount() {
    userStore();
  },

  getInitialState() {
    return { value: '' };
  },

  handleError(e) {
    this.setState({ value: e });
  },

  getcookie(cname) {
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for(var i=0; i<ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0)==' ') c = c.substring(1);
        if (c.indexOf(name) == 0) return c.substring(name.length,c.length);
    }
    return " "
  },


  errorDisplay() {
    return (<p>{ this.state.value }</p>);
  },

  register(event) {
    console.log(this.refs.name.value);
    console.log(event);
    const newUser = {
      name: this.refs.name.value,
      email: this.refs.email.value,
      password: this.getcookie('password'),
      confirmPass: this.getcookie('password'),
      zipCode: this.getcookie('zipcode')
    };

    var validateResult = true;
    if(newUser.name == undefined || newUser.password == undefined || newUser.confirmPass == undefined) {
      validateResult = false;
    }
    if(validateResult) {
      if (newUser.password.length > 16 || newUser.password.length < 8) {
        validateResult = false;
      }
      if(newUser.password != newUser.confirmPass) {
        validateResult = false;
      }
    }
    console.log(newUser)
    if(validateResult) {
      request
        .put('http://vohras.tk:9000/users')
        .set('Authentication',this.getcookie('username')+':'+this.getcookie('password'))
        .send(newUser)
        .end((err, res) => {
          if (err) {
            console.error('register error!');
            return;
          }
          document.cookie = 'name=' + newUser.name + '; expires=Thu, 18 Dec 2030 12:00:00 UTC';
          document.cookie = 'password=' + newUser.password + '; expires=Thu, 18 Dec 2030 12:00:00 UTC';
        })
    }
    else {
      alert("Input Error");
    }
  },

  render() {
    return (
	<div className = 'title'>
      <DocumentTitle title='Sparrow'>

        <section className='login-page'>

          <form className='input'>
            <input ref='name' type='text' className='textbox' placeholder = 'Name'/>
            <br/>

            <input ref='email' type='text' className='textbox' placeholder = 'Email'/>

          </form>

          <div className='buttons'>
            <button type='button' id='register' onClick={ this.register } >Update</button>
          </div>
            { this.errorDisplay() }


        </section>
      </DocumentTitle>
	</div>
    );
  }

});

export default RegisterPage;
