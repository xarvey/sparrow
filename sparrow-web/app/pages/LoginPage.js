import React from 'react';
import { Link }        from 'react-router';
import DocumentTitle from 'react-document-title';
import UserActions from '../actions/UserActions';

const InputError = 'Invalid input';

const LoginPage = React.createClass({

  getInitialState() {
    return { value: '' };
  },

  handleError(e) {
    this.setState({ value: e });
  },

  errorDisplay() {
    return (<p>{ this.state.value }</p>);
  },

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
  },

  login() {
    const usr = document.getElementById('Username').value;
    const pass = document.getElementById('Password').value;
    console.log(usr);
    if (pass.length > 16 || pass.length < 8 || usr.length < 1) {
      console.log(InputError);
      this.handleError(InputError);
    } else {
      const loginAttempt = {
        'user': usr,
        'password': pass
      };
      UserActions.login(loginAttempt);
    }
  }

});

export default LoginPage;
