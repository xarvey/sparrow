'use strict';

import React         from 'react/addons';
import {Link}        from 'react-router';
import DocumentTitle from 'react-document-title';

var PasswordError = "Password length invalid";
var MisMatch = "Passwords don't match";

const RegisterPage = React.createClass({

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
        <section className="register-page">

          <div>
            <input type="text" className="textbox" id="Username" placeholder="Username"/>
            <br></br>
            <input type="password" className="textbox" id="Password" placeholder="Password"/>
			<br></br>
			<input type="password" className="textbox" id="ConfirmPassword" placeholder="Confirm Password"/>
          </div>

          <div className="buttons">
            <button type="button" id="register" onClick={this.register}>Register</button>
		  </div>
            <Link to="/login">Login</Link>
            {this.errorDisplay()}
          

        </section>
      </DocumentTitle>
	  </div>
    );
  },

  register() {
    var usr = document.getElementById("Username").value;
    var pass = document.getElementById("Password").value;
	var confirm = document.getElementById("ConfirmPassword").value;
    if(pass.length > 16 || pass.length < 1) {
      console.log(PasswordError);
      this.handleError(PasswordError);
    }
	if(pass != confirm)
	{
		console.log(MisMatch);
		this.handleError(MisMatch);
	}
  }

});

export default RegisterPage;