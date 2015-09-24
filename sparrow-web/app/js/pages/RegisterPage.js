'use strict';

import React         from 'react/addons';
import {Link}        from 'react-router';
import DocumentTitle from 'react-document-title';

var PasswordError = "Password length invalid";

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
      <DocumentTitle title="Sparrow">
        <section className="register-page">

          <div>
            <input type="text" className="textbox" id="Username" placeholder="Username"/>
            <br></br>
            <input type="password" className="textbox" id="Password" placeholder="Password"/>
          </div>

          <div>
            <button type="button" id="register" onClick={this.register}>Register</button>
            <Link to="/login"><button type="button" id="login">Login</button></Link>
            {this.errorDisplay()}
          </div>

        </section>
      </DocumentTitle>
    );
  },

  register() {
    var usr = document.getElementById("Username").value;
    var pass = document.getElementById("Password").value;
    if(pass.length > 16 || pass.length < 1) {
      console.log(PasswordError);
      this.handleError(PasswordError);
    }
  }

});

export default RegisterPage;