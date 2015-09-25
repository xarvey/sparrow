'use strict';

import React         from 'react/addons';
import actions       from '../actions/CurrentUserActions'
import {Link}        from 'react-router';
import DocumentTitle from 'react-document-title';

var PasswordError = "Password length invalid";

const LoginPage = React.createClass({

  getInitialState() {
    return {value: ''};
  },

  handleError(e) {
    this.setState({value: e})
  },

  errorDisplay() {
    return (<p>{this.state.value}</p>);
  },

  render() {
    return (
	<div className = "title">
      <DocumentTitle title="Sparrow">

        <section className="login-page">

          <div className="input">
            <input type="text" className="textbox" id = "Username" placeholder ="Username"/>
            <br></br>
            <input type="password" className="textbox" id = "Password" placeholder ="Password"/>
          </div>

          <div className="buttons">
            <button type="button" id="login" onClick={this.login}>Login</button>
          </div>
			<Link to="/register">Need an account? Register</Link>
            {this.errorDisplay()}


        </section>
      </DocumentTitle>
	</div>
    );
  },

  login() {
    var usr = document.getElementById("Username").value;
    var pass = document.getElementById("Password").value;
    if(pass.length > 16 || pass.length < 1) {
      console.log(PasswordError);
      this.handleError(PasswordError);
    }
    else {
        actions.login({
          'Username':usr,
          'Password':pass
        });
    }
  }

});

export default LoginPage;
