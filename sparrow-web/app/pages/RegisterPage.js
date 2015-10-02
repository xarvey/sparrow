import React, { Component } from 'react';
import { Link }        from 'react-router';
import DocumentTitle from 'react-document-title';

import UserActions from '../actions/UserActions';
import userStore from '../stores/UserStore';
// const PasswordError = 'Password length invalid';

class RegisterPage extends Component {

  componentDidMount() {
    userStore();
  }

  state =  {
    value: ''
  }

  handleError(e) {
    this.setState({ value: e });
  }

  errorDisplay() {
    return (<p>{ this.state.value }</p>);
  }

  register(event) {
    console.log('yes');
    console.log(event);
    const newUser = {
      name: document.getElementById('name').value,
      email: document.getElementById('email').value,
      passcode: document.getElementById('pass').value,
      zipCode: document.getElementById('zip').value
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
  }

  render() {
    return (
	<div className = 'title'>
      <DocumentTitle title='Sparrow'>

        <section className='login-page'>

          <form className='input'>
            <input ref='name' id='name' type='text' className='textbox' placeholder = 'Name'/>
            <br/>
            <input ref='email' id='email' type='text' className='textbox' placeholder = 'Username'/>
            <br/>
            <input ref='pass' id='pass' type='password' className='textbox' placeholder = 'Password'/>
            <br/>
            <input ref='confirmPass' type='password' className='textbox' placeholder = 'Confirm Password'/>
            <br/>
            <input ref='zipCode' id='zip' type='text' className='textbox' placeholder = 'Zip Code'/>
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

}

export default RegisterPage;
