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
      passcode: this.refs.pass.value,
      zipCode: this.refs.zipCode.value
    };

    UserActions.register(newUser);

    // TODO: validation
    /*
    // const usr = document.getElementById('Username').value;
    const pass = document.getElementById('Password').value;
    if (pass.length > 16 || pass.length < 1) {
      console.log(PasswordError);
      this.handleError(PasswordError);
    }
    */
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
