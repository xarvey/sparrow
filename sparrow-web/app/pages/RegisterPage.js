import React from 'react';
import { Link }        from 'react-router';
import DocumentTitle from 'react-document-title';

import UserActions from '../actions/UserActions';

const userStore = require('../stores/UserStore');
// const PasswordError = 'Password length invalid';

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

  errorDisplay() {
    return (<p>{ this.state.value }</p>);
  },

  register(event) {
    console.log(this.refs.name.value);
    console.log(event);
    const newUser = {
      name: this.refs.name.value,
      email: this.refs.email.value,
      password: this.refs.pass.value,
      confirmPass: this.refs.confirmPass.value,
      zipCode: this.refs.zipCode.value
    };

    var validateResult = true;
    //defect #22
    if(newUser.name == undefined || newUser.email == undefined || newUser.password == undefined || newUser.confirmPass == undefined) {
      validateResult = false;
    }
    if(validateResult) {
      var re = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
      validateResult &= re.test(newUser.email);
      if (newUser.password.length > 16 || newUser.password.length < 8) {
        validateResult = false;
      }
      // defect #1
      /*
      if(newUser.password != newUser.confirmPass) {
        validateResult = false;
      }*/
    }
    if(validateResult) {
      UserActions.register(newUser);
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
            <br/>
            <input ref='pass' type='password' className='textbox' placeholder = 'Password'/>
            <br/>
            <input ref='confirmPass' type='password' className='textbox' placeholder = 'Confirm Password'/>
            <br/>
            <input ref='zipCode' type='text' className='textbox' placeholder = 'Zip Code'/>
          </form>

          <div className='buttons'>
            <button type='button' id='register' onClick={ this.register } >Register</button>
          </div>
			<span>Already have an account? &nbsp;<Link to='/login'> Login</Link></span>
            { this.errorDisplay() }


        </section>
      </DocumentTitle>
	</div>
    );
  }

});

export default RegisterPage;
