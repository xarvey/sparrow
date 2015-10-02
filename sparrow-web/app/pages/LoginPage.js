import React, { Component } from 'react';
import { Link }        from 'react-router';
import DocumentTitle from 'react-document-title';

const PasswordError = 'Password length invalid';

class LoginPage extends Component {

  state =  {
    value: ''
  }

  handleError(e) {
    this.setState({ value: e });
  }

  errorDisplay() {
    return (<p>{ this.state.value }</p>);
  }

  render() {
    return (
	<div className = 'title'>
      <DocumentTitle title='Sparrow'>

        <section className='login-page'>

          <div className='input'>
            <input type='text' className='textbox' id = 'Username' placeholder = 'Username'/>
            <br></br>
            <input type='password' className='textbox' id = 'Password' placeholder = 'Password'/>
          </div>

          <div className='buttons'>
            <button type='button' id='login' onClick={ this.login }>Login</button>
          </div>
			<span>Need an account? &nbsp; <Link to='/register'>Register</Link></span>
            { this.errorDisplay() }


        </section>
      </DocumentTitle>
	</div>
    );
  }

  login() {
    // const usr = document.getElementById('Username').value;
    const pass = document.getElementById('Password').value;
    if (pass.length > 16 || pass.length < 1) {
      console.log(PasswordError);
      this.handleError(PasswordError);
    } /* else {
      actions.login({
        'Username': usr,
        'Password': pass
      });
    } */
  }

}

export default LoginPage;
